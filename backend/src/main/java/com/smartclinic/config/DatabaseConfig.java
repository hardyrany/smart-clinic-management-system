package com.smartclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.smartclinic.repository.jpa")
@EnableMongoRepositories(basePackages = "com.smartclinic.repository.mongo")
public class DatabaseConfig {
}