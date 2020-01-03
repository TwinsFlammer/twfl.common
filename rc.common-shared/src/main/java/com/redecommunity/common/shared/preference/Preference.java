package com.redecommunity.common.shared.preference;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.permissions.user.data.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum Preference {
    VISIBILITY(
            "visibility",
            "Visibilidade dos jogadores",
            0,
            0,
            new String[]{}
    ),
    LOBBY_PROTECTION(
            "lobby_protection",
            "",
            0,
            0,
            new String[]{}
    ),
    BLOOD_EFFECT(
            "blood_effect",
            "",
            0,
            0,
            new String[]{}
    ),
    MENTION_NOTIFICATIONS(
            "mention_notifications",
            "",
            0,
            0,
            new String[]{}
    ),
    ACTION_BAR_ABILITIES(
            "action_bar_abilities",
            "",
            0,
            0,
            new String[]{}
    ),
    CHAT_LOCAL(
            "chat_local",
            "",
            0,
            0,
            new String[]{}
    ),
    CHAT_GLOBAL(
            "chat_global",
            "",
            0,
            0,
            new String[]{}
    ),
    PERSONAL_MARKET(
            "personal_market",
            "",
            0,
            0,
            new String[]{}
    ),
    TELEPORT_REQUEST(
            "teleport_request",
            "",
            0,
            0,
            new String[]{}
    ),
    MONEY_RECEIVE(
            "money_receive",
            "",
            0,
            0,
            new String[]{}
    ),
    UNDER_ATTACK_MESSAGE(
            "under_attack_message",
            "",
            0,
            0,
            new String[]{}
    ),
    MONEY_RECEIVED_MESSAGE(
            "money_received_message",
            "",
            0,
            0,
            new String[]{}
    ),
    COMBAT_MESSAGE(
            "combat_message",
            "",
            0,
            0,
            new String[]{}
    ),
    JOIN_MESSAGE(
            "join_message",
            "",
            0,
            0,
            new String[]{}
    ),
    PRIVATE_MESSAGE(
            "private_message",
            "",
            0,
            0,
            new String[]{}
    );

    @Getter
    private final String columnName, displayName;
    private final Integer id, data;
    private final String[] description;

    public static <T> Set<T> toPreference(ResultSet resultSet) throws SQLException {
        Set<T> preferences = Sets.newConcurrentHashSet();

        for (Preference preference : Preference.values())
            if (resultSet.getBoolean(preference.getColumnName())) preferences.add((T) preference);

        return preferences;
    }

    public String getColor(User user) {
        return user.isEnabled(this) ? "a" : "4";
    }
}
