package com.redefocus.common.shared.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public enum ServerType {
    LOGIN("login-"),
    LOBBY("lobby-"),
    FACTIONS("factions-");

    private final String prefix;

    public Boolean isValid(String name) {
        return name.startsWith(this.prefix);
    }
}
