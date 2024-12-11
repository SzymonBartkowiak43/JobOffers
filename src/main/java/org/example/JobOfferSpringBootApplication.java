package org.example;

import org.example.infrastructure.security.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfigurationProperties.class)
public class JobOfferSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferSpringBootApplication.class, args);
    }
}
