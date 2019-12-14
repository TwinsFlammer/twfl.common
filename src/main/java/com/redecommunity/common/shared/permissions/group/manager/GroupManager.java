package com.redecommunity.common.shared.permissions.group.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redecommunity.common.shared.permissions.group.data.Group;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by @SrGutyerrez
 */
public class GroupManager {
    private static Collection<Group> groups = Lists.newArrayList();

    public static Group getGroup(Integer id) {
        return GroupManager.groups
                .stream()
                .filter(group -> group.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Group getGroup(String name) {
        return GroupManager.groups
                .stream()
                .filter(group -> group.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static Group toGroup(ResultSet resultSet) throws SQLException {
        return new Group(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("prefix"),
                resultSet.getString("suffix"),
                Color.getColor(resultSet.getString("color")),
                resultSet.getInt("priority"),
                resultSet.getInt("tab_list_order"),
                resultSet.getLong("discord_group_id"),
                resultSet.getInt("server_id"),
                Sets.newConcurrentHashSet()
        );
    }
}
