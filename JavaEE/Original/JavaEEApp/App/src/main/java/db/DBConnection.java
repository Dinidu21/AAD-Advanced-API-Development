package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static volatile DBConnection dbConnection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";
    private final Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            synchronized (DBConnection.class) {
                if (dbConnection == null) {
                    dbConnection = new DBConnection();
                }
            }
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}