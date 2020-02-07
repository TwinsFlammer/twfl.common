package com.redecommunity.common.shared.permissions.group.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class GroupDao extends Table {

    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_group";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(25) NOT NULL," +
                                "`prefix` VARCHAR(25) NOT NULL," +
                                "`suffix` VARCHAR(25) NOT NULL," +
                                "`color` VARCHAR(15) NOT NULL," +
                                "`priority` INTEGER NOT NULL," +
                                "`tab_list_order` INTEGER NOT NULL," +
                                "`discord_group_id` LONG NOT NULL," +
                                "`server_id` INTEGER NOT NULL" +
                                ")",
                        this.getTableName()
                )
        );
    }

    @Override
    public <T> Set<T> findAll() {
        String query = String.format(
                "SELECT * FROM %s",
                this.getTableName()
        );

        Set<T> groups = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Group group = GroupManager.toGroup(resultSet);

                groups.add((T) group);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return groups;
    }
}
