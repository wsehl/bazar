package models;

import java.sql.Connection;

import db.DBConnector;

public class Controller {
    private Connection connection;

    public Controller() throws Exception {
        DBConnector dbConnector = new DBConnector();
        this.connection = dbConnector.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}
