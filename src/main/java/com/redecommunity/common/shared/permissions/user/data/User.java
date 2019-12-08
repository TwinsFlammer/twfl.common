package com.redecommunity.common.shared.permissions.user.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Getter
@Setter
public class User {
    protected final Integer id;
    protected final String name, displayName;
    protected final UUID uniqueId;
    protected String email;
    protected Long discordId;
    protected final Long createdAt;
    protected Long firstLogin, lastLogin;
    protected String lastAddress;
    protected Integer lastLobbyId, langId;

    public String toString() {
        JSONObject object = new JSONObject();

        object.put("id", this.id);
        object.put("name", this.name);
        object.put("display_name", this.displayName);
        object.put("unique_id", this.uniqueId);
        object.put("email", this.email);
        object.put("discord_id", this.discordId);
        object.put("created_at", this.createdAt);
        object.put("first_login", this.firstLogin);
        object.put("last_login", this.lastLogin);
        object.put("last_address", this.lastAddress);
        object.put("last_lobby_id", this.lastLobbyId);
        object.put("lang_id", this.langId);

        return object.toString();
    }
}
