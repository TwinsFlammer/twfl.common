package com.redefocus.common.shared.permissions.user.group.listener;

import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.permissions.group.data.Group;
import com.redefocus.common.shared.permissions.group.manager.GroupManager;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.group.data.UserGroup;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.server.data.Server;
import com.redefocus.common.shared.server.manager.ServerManager;
import com.redefocus.common.shared.util.Constants;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Objects;

/**
 * Created by @SrGutyerrez
 */
public class UserGroupJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.USER_GROUP_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer groupId = ((Long) jsonObject.get("group_id")).intValue();
        Integer serverId = ((Long) jsonObject.get("server_id")).intValue();
        Long duration = (Long) jsonObject.get("duration");
        Boolean action = (Boolean) jsonObject.get("action");

        Group group = GroupManager.getGroup(groupId);
        Server server = ServerManager.getServer(serverId);

        UserGroup userGroup = new UserGroup(
                group,
                server,
                duration
        );

        User user = UserManager.getUser(userId);

        if (!action) {
            user.getGroups().add(userGroup);
        } else {
            userGroup = user.getGroups()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(userGroup1 -> userGroup1.getGroup().getId().equals(group.getId()))
                    .filter(userGroup1 -> serverId == 0 ? userGroup1.getServer() == null : userGroup1.getServer().getId().equals(server.getId()))
                    .findFirst()
                    .orElse(null);

            if (userGroup != null) user.getGroups().remove(userGroup);
        }

        user.setWaitingTabListRefresh(true);
    }
}
