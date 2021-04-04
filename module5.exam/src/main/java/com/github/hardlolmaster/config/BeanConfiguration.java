package com.github.hardlolmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

@Configuration
public class BeanConfiguration {
    @Bean
    public Yaml yaml() {
        return new Yaml();
    }
}
