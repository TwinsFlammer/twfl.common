package br.com.twinsflammer.common.shared.permissions.user.dao;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.databases.mysql.dao.Table;
import br.com.twinsflammer.common.shared.permissions.user.group.dao.UserGroupDao;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class UserDao extends Table {

    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_user";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s" +
                                "(" +
                                "`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(16) NOT NULL," +
                                "`display_name` VARCHAR(16) NOT NULL," +
                                "`unique_id` VARCHAR(255) NOT NULL," +
                                "`email` VARCHAR(255)," +
                                "`password` VARCHAR(255)," +
                                "`cash` INTEGER NOT NULL," +
                                "`discord_id` LONG," +
                                "`two_factor_authentication_enabled` BOOLEAN," +
                                "`two_factor_authentication_code` VARCHAR(255)," +
                                "`twitter_access_token` VARCHAR(255)," +
                                "`twitter_token_secret` VARCHAR(255)," +
                                "`created_at` LONG NOT NULL," +
                                "`first_login` LONG," +
                                "`last_login` LONG," +
                                "`last_address` VARCHAR(255)," +
                                "`last_lobby_id` INTEGER," +
                                "`language_id` INTEGER" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <T extends User> void insert(T user) {
        String query = String.format(
                "INSERT INTO %s (" +
                        "`name`," +
                        "`display_name`," +
                        "`unique_id`," +
                        "`cash`," +
                        "`created_at`," +
                        "`language_id`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "'%s'," +
                        "'%s'," +
                        "'%s'," +
                        "%d," +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                user.getName().toLowerCase(),
                user.getDisplayName(),
                user.getUniqueId(),
                user.getCash(),
                user.getCreatedAt(),
                user.getLanguageId()
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

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
        String where = this.generateWhere(keys);

        String query = String.format(
                "UPDATE %s SET %s WHERE `%s`=%s",
                this.getTableName(),
                where,
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public <K, V> void delete(K key, V value) {
        String query = String.format(
                "DELETE FROM %s WHERE `%s`=%s",
                this.getTableName(),
                key,
                value
        );

        this.execute(query);
    }

    public <K, V, T> T findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`='%s'",
                this.getTableName(),
                key,
                value
        );

        UserGroupDao userGroupDao = new UserGroupDao();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (!resultSet.next()) return null;

            User user = UserManager.toUser(resultSet);

            if (user.isConsole()) {
                Set<UserGroup> groups = userGroupDao.findAll(
                        user.getId(),
                        ""
                );

                user.getGroups().addAll(groups);
            }

            return (T) user;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> Set<T> findAll() {
        String query = String.format(
                "SELECT * FROM %s",
                this.getTableName()
        );

        UserGroupDao userGroupDao = new UserGroupDao();

        Set<T> users = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                User user = UserManager.toUser(resultSet);

                HashMap<String, Object> keys = Maps.newHashMap();

                keys.put("user_id", user.getId());

                Set<UserGroup> groups = userGroupDao.findAll(keys);

                user.getGroups().addAll(groups);

                users.add((T) user);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return users;
    }
}
