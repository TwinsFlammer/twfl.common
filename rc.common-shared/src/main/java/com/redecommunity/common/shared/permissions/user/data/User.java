package com.redecommunity.common.shared.permissions.user.data;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.friend.storage.FriendStorage;
import com.redecommunity.common.shared.ignored.storage.IgnoredStorage;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.preference.Preference;
import com.redecommunity.common.shared.preference.dao.PreferenceDao;
import com.redecommunity.common.shared.report.data.ReportReason;
import com.redecommunity.common.shared.report.manager.ReportReasonManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;
import com.redecommunity.common.shared.twitter.manager.TwitterManager;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.redis.data.Redis;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.language.factory.LanguageFactory;
import com.redecommunity.common.shared.permissions.user.group.data.UserGroup;
import com.redecommunity.common.shared.permissions.user.report.dao.UserReportDao;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.util.Constants;
import com.redecommunity.common.shared.util.action.channel.CustomActionChannel;
import com.redecommunity.common.shared.util.sound.channel.SoundChannel;
import com.redecommunity.common.shared.util.sound.enums.Sound;
import com.redecommunity.common.shared.util.title.channel.CustomTitleChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Setter
public class User {
    @Getter
    private final Integer id;
    @Getter
    private final String name, displayName;
    @Getter
    private final UUID uniqueId;
    @Getter
    private String email, password;
    @Getter
    private Long discordId;

    private Boolean twoFactorAuthenticationEnabled;

    @Getter
    private String twoFactorAuthenticationCode;
    @Getter
    private final Long createdAt;
    @Getter
    private Long firstLogin, lastLogin;
    @Getter
    private String lastAddress;
    @Getter
    private Integer lastLobbyId, languageId;
    @Getter
    private String twitterAccessToken, twitterTokenSecret;
    @Getter
    private final List<UserGroup> groups;
    @Getter
    private final List<Preference> preferences;
    @Getter
    private final List<Integer> friends;
    @Getter
    private final List<Integer> ignored;
    @Getter
    private final List<ReportReason> reports;
    @Getter
    private final List<Skin> skins;

    private Boolean changingSkin, waitingTabListRefresh;

    public String getPrefix() {
        return this.getHighestGroup().getColor() + this.getHighestGroup().getPrefix();
    }

    public String setPassword(String password) {
        return this.password = Helper.hash(password);
    }

    public Group getHighestGroup() {
        return this.groups
                .stream()
                .min((userGroup1, userGroup2) -> userGroup2.getGroup().getPriority().compareTo(userGroup1.getGroup().getPriority()))
                .map(UserGroup::getGroup)
                .orElse(
                        GroupManager.getGroup(GroupNames.DEFAULT)
                );
    }

    public Boolean hasGroupExact(Group group) {
        return this.groups
                .stream()
                .anyMatch(userGroup -> userGroup.getGroup().equals(group));
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
        return group.isDefault() || this.groups
                .stream()
                .anyMatch(userGroup -> userGroup.getGroup().equals(group) || userGroup.getGroup().getPriority() >= group.getPriority());
    }

    public Boolean hasGroup(Integer id) {
        Group group = GroupManager.getGroup(id);

        return this.hasGroup(group);
    }

    public Boolean hasGroup(String name) {
        Group group = GroupManager.getGroup(name);

        return this.hasGroup(group);
    }

    public Boolean hasGroup(Group group, Server server) {
        return this.groups
                .stream()
                .anyMatch(
                        userGroup -> userGroup.getGroup().equals(group)
                                && userGroup.getServer().isSimilar(server)
                );
    }

    public Boolean hasGroup(String groupName, Server server) {
        Group group = GroupManager.getGroup(groupName);

        return this.hasGroup(group, server);
    }

    public Boolean hasGroup(Integer groupId, Server server) {
        Group group = GroupManager.getGroup(groupId);

        return this.hasGroup(group, server);
    }

    public Language getLanguage() {
        LanguageFactory languageFactory = new LanguageFactory();

        return languageFactory.getLanguage(this.languageId);
    }

    public void sendMessage(String message) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("platform", "bukkit-server");
        jsonObject.put("user_id", this.id);
        jsonObject.put("received_message", Helper.colorize(message));

