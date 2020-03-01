package com.redecommunity.common.shared.twitter.storage;

import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
public class TwitterStorage extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_twitter_request_token";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`oauth_token` VARCHAR(255) NOT NULL," +
                                "`oauth_verifier` VARCHAR(255)," +
                                "`generated_pin` VARCHAR(8)" +
                                ");",
                        this.getTableName()
                )
        );
    }

    @Override
    public <K, V, T> T findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`='%s'",
                this.getTableName(),
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("oauth_token", resultSet.getString("oauth_token"));
                jsonObject.put("oauth_verifier", resultSet.getString("oauth_verifier"));
                jsonObject.put("generated_pin", resultSet.getString("generated_pin"));

                return (T) jsonObject;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public <T extends User, O extends String> void insert(T object, O oAuthToken) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`oauth_token`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "'%s'" +
                        ");",
                this.getTableName(),
                object.getId(),
                oAuthToken
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
    public <K, V> void delete(K key, V value) {
        String query = String.format(
                "DELETE FROM %s WHERE `%s`='%s';",
                this.getTableName(),
                key,
                value
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
}
