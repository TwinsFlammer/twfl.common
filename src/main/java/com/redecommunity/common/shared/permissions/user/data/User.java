package com.redecommunity.common.shared.permissions.user.data;

import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.util.Collection;
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
    protected Collection<Group> groups;

    public Group getHighestGroup() {
        return this.groups
                .stream()
                .min((group1, group2) -> group2.getPriority().compareTo(group1.getPriority()))
                .orElse(null);
    }

    public boolean hasGroup(Group group) {
        return this.groups.contains(group);
    }

    public boolean hasGroup(Integer id) {
        Group group = GroupManager.getGroup(id);

        return this.hasGroup(group);
    }

    public boolean hasGroup(String name) {
        Group group = GroupManager.getGroup(name);

        return this.hasGroup(group);
    }

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
