package br.com.twinsflammer.common.shared.skin.dao;

import br.com.twinsflammer.common.shared.skin.manager.SkinManager;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import br.com.twinsflammer.common.shared.databases.mysql.dao.Table;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.skin.data.Skin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class SkinDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_user_skin";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`texture` VARCHAR(8) NOT NULL," +
                                "`signature` TEXT NOT NULL," +
                                "`value` TEXT NOT NULL," +
                                "`last_use` LONG NOT NULL," +
                                "`active` BOOLEAN NOT NULL," +
                                "`owner` VARCHAR(16) NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <U extends User, S extends Skin> S insert(U user, S skin) {
        Long lastUse = System.currentTimeMillis();

        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`texture`," +
                        "`signature`," +
                        "`value`," +
                        "`last_use`," +
                        "`active`," +
                        "`owner`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "'%s'," +
                        "'%s'," +
                        "'%s'," +
                        "%d," +
                        "%b," +
                        "'%s'" +
                        ");",
                this.getTableName(),
                user.getId(),
                skin.getTexture(),
                skin.getSignature(),
                skin.getValue(),
                lastUse,
                true,
                skin.getOwner()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();

            HashMap<String, Object> keys = Maps.newHashMap();

            keys.put("user_id", user.getId());
            keys.put("texture", skin.getTexture());
            keys.put("signature", skin.getSignature());
            keys.put("value", skin.getValue());
            keys.put("last_use", lastUse);
            keys.put("active", true);
            keys.put("owner", skin.getOwner());

            Skin skin1 = this.findOne(keys);

            return (S) skin1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
        String where = this.generateWhere(keys);

        String query = String.format(
                "UPDATE %s SET %s WHERE `%s`=%d;",
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

    public <K, V, T> T findOne(HashMap<K, V> keys) {
        String where = this.generateWhere(keys).replaceAll(", ", " AND ");

        String query = String.format(
                "SELECT * FROM %s WHERE %s;",
                this.getTableName(),
                where
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) return (T) SkinManager.toSkin(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public <K, V, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        Set<T> skins = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Skin skin = SkinManager.toSkin(resultSet);

                skins.add((T) skin);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return skins;
    }
}
