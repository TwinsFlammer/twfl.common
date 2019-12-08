package com.redecommunity.common.shared.databases.mysql.dao;

import com.redecommunity.common.Common;
import com.redecommunity.common.shared.databases.mysql.data.MySQL;
import com.redecommunity.common.shared.databases.mysql.manager.MySQLManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public abstract class Table implements TableImpl {
    private final String tableName;
    private final String databaseName;

    private MySQL mySQL = Common.getInstance().getDatabaseManager().getMySQLManager().getDatabase(databaseName);

    @Override
    public void createTable() throws SQLException {
        // TODO not implemented yet
    }

    public boolean execute(String query) throws SQLException {
        return this.mySQL.execute(query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.mySQL.prepareStatement(query);
    }

    @Override
    public String getTableName() throws SQLException {
        return this.tableName;
    }

    public <T> void insert(T object) throws SQLException {

    }

    public <K, V> void update(K key, V value) throws SQLException {

    }

    public <K, V> void delete(K key, V value) throws SQLException {

    }

    public <K, V, T> T findOne(K key, V value) throws SQLException {
        return null;
    }

    public <T> Set<T> findAll() throws SQLException {
        return null;
    }
}