package com.redecommunity.common.shared.databases.mysql.dao;

import com.redecommunity.common.shared.databases.mysql.data.MySQL;
import com.redecommunity.common.shared.Common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
        return Common.getInstance()
                .getDatabaseManager()
                .getMySQLManager()
                .getDatabase(this.getDatabaseName());
    }

    public <K, V> String generateWhere(HashMap<K, V> keys) {
        StringBuilder stringBuilder = new StringBuilder();

        Set<Map.Entry<K, V>> entry = keys.entrySet();

        Object[] entries = entry.toArray();

        for (int i = 0; i < entry.size(); i++) {
            Object object = entries[i];

            Map.Entry<K, V> entry1 = (Map.Entry<K, V>) object;

            K key1 = entry1.getKey();
            V value1 = entry1.getValue();

            stringBuilder.append("`")
                    .append(key1)
                    .append("`")
                    .append("=")
                    .append((value1 instanceof String ? "'" + value1 + "'" : value1));

            if ((i + 1) != entry.size()) stringBuilder.append(",")
                    .append(" ");
        }

        return stringBuilder.toString();
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