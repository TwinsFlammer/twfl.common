package com.redecommunity.common.shared.permissions.group.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.permissions.group.data.Group;

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
}
