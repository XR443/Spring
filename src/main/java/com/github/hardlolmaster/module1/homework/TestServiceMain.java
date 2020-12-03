package com.github.hardlolmaster.module1.homework;

import com.github.hardlolmaster.module1.homework.services.CommandLineTestingSystemServiceImpl;
import com.github.hardlolmaster.module1.homework.services.ITestingSystemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class TestServiceMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestServiceMain.class);
        ITestingSystemService testingSystem = context.getBean(CommandLineTestingSystemServiceImpl.class);
        testingSystem.startTesting();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/module1/homework/questions");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
