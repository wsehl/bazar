package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.NoDatabaseConnectionException;

public class DBConnector {
    static final String DB_URL = "jdbc:postgresql://194.87.106.167/marketplace";
    static final String USER = "postgres";
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
