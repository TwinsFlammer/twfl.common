package com.redefocus.common.shared.cooldown.manager;

import com.google.common.collect.Lists;
import com.redefocus.common.shared.cooldown.data.Cooldown;
import com.redefocus.common.shared.permissions.user.data.User;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class CooldownManager {
    private static List<Cooldown> cooldowns = Lists.newArrayList();

    public static List<Cooldown> getCooldowns() {
        return CooldownManager.cooldowns;
    }

    public static <T> void startCooldown(User user, Long duration, T object) {
        Cooldown cooldown = new Cooldown(
                user.getId(),
                System.currentTimeMillis() + duration,
                object
        );

        CooldownManager.cooldowns.add(cooldown);
    }

    public static <T> Boolean inCooldown(User user, T object) {
        return CooldownManager.cooldowns
                .stream()
                .anyMatch(cooldown -> {
                    if (cooldown.getUserId().equals(user.getId()) && cooldown.getClazz().equals(object))
                        return cooldown.inCooldown();

                    return false;
                });
    }

    public static <T> Long getRemainingTime(User user, T object) {
        return CooldownManager.cooldowns
                .stream()
                .filter(cooldown -> {
                    if (cooldown.getUserId().equals(user.getId()) && cooldown.getClazz().equals(object))
                        return cooldown.inCooldown();

                    return false;
                })
                .findFirst()
                .map(cooldown -> cooldown.getEndTime() - System.currentTimeMillis())
                .orElseGet(System::currentTimeMillis);
    }

    private static void removeExpired() {
        CooldownManager.cooldowns.removeIf(cooldown ->
                !cooldown.inCooldown()
        );
    }
}
