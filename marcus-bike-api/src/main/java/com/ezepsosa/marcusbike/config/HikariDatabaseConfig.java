package com.ezepsosa.marcusbike.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDatabaseConfig {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/marcusbike");
        config.setUsername("postgres");
        config.setPassword("admin");
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(4);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private HikariDatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
