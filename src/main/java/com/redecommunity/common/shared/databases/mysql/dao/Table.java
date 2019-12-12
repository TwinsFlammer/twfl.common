package com.redecommunity.common.shared.databases.mysql.dao;

import com.redecommunity.common.shared.Common;
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
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public <T> void insert(T object) {
        // TODO auto-generated method stub
    }

    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
        // TODO auto-generated method stub
    }

    public <K, V> void delete(K key, V value) {
        // TODO auto-generated method stub
    }

    public <K, V, T> T findOne(K key, V value) {
        return null;
    }

    public <T> Set<T> findAll() {
        return null;
    }
}