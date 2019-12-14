package com.redecommunity.common.shared.databases.mysql.dao;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.data.MySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public abstract class Table implements ITable {

    @Override
    public abstract void createTable();

    public boolean execute(String query) {
        try (
                Connection connection = this.getMySQL().getConnection();
                Statement statement = connection.createStatement();
        ) {
            return statement.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.getMySQL().getConnection();
    }

    private MySQL getMySQL() {
        return Common.getInstance().getDatabaseManager().getMySQLManager().getDatabase(this.getDatabaseName());
    }

    @Override
    public abstract String getDatabaseName();

    @Override
    public abstract String getTableName();

    @Override
    public <T> void insert(T object) {
        // TODO auto-generated method stub
    }

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
        // TODO auto-generated method stub
    }

    @Override
    public <K, V> void delete(K key, V value) {
        // TODO auto-generated method stub
    }

    @Override
    public <K, V, T> T findOne(K key, V value) {
        return null;
    }

    @Override
    public <T> Set<T> findAll() {
        return null;
    }

    @Override
    public <K, V, U, I, T> Set<T> findAll(HashMap<K, V> keys) {
        return null;
    }
}