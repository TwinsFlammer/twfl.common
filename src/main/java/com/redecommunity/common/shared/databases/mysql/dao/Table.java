package com.redecommunity.common.shared.databases.mysql.dao;

import com.redecommunity.common.Common;
import com.redecommunity.common.shared.databases.mysql.data.MySQL;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Table implements ITable {
    private final String tableName;
    private final String databaseName;

    @Override
    public void createTable() throws SQLException {
        // TODO auto-generated method stub
    }

    private MySQL getMySQL() {
        return Common.getInstance().getDatabaseManager().getMySQLManager().getDatabase(this.databaseName);
    }

    public boolean execute(String query) throws SQLException {
        return this.getMySQL().execute(query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.getMySQL().prepareStatement(query);
    }

    @Override
    public String getTableName() throws SQLException {
        return this.tableName;
    }

    @Override
    public <T> void insert(T object) throws SQLException {
        // TODO auto-generated method stub
    }

    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) throws SQLException {
        // TODO auto-generated method stub
    }

    public <K, V> void delete(K key, V value) throws SQLException {
        // TODO auto-generated method stub
    }

    public <K, V, T> T findOne(K key, V value) throws SQLException {
        return null;
    }

    public <T> Set<T> findAll() throws SQLException {
        return null;
    }
}