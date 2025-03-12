package com.ezepsosa.marcusbike.config;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private static final ThreadLocal<Connection> connectionThread = new ThreadLocal<>();

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
            connection.commit();
            connection.close();
            connectionThread.remove();
        }
    }

    public static void rollbackTransaction() {
        Connection conn = connectionThread.get();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Rollback error", e);
            } finally {
                connectionThread.remove();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionThread.get();
        return (conn != null) ? conn : HikariDatabaseConfig.getConnection();
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && connectionThread.get() == null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when closing the active connection", e);
        }
    }
}