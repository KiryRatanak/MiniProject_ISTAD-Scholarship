package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/istad-database";
    private static final String USER = "postgres";
    private static final String PASS = "postgresql71";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Make sure the JAR is in the classpath!");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}