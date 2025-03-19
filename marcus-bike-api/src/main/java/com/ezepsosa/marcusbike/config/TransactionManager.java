package com.ezepsosa.marcusbike.config;

import java.sql.Connection;
import java.sql.SQLException;

// Manages database transactions using a thread-local connection.
public class TransactionManager {

    private static final ThreadLocal<Connection> connectionThread = ThreadLocal.withInitial(() -> null);

    public static void beginTransaction() throws SQLException {
        if (connectionThread.get() == null) {
            Connection connection = HikariDatabaseConfig.getConnection();
            connection.setAutoCommit(false);
            connectionThread.set(connection);
        }
    }

    public static void commitTransaction() throws SQLException {
        Connection connection = connectionThread.get();
        if (connection != null) {
            try {
                connection.commit();
            } finally {
                try {
                    connection.close();
                } finally {
                    connectionThread.remove();
                }
            }
        }
    }

    public static void rollbackTransaction() throws SQLException {
        Connection connection = connectionThread.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Rollback error", e);
            } finally {
                try {
                    connection.close();
                } finally {
                    connectionThread.remove();
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = connectionThread.get();
        if (connection == null || connection.isClosed()) {
            connection = HikariDatabaseConfig.getConnection();
            connectionThread.set(connection);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null && connectionThread.get() == connection) {
            connectionThread.remove();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }
}
