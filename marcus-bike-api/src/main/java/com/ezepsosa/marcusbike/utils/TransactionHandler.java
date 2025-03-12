package com.ezepsosa.marcusbike.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;

public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    public static <T> T startTransaction(Supplier<T> task) {
        Connection connection = null;
        try {
            connection = HikariDatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            T result = task.get();

            connection.commit();
            connection.close();
            return result;
        } catch (Exception e) {
            rollbackTransaction(connection);
            logger.error("Error in transaction. Rollback executed: {}", e.getMessage());
            throw new RuntimeException("Error in transaction. Rollback executed.", e);
        }
    }

    public static void executeWithTransactionStarted(Runnable task) {
        Connection connection = null;
        try {
            connection = HikariDatabaseConfig.getConnection();
            connection.setAutoCommit(false); // Iniciar transacción

            task.run();

            connection.commit(); // Confirmar transacción
            connection.close();
        } catch (Exception e) {
            rollbackTransaction(connection);
            logger.error("Error in transaction. Rollback executed: {}", e.getMessage());
            throw new RuntimeException("Error in transaction. Rollback executed.", e);
        }
    }

    private static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Rollback error", e);
            }
        }
    }
}
