package com.redecommunity.common.shared.permissions.user.group.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class UserGroupDao extends Table {

    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_users_groups";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`group_id` INTEGER NOT NULL," +
                                "`server_id` INTEGER NOT NULL," +
                                "`end_time` LONG NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <K, V, U, I, T> Set<T> findAll(HashMap<K, V> keys) {
        String where = this.generateWhere(keys);

        String query = String.format(
                "SELECT * FROM %s WHERE %s",
                this.getTableName(),
                where
        );

        Set<T> groups = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Group group = GroupManager.getGroup(resultSet.getInt("group_id"));

                groups.add((T) group);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return groups;
    }
}
