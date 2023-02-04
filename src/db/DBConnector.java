package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.NoDatabaseConnectionException;

public class DBConnector {
    static final String DB_URL = "jdbc:postgresql://195.49.215.164/marketplace";
    static final String USER = "marketplace";
    static final String PASSWORD = "Qqwerty1!";

    private Connection connection;

    public DBConnector() throws Exception {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new NoDatabaseConnectionException("Can't connect to database");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
