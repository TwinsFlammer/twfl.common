package com.redecommunity.common.shared.server.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class ServerDao extends Table {

    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_servers";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(100) NOT NULL," +
                                "`display_name` VHARCHAR(100) NOT NULL," +
                                "`description` TEXT," +
                                "`slots` INTEGER NOT NULL," +
                                "`address` VARCHAR(255) NOT NULL," +
                                "`port` INTEGER NOT NULL," +
                                "`status` INTEGER NOT NULL" +
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

        Set<T> servers = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            Server server = ServerManager.toServer(resultSet);

            servers.add((T) server);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return servers;
    }
}
