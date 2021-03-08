package com.github.hardlolmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainActuator {
    public static void main(String[] args) {
        SpringApplication.run(MainActuator.class);
    }

    @Bean
    public HealthIndicator healthIndicator() {
        return new HealthIndicator() {
            @Override
            public Health health() {
                return Health.up().build();
            }
        };
    }
}
