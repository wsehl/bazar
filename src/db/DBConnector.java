package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static final String DB_URL = "jdbc:postgresql://194.87.106.167/marketplace";
    static final String USER = "postgres";
    static final String PASSWORD = "Qqwerty1!";

    private Connection connection;

    public DBConnector() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
