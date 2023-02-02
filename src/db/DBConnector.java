package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static final String DB_URL = "jdbc:postgresql://195.49.215.164/marketplace";
    static final String USER = "marketplace";
    static final String PASSWORD = "marketplace";

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
