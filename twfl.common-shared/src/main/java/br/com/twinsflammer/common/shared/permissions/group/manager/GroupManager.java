package br.com.twinsflammer.common.shared.permissions.group.manager;

import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.group.dao.GroupDao;
import br.com.twinsflammer.common.shared.permissions.group.data.Group;
import br.com.twinsflammer.common.shared.permissions.permission.dao.PermissionDao;
import br.com.twinsflammer.common.shared.permissions.permission.data.Permission;
import com.google.common.collect.Lists;

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

        if (groups1.isEmpty()) {
            Group MASTER = GroupManager.generateGroup(1, GroupNames.MASTER, "[Mestre] ", "", "6", 100, 1, -1L, 0),
                    DIRECTOR = GroupManager.generateGroup(2, GroupNames.DIRECTOR, "[Diretor] ", "", "3", 95, 2, -1L, 0),
                    ADMINISTRATOR = GroupManager.generateGroup(3, GroupNames.ADMINISTRATOR, "[Admin] ", "", "c", 90, 3, -1L, 0),
                    MODERATOR = GroupManager.generateGroup(4, GroupNames.MODERATOR, "[Moderador] ", "", "2", 85, 4, -1L, 0),
                    HELPER = GroupManager.generateGroup(5, GroupNames.HELPER, "[Ajudante] ", "", "a", 80, 5, -1L, 0),
                    YOUTUBER = GroupManager.generateGroup(6, GroupNames.YOUTUBER, "[Youtuber] ", "", "b", 75, 6, -1L, 0),
                    NOBLE = GroupManager.generateGroup(7, GroupNames.NOBLE, "[Nobre] ", "", "4", 75, 6, -1L, 0),
                    KNIGHT = GroupManager.generateGroup(8, GroupNames.KNIGHT, "[Cavaleiro] ", "", "3", 75, 6, -1L, 0),
                    FARMER = GroupManager.generateGroup(9, GroupNames.FARMER, "[Camponês] ", "", "9", 75, 6, -1L, 0),
                    DEFAULT = GroupManager.generateGroup(15, GroupNames.DEFAULT, "", "", "7", 0, 15, -1L, 0);

            groupDao.insert(
                    MASTER,
                    DIRECTOR,
                    ADMINISTRATOR,
                    MODERATOR,
                    HELPER,
                    YOUTUBER,
                    NOBLE,
                    KNIGHT,
                    FARMER,
                    DEFAULT
            );
        }

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

    private static Group generateGroup(Integer id, String name, String prefix, String suffix, String color, Integer priority, Integer tabListOrder, Long discordGroupId, Integer serverId) {
        return new Group(
                id,
                name,
                prefix,
                suffix,
                color,
                priority,
                tabListOrder,
                discordGroupId,
                serverId,
                Lists.newArrayList()
        );
    }
}