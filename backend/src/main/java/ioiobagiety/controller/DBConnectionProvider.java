package ioiobagiety.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
    public static final String DB_URL = "jdbc:h2:mem:iobagiety";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "";
    private static Logger logger = LoggerFactory.getLogger(DBConnectionProvider.class);

    public static Connection provideConnection() throws SQLException {

        logger.debug("Connected to database");
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
