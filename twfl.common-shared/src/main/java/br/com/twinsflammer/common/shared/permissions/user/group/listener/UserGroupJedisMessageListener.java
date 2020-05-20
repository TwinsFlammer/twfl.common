package br.com.twinsflammer.common.shared.permissions.user.group.listener;

import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.permissions.group.data.Group;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.util.Constants;
import br.com.twinsflammer.common.shared.permissions.group.manager.GroupManager;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.server.manager.ServerManager;
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
