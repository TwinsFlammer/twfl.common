package br.com.twinsflammer.common.shared.permissions.user.group.dao;

import br.com.twinsflammer.common.shared.databases.mysql.dao.Table;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import com.google.common.collect.Sets;
import br.com.twinsflammer.common.shared.permissions.user.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return "server_user_group";
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

    public <T extends UserGroup, U extends User> void insert(U user, T object) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`group_id`," +
                        "`server_id`," +
                        "`end_time`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d," +
                        "%d," +
                        "%d" +
                        ")",
                this.getTableName(),
                user.getId(),
                object.getGroup().getId(),
                object.getServer() == null ? 0 : object.getServer().getId(),
                object.getDuration()
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

    public <U extends User, T extends UserGroup> void delete(U user, T object) {
        String query = String.format(
                "DELETE FROM %s WHERE `user_id`=%d AND `group_id`=%d AND `server_id`=%d",
                this.getTableName(),
                user.getId(),
                object.getGroup().getId(),
                object.getServer() == null ? 0 : object.getServer().getId()
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

    public <K extends Integer, V extends String, U, I, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `user_id`=%d %s;",
                this.getTableName(),
                key,
                value
        );

        System.out.println(query);

        Set<T> groups = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                UserGroup userGroup = UserGroup.toUserGroup(resultSet);

                groups.add((T) userGroup);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return groups;
    }
}
