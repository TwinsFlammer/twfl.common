package com.redecommunity.common.shared.permissions.user.data;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.redis.data.Redis;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.language.factory.LanguageFactory;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.util.Constants;
import com.redecommunity.common.shared.util.Helper;
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
    protected Integer lastLobbyId, languageId;
    protected Collection<Group> groups;

    public Group getHighestGroup() {
        return this.groups
                .stream()
                .min((group1, group2) -> group2.getPriority().compareTo(group1.getPriority()))
                .orElse(
                        GroupManager.getGroup("default")
                );
    }

    public Boolean hasGroupExact(Group group) {
        return this.groups.contains(group);
    }

    public Boolean hasGroupExact(String name) {
        Group group = GroupManager.getGroup(name);

        return this.hasGroupExact(group);
    }

    public Boolean hasGroupExact(Integer id) {
        Group group = GroupManager.getGroup(id);

        return this.hasGroupExact(group);
    }

    public Boolean hasGroup(Group group) {
        return this.groups
                .stream()
                .anyMatch(group1 -> group1.equals(group) || group1.getPriority() >= group.getPriority());
    }

    public Boolean hasGroup(Integer id) {
        Group group = GroupManager.getGroup(id);

        return this.hasGroup(group);
    }

    public Boolean hasGroup(String name) {
        Group group = GroupManager.getGroup(name);

        return this.hasGroup(group);
    }

    public Language getLanguage() {
        LanguageFactory languageFactory = new LanguageFactory();

        return languageFactory.getLanguage(this.languageId);
    }

    public void sendMessage(String message) {
        Redis redis = Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("platform", "bukkit-server");
        jsonObject.put("user_id", this.id);
        jsonObject.put("received_message", Helper.colorize(message));

        redis.sendMessage(
                Constants.MESSAGE_CHANNEL,
                jsonObject.toString()
        );
    }

    public void kick(String reason) {
        Redis redis = Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("reason", Helper.colorize(reason));

        redis.sendMessage(
                Constants.KICK_CHANNEL,
                jsonObject.toString()
        );
    }

    public Boolean isConsole() {
        return this.id == 1;
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
        object.put("language_id", this.languageId);

        return object.toString();
    }
}
