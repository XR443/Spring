package com.github.hardlolmaster.batch;

import com.github.hardlolmaster.domain.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    public static List<Product> from = new ArrayList<>(Arrays.asList(
            new Product(null, "name1", "descr1", 10.0d),
            new Product(null, "name2", "descr2", 10.0d),
            new Product(null, "name3", "descr3", 10.0d),
            new Product(null, "name4", "descr4", 10.0d),
            new Product(null, "name5", "descr5", 10.0d),
            null
    ));
    public static List<Product> to = new ArrayList<>();

    @Bean
    public ItemReader<Product> reader() {
        return new ItemReader<Product>() {
            int i = 0;

            @Override
            public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return from.get(i++);
            }
        };
    }

    @Bean
    public ItemReader<Product> readerJpa(EntityManagerFactory managerFactory) {
        return new JpaPagingItemReaderBuilder<Product>()
                .entityManagerFactory(managerFactory)
                .name("readerJpa")
                .queryString("SELECT p FROM Product p")
                .build();
    }

    @Bean("mongoWriter")
    public ItemWriter<Product> writer(MongoOperations mongoOperations) {
        return new MongoItemWriterBuilder<Product>().template(mongoOperations).collection("product").build();
    }

    @Bean("jpaWriter")
    public ItemWriter<Product> writer(EntityManagerFactory managerFactory) {
        return new JpaItemWriterBuilder<Product>().entityManagerFactory(managerFactory).usePersist(true).build();
    }

    @Bean
    public Step jpaWriterStep(StepBuilderFactory stepBuilderFactory,
                              ItemReader<Product> reader,
                              @Qualifier("jpaWriter") ItemWriter<Product> writer) {
        return stepBuilderFactory.get("jpaWriterStep")
                .<Product, Product>chunk(2)
                .reader(reader)
                .processor(new ItemProcessor<Product, Product>() {
                    @Override
                    public Product process(Product item) throws Exception {
                        item.setDescription("ToJpaProcess");
                        return item;
                    }
                })
                .writer(writer)
                .build();
    }

    @Bean
    public Step mongoWriterStep(StepBuilderFactory stepBuilderFactory,
                                @Qualifier("readerJpa") ItemReader<Product> reader,
                                @Qualifier("mongoWriter") ItemWriter<Product> writer) {
        return stepBuilderFactory.get("mongoWriterStep")
                .<Product, Product>chunk(2)
                .reader(reader)
                .processor(new ItemProcessor<Product, Product>() {
                    @Override
                    public Product process(Product item) throws Exception {
                        item.setDescription("ToMongoProcess");
                        return item;
                    }
                })
                .writer(writer)
                .build();
    }

    @Bean
    public Job jpaJob(JobBuilderFactory jobBuilderFactory,
                      @Qualifier("jpaWriterStep") Step jpaStep,
                      @Qualifier("mongoWriterStep") Step mongoStep) {
        return jobBuilderFactory.get("jpaJob")
                .start(jpaStep)
                .next(mongoStep)
                .build();
    }
}
