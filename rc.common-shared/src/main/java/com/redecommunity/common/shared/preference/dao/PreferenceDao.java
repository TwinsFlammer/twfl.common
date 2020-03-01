package com.redecommunity.common.shared.preference.dao;

import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.preference.Preference;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class PreferenceDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_user_preference";
    }

    @Override
    public void createTable() {
        StringBuilder columns = new StringBuilder();

        Preference[] preferences = Preference.values();

        for (int i = 0; i < preferences.length; i++) {
            Preference preference = preferences[i];

            columns.append("`")
                    .append(preference.getColumnName())
                    .append("`")
                    .append(" ")
                    .append("INTEGER")
                    .append(" ")
                    .append("NOT")
                    .append(" ")
                    .append("NULL")
                    .append(" ")
                    .append("DEFAULT")
                    .append(" ")
                    .append("0")
                    .append((i + 1 == preferences.length ? "" : ","));
        }

        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "%s" +
                                ");",
                        this.getTableName(),
                        columns.toString()
                )
        );
    }

    public <T extends User, K, V> void insert(T object, HashMap<K, V> keys) {
        StringBuilder keys1 = new StringBuilder();
        StringBuilder values1 = new StringBuilder();

        int kIndex = 0;

        for (K key : keys.keySet()) {
            keys1.append("`")
                    .append(key)
                    .append("`")
                    .append(kIndex + 1 == keys.keySet().size() ? "" : ",");

            kIndex++;
        }

        int vIndex = 0;

        for (V value : keys.values()) {
            values1.append(value)
                    .append(vIndex + 1 == keys.values().size() ? "" : ",");

            vIndex++;
        }

        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "%s" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%s" +
                        ");",
                this.getTableName(),
                keys1.toString(),
                object.getId(),
                values1.toString()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d",
                this.getTableName(),
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) return Preference.toPreference(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
