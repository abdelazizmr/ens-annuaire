package com.ens.ensannuaire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ens_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        return connection;
    }

}
