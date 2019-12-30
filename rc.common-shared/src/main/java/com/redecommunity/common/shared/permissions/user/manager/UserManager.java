package com.redecommunity.common.shared.permissions.user.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.friend.database.FriendDatabase;
import com.redecommunity.common.shared.ignored.database.IgnoredDatabase;
import com.redecommunity.common.shared.permissions.user.dao.UserDao;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.preference.Preference;
import com.redecommunity.common.shared.preference.dao.PreferenceDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class UserManager {
    private static final List<User> users = Lists.newArrayList();

    public static List<User> getOnlineUsers() {
        return UserManager.users
                .stream()
                .filter(User::isOnline)
                .collect(Collectors.toList());
    }

    public static User getUser(Integer id) {
        return UserManager.users.
                stream()
                .filter(Objects::nonNull)
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .orElse(
                        UserManager.findOne("id", id)
                );
    }

    public static User getUser(String name) {
        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> name.equalsIgnoreCase(user.getName()))
                .findFirst()
                .orElse(
                        UserManager.findOne("name", name.toLowerCase())
                );
    }

    public static User getUser(UUID uniqueId) {
        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> uniqueId.equals(user.getUniqueId()))
                .findFirst()
                .orElse(
                        UserManager.findOne("unique_id", uniqueId)
                );
    }

    private static <K, V> User findOne(K key, V value) {
        UserDao userDao = new UserDao();

        User user = userDao.findOne(key, value);

        PreferenceDao preferenceDao = new PreferenceDao();

        Set<Preference> preferences = preferenceDao.findAll("user_id", user.getId());

        user.getPreferences().addAll(preferences);

        FriendDatabase friendDatabase = new FriendDatabase();
        IgnoredDatabase ignoredDatabase = new IgnoredDatabase();

        Set<Integer> friends = friendDatabase.findAll("user_id", user.getId());
        Set<Integer> ignored = ignoredDatabase.findAll("user_id", user.getId());

        user.getFriends().addAll(friends);
        user.getIgnored().addAll(ignored);

        UserManager.users.add(user);

        return user;
    }

    public static User generateUser(String username, UUID uniqueId) {
        return new User(
                null,
                username.toLowerCase(),
                username,
                uniqueId,
                null,
                null,
                null,
                System.currentTimeMillis(),
                null,
                null,
                null,
                null,
                1,
                null,
                null,
                null,
                null,
                false
        );
    }

    public static User toUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("display_name"),
                UUID.fromString(resultSet.getString("unique_id")),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getLong("discord_id"),
                resultSet.getLong("created_at"),
                resultSet.getLong("first_login"),
                resultSet.getLong("last_login"),
                resultSet.getString("last_address"),
                resultSet.getInt("last_lobby_id"),
                resultSet.getInt("language_id"),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                false
        );
    }
}
