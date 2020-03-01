package com.redecommunity.common.shared.databases.mysql.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class MySQL {
    protected final String host, user, password, database;

    private HikariDataSource hikariDataSource;

    public void start() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(
                String.format(
                        "jdbc:mysql://%s/%s",
                        this.host,
                        this.database
                )
        );
        hikariConfig.setUsername(this.user);
        hikariConfig.setPassword(this.password);
        hikariConfig.addDataSourceProperty(
                "cachePrepStmts",
                "true"
        );
        hikariConfig.addDataSourceProperty(
                "prepStmtCacheSize",
                "250"
        );
        hikariConfig.addDataSourceProperty(
                "prepStmtCacheSqlLimit",
                "2048"
        );

        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void refresh() {
        if (this.isClosed()) this.start();
    }

    public Connection getConnection() throws SQLException {
        return this.hikariDataSource.getConnection();
    }

    private boolean isClosed() {
        return this.hikariDataSource == null || this.hikariDataSource.isClosed();
    }
}
