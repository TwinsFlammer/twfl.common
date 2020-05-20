package br.com.twinsflammer.common.shared.preference.dao;

import br.com.twinsflammer.common.shared.preference.Preference;
import br.com.twinsflammer.common.shared.databases.mysql.dao.Table;
import br.com.twinsflammer.common.shared.permissions.user.data.User;

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

    public <T extends User, K extends Preference, V> void insert(T object) {
        StringBuilder keys1 = new StringBuilder();
        StringBuilder values1 = new StringBuilder();

        Preference[] preferences = Preference.values();

        for (int i = 0; i < preferences.length; i++) {
            Preference preference = preferences[i];

            keys1.append("`")
                    .append(preference.getColumnName())
                    .append("`")
                    .append(i + 1 >= preferences.length ? "" : ",");
        }

        for (int i = 0; i < preferences.length; i++) {
            Preference preference = preferences[i];

            Boolean enabled = object.isDisabled(preference);

            values1.append(enabled)
                    .append(i + 1 >= preferences.length ? "" : ",");
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

    public <K, V, U extends User> void update(HashMap<K, V> keys, U user) {
        String where = this.generateWhere(keys);

        String query = String.format(
                "UPDATE %s SET %s WHERE `user_id`=%d;",
                this.getTableName(),
                where,
                user.getId()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            Integer affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0)
                this.insert(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
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
