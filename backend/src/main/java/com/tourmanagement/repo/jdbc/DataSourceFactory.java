package com.tourmanagement.repo.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.tourmanagement.config.AppConfig;

public class DataSourceFactory {
    private static HikariDataSource dataSource;

    public static synchronized HikariDataSource getDataSource(AppConfig.ProfileConfig config) {
        if (dataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUsername());
            hikariConfig.setPassword(config.getPassword());
            hikariConfig.setMaximumPoolSize(config.getPoolSize());
            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }
}
