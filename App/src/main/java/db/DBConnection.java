package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static volatile DBConnection dbConnection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";
    private Connection connection = null;

    private DBConnection (){
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        return (null == dbConnection) ? dbConnection = new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
