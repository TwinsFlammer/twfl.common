package com.redefocus.common.shared.friend.listener;

import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.util.Constants;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class FriendJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.FRIEND_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer friendId = ((Long) jsonObject.get("friend_id")).intValue();
        Boolean action = (Boolean) jsonObject.get("action");

        User user = UserManager.getUser(userId);

        if (action) user.addFriend(friendId);
        else user.removeFriend(friendId);
    }
}
