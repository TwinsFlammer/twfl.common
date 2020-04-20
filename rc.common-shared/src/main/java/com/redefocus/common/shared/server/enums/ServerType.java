package com.redefocus.common.shared.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public enum ServerType {
    LOGIN(
            "login-",
            "Login"
    ),
    LOBBY(
            "lobby-",
            "Saguão"
    ),
    FACTIONS(
            "factions-",
            "Factions"
    );

    private final String prefix;
    private final String categoryName;

    public Boolean isValid(String name) {
        return name.startsWith(this.prefix);
    }
}
