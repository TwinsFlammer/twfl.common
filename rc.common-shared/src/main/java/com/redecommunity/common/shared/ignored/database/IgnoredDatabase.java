package com.redecommunity.common.shared.ignored.database;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class IgnoredDatabase extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_user_ignored";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`ignored_id` INTEGER NOT NULL" +
                                ")",
                        this.getTableName()
                )
        );
    }

    public <T extends User, F extends Integer> void insert(T object, F friendId) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`ignored_id`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                object.getId(),
                friendId
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

    public <K, V extends Integer, F, I extends Integer> void delete(K key, V value, F key1, I value1) {
        String query = String.format(
                "DELETE FROM %s WHERE `%s`=%d AND `%s`=%d",
                this.getTableName(),
                key,
                value,
                key1,
                value1
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

    public <K, V, U, I extends Integer, T> Set<T> findAll(U key, I value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d",
                this.getTableName(),
                key,
                value
        );

        Set<T> friends = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Integer ignoredId = resultSet.getInt("ignored_id");

                friends.add((T) ignoredId);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return friends;
    }
}
