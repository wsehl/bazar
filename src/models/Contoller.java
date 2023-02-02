package models;

import java.sql.Connection;

import db.DBConnector;

public class Contoller {
    private Connection connection;

    public Contoller() {
        setConnection(new DBConnector());
    }

    public Contoller(DBConnector dbConnector) {
        setConnection(dbConnector);
    }

    private void setConnection(DBConnector dbConnector) {
        this.connection = dbConnector.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}
