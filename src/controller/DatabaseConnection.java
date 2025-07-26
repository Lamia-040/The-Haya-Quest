package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/the_haya_quest";
    private static final String USER = "sadmin";
    private static final String PASSWORD = "5Z4gf16Y)}i";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Optional: test main
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Database connection successful!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed:");
            e.printStackTrace();
        }
    }
}
