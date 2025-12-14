package by.art.webprovider.model.pool;

import by.art.webprovider.exception.ConnectionPoolException;
import by.art.webprovider.util.ConfigManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {

    INSTANCE;

    private final Logger logger = LogManager.getLogger();
    private static final int POOL_SIZE = 8;
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenAwayConnections;
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";

    ConnectionPool() {
    }

    public void initPool() throws ConnectionPoolException {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        givenAwayConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        String driverName = ConfigManager.getProperty(DB_DRIVER);
        String url = ConfigManager.getProperty(DB_URL);
        String username = ConfigManager.getProperty(DB_USERNAME);
        String password = ConfigManager.getProperty(DB_PASSWORD);
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL driver not found", e);
            throw new ConnectionPoolException("MySQL driver not found", e);
        }
        logger.debug("MySQL driver loaded");
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.add(new ProxyConnection(connection));
            } catch (SQLException e) {
                logger.error("database access error, connection not received", e);
            }
        }
        logger.info("Database connection pool created");
        if (freeConnections.isEmpty()) {
            logger.error("connection pool initialization error");
            throw new ConnectionPoolException("connection pool initialization error");
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.put(connection);
        } catch (InterruptedException e) {
            logger.warn("Thread was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection &&
                givenAwayConnections.remove(connection)) {
            freeConnections.add((ProxyConnection) connection);
        } else {
            logger.warn("Connection is not instance of ProxyConnection");
        }
    }

    public void destroyPool() throws ConnectionPoolException {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
                logger.debug("Connection is closed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ConnectionPoolException("Thread was interrupted", e);
            } catch (SQLException e) {
                throw new ConnectionPoolException("Closing connection error", e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() throws ConnectionPoolException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.log(Level.DEBUG, "MySQL driver deregistered");
            } catch (SQLException e) {
                throw new ConnectionPoolException("driver deregistration error", e);
            }
        }
    }
}

