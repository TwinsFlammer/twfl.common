package br.com.twinsflammer.common.shared.ignored.listener;

import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.util.Constants;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class IgnoreJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.IGNORE_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer ignoredId = ((Long) jsonObject.get("ignored_id")).intValue();
        Boolean action = (Boolean) jsonObject.get("action");

        User user = UserManager.getUser(userId);

        if (action) user.ignore(ignoredId);
        else user.unIgnore(ignoredId);
    }
}
