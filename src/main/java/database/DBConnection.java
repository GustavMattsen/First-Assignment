package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            System.getenv().getOrDefault("JDBC_URL",
                    "jdbc:mysql://localhost:3306/todoit?useSSL=false&serverTimezone=UTC");

    private static final String USER =
            System.getenv().getOrDefault("JDBC_USER", "root");

    private static final String PASSWORD =
            System.getenv().getOrDefault("JDBC_PASSWORD", "1234");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
