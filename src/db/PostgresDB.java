package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import db.interfaces.IDB;
import exceptions.NoDatabaseConnectionException;

public class PostgresDB implements IDB {
    static final String DB_URL = "jdbc:postgresql://194.87.106.167/marketplace";
    static final String USER = "postgres";
    static final String PASSWORD = "Qqwerty1!";

    private Connection connection;

    public Connection getConnection() throws NoDatabaseConnectionException {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();

            throw new NoDatabaseConnectionException("Database connection error");
        }

        return connection;
    }
}