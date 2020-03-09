package com.redecommunity.common.shared.cooldown.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.cooldown.data.Cooldown;
import com.redecommunity.common.shared.permissions.user.data.User;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class CooldownManager {
    private static List<Cooldown> cooldowns = Lists.newArrayList();

    public static List<Cooldown> getCooldowns() {
        return CooldownManager.cooldowns;
    }

    public static <T> void startCooldown(User user, Long duration, T clazz) {
        Cooldown cooldown = new Cooldown(
                user.getId(),
                System.currentTimeMillis() + duration,
                clazz
        );

        CooldownManager.cooldowns.add(cooldown);
    }

    public static <T> Boolean inCooldown(User user, T clazz) {
        return CooldownManager.cooldowns
                .stream()
                .anyMatch(cooldown -> {
                    if (cooldown.getUserId().equals(user.getId()) && cooldown.getClazz().equals(clazz))
                        return cooldown.inCooldown();

                    return false;
                });
    }
}
