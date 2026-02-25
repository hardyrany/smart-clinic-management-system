package com.smartclinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseChecker implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== VERIFICANDO TABELAS ===");
        
        List<String> tables = jdbcTemplate.queryForList("SHOW TABLES", String.class);
        
        for (String table : tables) {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + table, 
                Integer.class
            );
            System.out.println("Tabela: " + table + " - " + count + " registros");
        }
    }
}