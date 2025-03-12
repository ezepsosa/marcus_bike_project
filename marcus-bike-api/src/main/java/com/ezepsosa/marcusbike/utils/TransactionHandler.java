package com.ezepsosa.marcusbike.utils;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.TransactionManager;

public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    public static <T> T startTransaction(Supplier<T> task) {
        try {
            TransactionManager.beginTransaction();

            T result = task.get();

            TransactionManager.commitTransaction();
            return result;
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            logger.error("Error in transacction. Rollback {}", e.getMessage());
            throw new RuntimeException("Error in transacction. Rollback {}", e);
        }
    }

    public static void executeWithTransactionStarted(Runnable task) {
        try {
            TransactionManager.beginTransaction();

            task.run();

            TransactionManager.commitTransaction();
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            logger.error("Error in transacction. Rollback {}", e.getMessage());
            throw new RuntimeException("Error in transacction. Rollback {}", e);
        }
    }
}
