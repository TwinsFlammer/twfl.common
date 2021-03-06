package br.com.twinsflammer.common.shared.permissions.user.manager;

import br.com.twinsflammer.common.shared.friend.storage.FriendStorage;
import br.com.twinsflammer.common.shared.ignored.storage.IgnoredStorage;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.group.data.Group;
import br.com.twinsflammer.common.shared.permissions.user.dao.UserDao;
import br.com.twinsflammer.common.shared.preference.Preference;
import br.com.twinsflammer.common.shared.preference.dao.PreferenceDao;
import br.com.twinsflammer.common.shared.report.data.ReportReason;
import br.com.twinsflammer.common.shared.skin.dao.SkinDao;
import br.com.twinsflammer.common.shared.skin.data.Skin;
import com.google.common.collect.Lists;
import br.com.twinsflammer.common.shared.permissions.user.group.dao.UserGroupDao;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import br.com.twinsflammer.common.shared.permissions.group.manager.GroupManager;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.report.dao.UserReportDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class UserManager {
    private static List<User> users = Lists.newArrayList();

    public UserManager() {
        UserDao userDao = new UserDao();

        User user = UserManager.getUser("console");

        if (user == null) {
            user = UserManager.generateUser(
                    "CONSOLE",
                    UUID.fromString("00000000-0000-0000-0000-000000000000")
            );

            userDao.insert(user);

            Group group = GroupManager.getGroup(GroupNames.MASTER);

            UserGroup userGroup = new UserGroup(
                    group,
                    null,
                    -1L
            );

            UserGroupDao userGroupDao = new UserGroupDao();

            userGroupDao.insert(
                    user,
                    userGroup
            );
        }
    }

    public static List<User> getUsers() {
        return UserManager.users;
    }

    public static void unloadUser(Integer userId) {
        UserManager.users.removeIf(user -> user != null && userId.equals(user.getId()));
    }

    @Deprecated
    public static void removeUser(Integer userId) {
        UserManager.unloadUser(userId);
    }

    public static List<User> getOnlineUsers() {
        return UserManager.users
                .stream()
                .filter(User::isOnline)
                .collect(Collectors.toList());
    }


    public static User getUser(Integer id, Boolean download) {
        return download ? UserManager.findOne("id", id) : UserManager.getUser(id);
    }

    public static User getUser(String name, Boolean download) {
        return download ? UserManager.findOne("name", name.toLowerCase()) : UserManager.getUser(name);
    }

    public static User getUser(UUID uniqueId, Boolean download) {
        return download ? UserManager.findOne("unique_id", uniqueId) : UserManager.getUser(uniqueId);
    }

    public static User getUser(Integer id) {
        return UserManager.users.
                stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseGet(() -> UserManager.findOne("id", id));
    }

    public static User getUser(String name) {
        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> UserManager.findOne("name", name.toLowerCase()));
    }

    public static User getUser(UUID uniqueId) {
        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getUniqueId().equals(uniqueId))
                .findFirst()
                .orElseGet(() -> UserManager.findOne("unique_id", uniqueId));
    }

    private static <K, V> User findOne(K key, V value) {
        UserDao userDao = new UserDao();

        User user = userDao.findOne(key, value);

        if (user != null) {
            PreferenceDao preferenceDao = new PreferenceDao();

            Set<Preference> preferences = preferenceDao.findAll("user_id", user.getId());

            if (preferences != null) user.getPreferences().addAll(preferences);

            FriendStorage friendStorage = new FriendStorage();
            IgnoredStorage ignoredStorage = new IgnoredStorage();

            Set<Integer> friends = friendStorage.findAll("user_id", user.getId());
            Set<Integer> ignored = ignoredStorage.findAll("user_id", user.getId());

            user.getFriends().addAll(friends);
            user.getIgnored().addAll(ignored);

            UserReportDao userReportDao = new UserReportDao();

            Set<ReportReason> reports = userReportDao.findAll("user_id", user.getId());

            user.getReports().addAll(reports);

            SkinDao skinDao = new SkinDao();

            Set<Skin> skins = skinDao.findAll("user_id", user.getId());

            user.getSkins().addAll(skins);

            UserManager.users.removeIf(user1 -> {
                if (value instanceof Integer ) {
                    return user1.getId().equals(value);
                }

                if (value instanceof UUID) {
                    return user1.getUniqueId().equals(value);
                }

                return user1.getName().equalsIgnoreCase((String) value);
            });

            UserManager.users.add(user);
        }

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
                0,
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
                null,
                null,
                null,
                null,
                false,
                false
        );
    }

    public static List<User> getUsers(Group group) {
        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.hasGroup(group))
                .collect(Collectors.toList());
    }

    public static List<User> getUsers(String groupName) {
        Group group = GroupManager.getGroup(groupName);

        return UserManager.users
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.hasGroup(group))
                .collect(Collectors.toList());
    }

    public static User toUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("display_name"),
                UUID.fromString(resultSet.getString("unique_id")),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getInt("cash"),
                resultSet.getLong("discord_id"),
                resultSet.getBoolean("two_factor_authentication_enabled"),
                resultSet.getString("two_factor_authentication_code"),
                resultSet.getLong("created_at"),
                resultSet.getLong("first_login"),
                resultSet.getLong("last_login"),
                resultSet.getString("last_address"),
                resultSet.getInt("last_lobby_id"),
                resultSet.getInt("language_id"),
                resultSet.getString("twitter_access_token"),
                resultSet.getString("twitter_token_secret"),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Lists.newArrayList(),
                false,
                false
        );
    }
}
