package com.redecommunity.common.shared.permissions.permission.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.permission.data.Permission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class PermissionDao extends Table {
    public PermissionDao() {
        super("server_permissions", "general");
    }

    @Override
    public void createTable() throws SQLException {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(50) NOT NULL," +
                                "`group_id` INTEGER NOT NULL," +
                                "`grant_to_higher` BOOLEAN NOT NULL," +
                                "`server_id` INTEGER NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    @Override
    public <T> Set<T> findAll() throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement(
                String.format(
                        "SELECT * FROM %s",
                        this.getTableName()
                )
        );

        Set<T> permissions = Sets.newConcurrentHashSet();

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Permission permission = new Permission(
                    resultSet.getString("name"),
                    resultSet.getInt("group_id"),
                    resultSet.getBoolean("grant_to_higher"),
                    resultSet.getInt("server_id")
            );

            permissions.add((T) permission);
        }

        return permissions;
    }
}
