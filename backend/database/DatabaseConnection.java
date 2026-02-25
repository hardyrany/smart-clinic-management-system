package com.smartclinic.test;

import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/clinic";
        String user = "root";
        String password = "tarrafal92";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            // Verificar tabelas
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("=== TABELAS NO BANCO clinic ===");
            while (rs.next()) {
                System.out.println("→ " + rs.getString(1));
            }
            
            // Verificar registros em cada tabela
            String[] tables = {"doctors", "patients", "appointments", "doctor_available_times"};
            for (String table : tables) {
                rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table);
                rs.next();
                System.out.println("\n" + table + ": " + rs.getInt(1) + " registros");
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}