package com.smartclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.smartclinic.repository.jpa")
public class JpaConfig {
    // Aqui vai configuração JPA (DataSource, etc.) se necessário
}