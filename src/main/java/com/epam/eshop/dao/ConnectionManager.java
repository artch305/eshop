package com.epam.eshop.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by artch on 23.09.2020.
 */
public class ConnectionManager {

    private static ConnectionManager instance;
    private DataSource dataSource;
    private static final String HOLDER = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    private ConnectionManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MyDB");
        } catch (NamingException e) {
            LOGGER.error("Can't get DataSource", e);
            throw new RuntimeException("cannot obtain DataSource", e);
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (HOLDER) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Can't get Connection", e);
        }
        return connection;
    }
}
