package com.redecommunity.common.shared.permissions.user.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.permissions.user.dao.UserDao;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class UserManager {
    private static final Collection<User> users = Lists.newArrayList();
    private static final UserDao userDao = new UserDao();

    public static User getUser(Integer id) {
        return UserManager.users.
                stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(
                        UserManager.findOne("id", id)
                );
    }

    public static User getUser(String name) {
        return UserManager.users
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(
                        UserManager.findOne("name", name)
                );
    }

    public static User getUser(UUID uniqueId) {
        return UserManager.users
                .stream()
                .filter(user -> user.getUniqueId().equals(uniqueId))
                .findFirst()
                .orElse(
                        UserManager.findOne("unique_id", uniqueId)
                );
    }

    private static <K, V> User findOne(K key, V value) {
        User user = UserManager.userDao.findOne(key, value);

        UserManager.users.add(user);

        return user;
    }

    public static User toUser(ResultSet resultSet) throws SQLException {
        return new User(
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
                resultSet.getInt("lang_id"),
                Lists.newArrayList()
        );
    }
}