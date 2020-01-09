package com.redecommunity.common.shared.permissions.group.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.permission.dao.PermissionDao;
import com.redecommunity.common.shared.permissions.permission.data.Permission;
import com.redecommunity.common.shared.permissions.group.dao.GroupDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class GroupManager {
    private static List<Group> groups = Lists.newArrayList();

    public GroupManager() {
        GroupDao groupDao = new GroupDao();
        PermissionDao permissionDao = new PermissionDao();

        Set<Group> groups1 = groupDao.findAll();

        GroupManager.groups.addAll(groups1);

        Set<Permission> permissions = permissionDao.findAll();

        permissions.forEach(permission -> {
            Integer groupId = permission.getGroupId();

            Group group = GroupManager.getGroup(groupId);

            group.getPermissions().add(permission);

            if (permission.grantToHigher())
                GroupManager.groups
                        .stream()
                        .filter(group1 -> group1.getPriority() > group.getPriority())
                        .forEach(group1 -> group1.getPermissions().add(permission));
        });
    }

    public static List<Group> getGroups() {
        return GroupManager.groups;
    }

    public static void setGroups(List<Group> groups) {
        GroupManager.groups = groups;
    }

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
                resultSet.getString("color"),
                resultSet.getInt("priority"),
                resultSet.getInt("tab_list_order"),
                resultSet.getLong("discord_group_id"),
                resultSet.getInt("server_id"),
                Lists.newArrayList()
        );
    }
}