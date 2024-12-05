package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class JobOfferSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferSpringBootApplication.class, args);
    }
}
