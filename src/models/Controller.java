package models;

import java.sql.Connection;

import db.DBConnector;

public class Controller {
    private Connection connection;

    public Controller() throws Exception {
        setConnection(new DBConnector());
    }

    public Controller(DBConnector dbConnector) {
        setConnection(dbConnector);
    }

    private void setConnection(DBConnector dbConnector) {
        this.connection = dbConnector.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}
