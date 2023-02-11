package db.interfaces;

import java.sql.Connection;

import exceptions.NoDatabaseConnectionException;

public interface IDB {

    Connection getConnection() throws NoDatabaseConnectionException;
}