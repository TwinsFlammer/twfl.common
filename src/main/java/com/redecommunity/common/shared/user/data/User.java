package com.redecommunity.common.shared.user.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Getter
@Setter
public class User {
    private final Integer id;
    private final String name, displayName;
    private final UUID uniqueId;
    private String email;
    private Long discordId;
    private final Long createdAt;
    private Long firstLogin, lastLogin;
    private String lastAddress;
    private Integer lastLobbyId, langId;
}
