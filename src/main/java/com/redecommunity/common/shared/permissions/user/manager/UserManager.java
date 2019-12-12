package com.redecommunity.common.shared.permissions.user.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class UserManager {
    private static final Collection<User> users = Lists.newArrayList();

    public static User getUser(Integer id) {
        return UserManager.users.
                stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static User getUser(String name) {
        return UserManager.users
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static User getUser(UUID uniqueId) {
        return UserManager.users
                .stream()
                .filter(user -> user.getUniqueId().equals(uniqueId))
                .findFirst()
                .orElse(null);
    }
}
