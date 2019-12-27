package com.redecommunity.common.shared.preference;

import com.google.common.collect.Sets;
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
    VISIBILITY("visibility"),
    LOBBY_PROTECTION("lobby_protection"),
    BLOOD_EFFECT("blood_effect"),
    MENTION_NOTIFICATIONS("mention_notifications"),
    ACTION_BAR_ABILITIES("action_bar_abilities"),
    CHAT_LOCAL("chat_local"),
    CHAT_GLOBAL("chat_global"),
    PERSONAL_MARKET("personal_market"),
    TELEPORT_REQUEST("teleport_request"),
    MONEY_RECEIVE("money_receive"),
    UNDER_ATTACK_MESSAGE("under_attack_message"),
    MONEY_RECEIVED_MESSAGE("money_received_message"),
    COMBAT_MESSAGE("combat_message"),
    JOIN_MESSAGE("join_message"),
    PRIVATE_MESSAGE("private_message");

    @Getter
    private final String columnName;

    public static <T> Set<T> toPreference(ResultSet resultSet) throws SQLException {
        Set<T> preferences = Sets.newConcurrentHashSet();

        for (Preference preference : Preference.values())
            if (resultSet.getBoolean(preference.getColumnName())) preferences.add((T) preference);

        return preferences;
    }
}