        this.getRedis().sendMessage(
                Constants.MESSAGE_CHANNEL,
                jsonObject.toString()
        );
    }

    public void kick(String reason) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("reason", Helper.colorize(reason));

        this.getRedis().sendMessage(
                Constants.KICK_CHANNEL,
                jsonObject.toString()
        );
    }

    public void connect(Server server) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("platform", "proxy-server");
        jsonObject.put("user_id", this.id);
        jsonObject.put("server_id", server.getId());

        this.getRedis().sendMessage(
                Constants.CONNECT_CHANNEL,
                jsonObject.toString()
        );
    }

    public void setServer(Integer proxyId, String connectedAddress, Server server) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("server_id", server.getId());
        jsonObject.put("proxy_id", proxyId);
        jsonObject.put("connected_address", connectedAddress);

        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            jedis.hset("users", "id" + this.id, jsonObject.toString());
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }

    public void setOffline() {
        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            jedis.hdel("users", "id" + this.id);
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }

    public void setLogged(Boolean logged) {
        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            if (logged) {
                jedis.hset("logged_users", "id" + this.id, String.valueOf(true));
            } else {
                jedis.hdel("logged_users", "id" + this.id);
            }
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }

    public void addFriend(Integer userId, Boolean redisMessage) {
        this.friends.add(userId);

        FriendStorage friendStorage = new FriendStorage();

        friendStorage.insert(
                this,
                userId
        );

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("friend_id", userId);
        jsonObject.put("action", true);

        if (redisMessage) this.getRedis().sendMessage(
                Constants.FRIEND_CHANNEL,
                jsonObject.toString()
        );
    }

    @Deprecated
    public void addFriend(Integer userId) {
        this.addFriend(userId, false);
    }

    public void addFriend(User user, Boolean redisMessage) {
        this.addFriend(user.getId(), redisMessage);
    }

    @Deprecated
    public void addFriend(User user) {
        this.addFriend(user, true);
    }

    public void removeFriend(Integer userId, Boolean redisMessage) {
        this.friends.remove(userId);

        FriendStorage friendStorage = new FriendStorage();

        friendStorage.delete(
                "user_id",
                this.id,
                "friend_id",
                userId
        );

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("friend_id", userId);
        jsonObject.put("action", false);

        if (redisMessage) this.getRedis().sendMessage(
                Constants.FRIEND_CHANNEL,
                jsonObject.toString()
        );
    }

    @Deprecated
    public void removeFriend(Integer userId) {
        this.removeFriend(userId, false);
    }

    public void removeFriend(User user, Boolean redisMessage) {
        this.removeFriend(user.getId(), redisMessage);
    }

    @Deprecated
    public void removeFriend(User user) {
        this.removeFriend(user.getId(), false);
    }

    public void ignore(Integer userId, Boolean redisMessage) {
        this.ignored.add(userId);

        IgnoredStorage ignoredStorage = new IgnoredStorage();

        ignoredStorage.insert(
                this,
                userId
        );

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("ignored_id", userId);
        jsonObject.put("action", true);

        if (redisMessage) this.getRedis().sendMessage(
                Constants.IGNORE_CHANNEL,
                jsonObject.toString()
        );
    }

    @Deprecated
    public void ignore(Integer userId) {
        this.ignore(userId, false);
    }

    public void ignore(User user, Boolean redisMessage) {
        this.ignore(user.getId(), redisMessage);
    }

    @Deprecated
    public void ignore(User user) {
        this.ignore(user, true);
    }

    public void unIgnore(Integer userId, Boolean redisMessage) {
        this.ignored.remove(userId);

        IgnoredStorage ignoredStorage = new IgnoredStorage();

        ignoredStorage.delete(
                "user_id",
                this.id,
                "ignored_id",
                userId
        );

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("ignored_id", userId);
        jsonObject.put("action", false);

        if (redisMessage) this.getRedis().sendMessage(
                Constants.IGNORE_CHANNEL,
                jsonObject.toString()
        );
    }

    @Deprecated
    public void unIgnore(Integer userId) {
        this.unIgnore(userId, false);
    }

    public void unIgnore(User user, Boolean redisMessage) {
        this.unIgnore(user.getId(), redisMessage);
    }

    @Deprecated
    public void unIgnore(User user) {
        this.unIgnore(user, true);
    }

    public void addReport(ReportReason reportReason) {
        this.reports.add(reportReason);
    }

    public void report(User user, ReportReason reportReason) {
        user.addReport(reportReason);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", user.getId());
        jsonObject.put("report_reason_id", reportReason.getId());

        this.getRedis().sendMessage(
                ReportReasonManager.CHANNEL_NAME,
                jsonObject.toString()
        );

        UserReportDao userReportDao = new UserReportDao();

        userReportDao.insert(
                user,
                reportReason
        );
    }

    public void setSkin(Skin skin) {
        SkinDao skinDao = new SkinDao();

        this.skins.stream()
                .filter(Skin::isActive)
                .forEach(Skin::deactivate);

        Skin skin1 = skinDao.insert(
                this,
                skin
        );

        this.skins.add(skin1);
    }

    public void sendTitle(String title, String subtitle, Integer fadeIn, Integer fadeOut, Integer stay) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("title", title);
        jsonObject.put("subtitle", subtitle);
        jsonObject.put("fade_in", fadeIn);
        jsonObject.put("fade_out", fadeOut);
        jsonObject.put("stay", stay);

        CustomTitleChannel customTitleChannel = new CustomTitleChannel();

        customTitleChannel.sendMessage(jsonObject.toString());
    }

    public void sendAction(String message) {
        this.sendAction(
                message,
                null,
                false
        );
    }

    public void sendAction(String message, Integer ticks, Boolean stay) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("message", message);
        jsonObject.put("ticks", ticks);
        jsonObject.put("stay", stay);

        CustomActionChannel customActionChannel = new CustomActionChannel();

        customActionChannel.sendMessage(jsonObject.toString());
    }

    public void playSound(Sound sound, Float volume1, Float volume2) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("sound", sound);
        jsonObject.put("volume_1", volume1);
        jsonObject.put("volume_2", volume2);

        SoundChannel soundChannel = new SoundChannel();

        soundChannel.sendMessage(jsonObject.toString());
    }

    public <T> T getJSONConnection() {
        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            String connectedServer = jedis.hget("users", "id" + this.id);

            JSONObject jsonObject = (JSONObject) JSONValue.parse(connectedServer);

            return (T) jsonObject;
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public Server getServer() {
        JSONObject jsonObject = this.getJSONConnection();

        if (jsonObject != null) {
            Integer serverId = ((Long) jsonObject.get("server_id")).intValue();

            return ServerManager.getServer(serverId);
        }
        return null;
    }

    public Integer getProxyId() {
        JSONObject jsonObject = this.getJSONConnection();

        if (jsonObject != null) return ((Long) jsonObject.get("proxy_id")).intValue();

        return null;
    }

    public String getConnectedAddress() {
        JSONObject jsonObject = this.getJSONConnection();

        if (jsonObject != null) return (String) jsonObject.get("connected_address");

        return null;
    }

    public Redis getRedis() {
        return Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general");
    }

    public Twitter getTwitter() {
        Twitter twitter = new TwitterFactory().getInstance();

        twitter.setOAuthConsumer(
                TwitterManager.oAuthConsumerKey,
                TwitterManager.oAuthConsumerSecret
        );

        AccessToken accessToken = new AccessToken(
                this.twitterAccessToken,
                this.twitterTokenSecret
        );

        twitter.setOAuthAccessToken(
                accessToken
        );

        return twitter;
    }

    public Skin getSkin() {
        return this.skins
                .stream()
                .filter(Skin::isActive)
                .findFirst()
                .orElse(null);
    }

    public Long getTheTimeToTheNextSkinChange() {
        Skin skin = this.getSkin();

        return skin == null ? 0 : (skin.getLastUse() + TimeUnit.MINUTES.toMillis(15)) - System.currentTimeMillis();
    }

    public Boolean canChangeSkin() {
        Skin skin = this.getSkin();

        return skin == null || this.getTheTimeToTheNextSkinChange() <= 0;
    }

    public Boolean hasDiscordAssociated() {
        return this.discordId != 0;
    }

    public Boolean isOnline() {
        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            return jedis.hexists("users", "id" + this.id);
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public Boolean isConsole() {
        return this.id == 1;
    }

    public Boolean isSimilar(User user) {
        return this.id.equals(user.getId());
    }

    public Boolean isDisabled(Preference preference) {
        return this.preferences.contains(preference);
    }

    public Boolean isEnabled(Preference preference) {
        return !this.isDisabled(preference);
    }

    public Boolean isLogged() {
        try (Jedis jedis = this.getRedis().getJedisPool().getResource()) {
            return jedis.hexists("logged_users", "id" + this.id);
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public Boolean isIgnoring(Integer userId) {
        return this.ignored.contains(userId);
    }

    public Boolean isIgnoring(User user) {
        return this.isIgnoring(user.getId());
    }

    public Boolean isFriend(Integer userId) {
        return this.friends.contains(userId);
    }

    public Boolean isFriend(User user) {
        return this.isFriend(user.getId());
    }

    @Deprecated
    public Boolean isToggle(Preference preference) {
        return this.isEnabled(preference);
    }

    public Boolean isTwoFactorAuthenticationEnabled() {
        return this.twoFactorAuthenticationEnabled;
    }

    public Boolean isTwitterAssociated() {
        return this.twitterAccessToken != null && this.twitterTokenSecret != null;
    }

    public Boolean isChangingSkin() {
        return this.changingSkin;
    }

    public Boolean isRegistered() {
        return this.password != null;
    }

    public Boolean isVIP() {
        return this.hasGroup(GroupNames.ELITE);
    }

    public Boolean isWaitingTabListRefresh() {
        return this.waitingTabListRefresh;
    }

    public void togglePreference(Preference preference, Boolean value) {
        this.togglePreference(preference, value, true);
    }

    public void togglePreference(Preference preference, Boolean value, Boolean sendRedisMessage) {
        PreferenceDao preferenceDao = new PreferenceDao();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put(
                preference.getColumnName(),
                this.preferences.contains(preference)
        );

        preferenceDao.update(
                keys,
                this
        );

        if (value) this.preferences.add(preference);
        else this.preferences.remove(preference);

        if (!preference.isGlobal() || !sendRedisMessage) return;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.id);
        jsonObject.put("preference_name", preference.name());
        jsonObject.put("action", value);

        this.getRedis().sendMessage(
                Constants.PREFERENCE_CHANNEL,
                jsonObject.toString()
        );
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
