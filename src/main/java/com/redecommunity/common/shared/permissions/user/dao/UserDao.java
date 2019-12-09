package com.redecommunity.common.shared.permissions.user.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class UserDao extends Table {
    public UserDao() {
        super("server_users", "general");
    }

    @Override
    public void createTable() throws SQLException {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s" +
                                "(" +
                                "`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(16) NOT NULL," +
                                "`display_name` VARCHAR(16) NOT NULL," +
                                "`unique_id` VARCHAR(255) NOT NULL," +
                                "`email` VARCHAR(255)," +
                                "`discord_id` LONG," +
                                "`created_at` LONG NOT NULL," +
                                "`first_login` LONG," +
                                "`last_login` LONG," +
                                "`last_address` VARCHAR(255)," +
                                "`last_lobby_id` INTEGER," +
                                "`lang_id` INTEGER" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <T extends User> void insert(T user) throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement(
                String.format(
                        "INSERT INTO %s (" +
                                "`name`," +
                                "`display_name`," +
                                "`unique_id`," +
                                "`created_at`," +
                                "`lang_id`" +
                                ")" +
                                " VALUES " +
                                "(" +
                                "'%s'," +
                                "'%s'," +
                                "'%s'," +
                                "%d," +
                                "%d" +
                                ");",
                        this.getTableName(),
                        user.getName().toLowerCase(),
                        user.getDisplayName(),
                        user.getUniqueId(),
                        user.getCreatedAt(),
                        user.getLangId()
                )
        );

        preparedStatement.executeQuery();
    }

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) throws SQLException {
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

        this.execute(
                String.format(
                        "UPDATE %s SET %s WHERE `%s`=%s",
                        this.getTableName(),
                        stringBuilder.toString(),
                        key,
                        value
                )
        );
    }

    @Override
    public <K, V> void delete(K key, V value) throws SQLException {
        this.execute(
                String.format(
                        "DELETE FROM %s WHERE `%s`=%s",
                        this.getTableName(),
                        key,
                        value
                )
        );
    }

    @Override
    public <K, V, T> T findOne(K key, V value) throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement(
                String.format(
                        "SELECT * FROM %s WHERE `%s`='%s'",
                        this.getTableName(),
                        key,
                        value
                )
        );

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("display_name"),
                UUID.fromString(resultSet.getString("unique_id")),
                resultSet.getString("email"),
                resultSet.getLong("discord_id"),
                resultSet.getLong("created_at"),
                resultSet.getLong("first_login"),
                resultSet.getLong("last_login"),
                resultSet.getString("last_address"),
                resultSet.getInt("last_lobby_id"),
                resultSet.getInt("lang_id")
        );

        return (T) user;
    }

    @Override
    public <T> Set<T> findAll() throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement(
                String.format(
                        "SELECT * FROM %s",
                        this.getTableName()
                )
        );

        ResultSet resultSet = preparedStatement.executeQuery();

        Set<T> users = Sets.newConcurrentHashSet();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("display_name"),
                    UUID.fromString(resultSet.getString("unique_id")),
                    resultSet.getString("email"),
                    resultSet.getLong("discord_id"),
                    resultSet.getLong("created_at"),
                    resultSet.getLong("first_login"),
                    resultSet.getLong("last_login"),
                    resultSet.getString("last_address"),
                    resultSet.getInt("last_lobby_id"),
                    resultSet.getInt("lang_id")
            );

            users.add((T) user);
        }

        return users;
    }
}
