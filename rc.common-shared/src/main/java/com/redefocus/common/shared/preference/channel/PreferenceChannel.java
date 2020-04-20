package com.redefocus.common.shared.preference.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import com.redefocus.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class PreferenceChannel extends Channel {
    @Override
    public String getName() {
        return Constants.PREFERENCE_CHANNEL;
    }
}
