package com.redecommunity.common.shared.permissions.group.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.group.data.Group;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class GroupDao extends Table {
    public GroupDao() {
        super("server_groups", "general");
    }

    @Override
    public void createTable() throws SQLException {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY," +
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

        try (PreparedStatement preparedStatement = this.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Group group = new Group(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("prefix"),
                        resultSet.getString("suffix"),
                        Color.getColor(resultSet.getString("color")),
                        resultSet.getInt("priority"),
                        resultSet.getInt("tab_list_order"),
                        resultSet.getLong("discord_group_id"),
                        resultSet.getInt("server_id"),
                        Sets.newConcurrentHashSet()
                );

                groups.add((T) group);
            }

            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return groups;
    }
}
