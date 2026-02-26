package com.smartclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/tables")
    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        String query = "SHOW TABLES";
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            System.out.println("Found tables: " + tables);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return tables;
    }
}