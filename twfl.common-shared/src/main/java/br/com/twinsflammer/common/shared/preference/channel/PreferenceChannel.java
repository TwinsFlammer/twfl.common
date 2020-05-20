package br.com.twinsflammer.common.shared.preference.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class PreferenceChannel extends Channel {
    @Override
    public String getName() {
        return Constants.PREFERENCE_CHANNEL;
    }
}
