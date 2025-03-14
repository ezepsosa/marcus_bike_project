package com.ezepsosa.marcusbike.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;

public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    public static <T> T startTransaction(Function<Connection, T> task) {
        Connection connection = null;
        try {
            connection = HikariDatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            T result = task.apply(connection);

            connection.commit();
            return result;
        } catch (Exception e) {
            rollbackTransaction(connection);
            throw new RuntimeException("Transaction failed, rollback executed.", e);
        } finally {
            closeConnection(connection);
        }
    }

    public static void executeWithTransactionStarted(Consumer<Connection> task) {
        Connection connection = null;
        try {
            connection = HikariDatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            task.accept(connection);

            connection.commit();
        } catch (Exception e) {
            rollbackTransaction(connection);
            throw new RuntimeException("Transaction failed, rollback executed.", e);
        } finally {
            closeConnection(connection);
        }
    }

    private static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Rollback error", e);
            }
        }
    }

    private static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection: {}", e.getMessage());
            }
        }
    }
}
