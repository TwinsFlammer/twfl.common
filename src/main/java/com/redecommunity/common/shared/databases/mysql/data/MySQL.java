package com.redecommunity.common.shared.databases.mysql.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class MySQL {
    protected final String host, user, password, database;
    @Getter
    protected Connection connection;
    private Statement statement;

    public void start() throws SQLException {
        this.connection = DriverManager.getConnection(
                String.format(
                        "jdbc:mysql://%s/%s",
                        this.host,
                        this.database
                ),
                this.user,
                this.password
        );
        this.statement = this.connection.createStatement();
    }

    public void refresh() throws SQLException {
        if (this.isClosed()) this.start();
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query);
    }

    public boolean execute(String query) throws SQLException {
        return this.statement.execute(query);
    }

    private boolean isClosed() throws SQLException {
        return this.connection == null || this.connection.isClosed();
    }
}
