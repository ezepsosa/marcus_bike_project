package com.ezepsosa.marcusbike.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;

// Utility class for handling database transactions.  
// Provides methods for executing operations within a transaction, ensuring commit or rollback.
public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    // Starts a transaction, executes the provided function, commits the
    // transaction,
    // and ensures rollback on failure.
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

    // Rolls back the transaction in case of failure.
    private static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Rollback error", e);
            }
        }
    }

    // Closes the database connection and logs any errors.
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
