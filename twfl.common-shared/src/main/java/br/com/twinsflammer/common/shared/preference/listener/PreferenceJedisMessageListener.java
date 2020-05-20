package br.com.twinsflammer.common.shared.preference.listener;

import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.preference.Preference;
import br.com.twinsflammer.common.shared.util.Constants;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class PreferenceJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.PREFERENCE_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        String preferenceName = (String) jsonObject.get("preference_name");
        Boolean action = (Boolean) jsonObject.get("action");

        User user = UserManager.getUser(userId);

        if (user == null || !user.isOnline()) return;

        Preference preference = Preference.valueOf(preferenceName);

        user.togglePreference(
                preference,
                action,
                false
        );
    }
}
