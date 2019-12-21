package com.redecommunity.common.shared.server.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.server.dao.ServerDao;
import com.redecommunity.common.shared.server.data.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class ServerManager {
    private static List<Server> servers = Lists.newArrayList();

    public ServerManager() {
        ServerDao serverDao = new ServerDao();

        Set<Server> servers = serverDao.findAll();

        ServerManager.servers.addAll(servers);
    }

    public static Boolean addServer(Server server) {
        return ServerManager.servers.add(server);
    }

    public static Server getServer(Integer id) {
        return ServerManager.servers
                .stream()
                .filter(Objects::nonNull)
                .filter(server -> id.equals(server.getId()))
                .findFirst()
                .orElse(null);
    }

    public static Server getServer(String name) {
        return ServerManager.servers
                .stream()
                .filter(Objects::nonNull)
                .filter(server -> name.equals(server.getName()))
                .findFirst()
                .orElse(null);
    }

    public static List<Server> getLobbies() {
        return ServerManager.servers
                .stream()
                .filter(Objects::nonNull)
                .filter(Server::isLobby)
                .filter(Server::isOnline)
                .collect(Collectors.toList());
    }

    public static List<Server> getFactions() {
        return ServerManager.servers
                .stream()
                .filter(Objects::nonNull)
                .filter(Server::isFactions)
                .filter(Server::isOnline)
                .collect(Collectors.toList());
    }

    public static List<Server> getLoginServers() {
        return ServerManager.servers
                .stream()
                .filter(Objects::nonNull)
                .filter(Server::isLoginServer)
                .filter(Server::isOnline)
                .collect(Collectors.toList());
    }

    public static Server toServer(ResultSet resultSet) throws SQLException {
        return new Server(
                resultSet.getInt("id"),
                resultSet.getInt("slots"),
                resultSet.getInt("port"),
                resultSet.getInt("status"),
                resultSet.getString("name"),
                resultSet.getString("display_name"),
                resultSet.getString("description"),
                resultSet.getString("address"),
                0,
                false,
                new Integer[] { 0, 1, 2 }
        );
    }
}
