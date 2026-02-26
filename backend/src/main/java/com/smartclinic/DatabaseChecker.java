package com.smartclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseChecker {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, Long> lastMaxIdMap = new HashMap<>();

    @Scheduled(fixedRate = 5000)
    public void checkAllTables() {

        List<String> tables = jdbcTemplate.queryForList("SHOW TABLES", String.class);

        for (String table : tables) {
            try {
                Long maxId = jdbcTemplate.queryForObject(
                        "SELECT MAX(id) FROM " + table, Long.class);

                Long lastMaxId = lastMaxIdMap.getOrDefault(table, 0L);

                if (maxId != null && maxId > lastMaxId) {
                    Integer newCount = jdbcTemplate.queryForObject(
                            "SELECT COUNT(*) FROM " + table + " WHERE id > ?",
                            Integer.class,
                            lastMaxId);

                    if (newCount != null && newCount > 0) {
                        System.out.println("Table: " + table + " - " + newCount + " new record(s)");

                        List<Map<String, Object>> newRows = jdbcTemplate.queryForList(
                                "SELECT * FROM " + table + " WHERE id > ?",
                                lastMaxId);
                        for (Map<String, Object> row : newRows) {
                            System.out.println(row);
                        }
                    }

                    lastMaxIdMap.put(table, maxId);
                }
            } catch (Exception e) {
                System.err.println("Error checking table " + table + ": " + e.getMessage());
            }
        }

        System.out.println("==============================================");
    }
}