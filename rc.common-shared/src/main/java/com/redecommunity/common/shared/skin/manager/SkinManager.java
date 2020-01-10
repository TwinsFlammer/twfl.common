package com.redecommunity.common.shared.skin.manager;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class SkinManager {
    public static Skin getSkin(String owner) {
        List<User> users = UserManager.getUsers();

        User user = users.stream()
                .filter(user1 -> user1.getSkins()
                        .stream()
                        .anyMatch(skin -> skin.getOwner().equalsIgnoreCase(owner))
                )
                .findFirst()
                .orElse(null);

        return user == null ? SkinManager.findOne(owner) : user.getSkins()
                .stream()
                .filter(skin -> skin.getOwner().equalsIgnoreCase(owner))
                .findFirst()
                .orElse(null);
    }

    private static Skin findOne(String owner) {
        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("owner", owner);

        SkinDao skinDao = new SkinDao();

        return skinDao.findOne(keys);
    }

    public static Skin toSkin(ResultSet resultSet) throws SQLException {
        return new Skin(
                resultSet.getInt("id"),
                resultSet.getString("signature"),
                resultSet.getString("value"),
                resultSet.getLong("last_use"),
                resultSet.getBoolean("active"),
                resultSet.getString("owner")
        );
    }
}
