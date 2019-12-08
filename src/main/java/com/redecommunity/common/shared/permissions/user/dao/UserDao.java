package com.redecommunity.common.shared.permissions.user.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                                "`unique_id` UUID NOT NULL," +
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
    public <K, K1, V, V1> void update(K key1, V value1, K1 key2, V1 value2) throws SQLException {
        this.execute(
                String.format(
                        "UPDATE %s SET `%s`=%s WHERE `%s`=%s",
                        this.getTableName(),
                        key1,
                        value1,
                        key2,
                        value2
                )
        );
    }

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
