package com.github.hardlolmaster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.github.hardlolmaster.repository")
public class JpaConfig {
}
