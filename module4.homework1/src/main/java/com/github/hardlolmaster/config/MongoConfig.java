package com.github.hardlolmaster.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.github.hardlolmaster")
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "bacon";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        super.configureClientSettings(builder);
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/bacon");
        builder.applyConnectionString(connectionString);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.github.hardlolmaster");
    }
}
