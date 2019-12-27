package com.redecommunity.common.shared.preference.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class PreferenceChannel extends Channel {
    @Override
    public String getName() {
        return Constants.PREFERENCE_CHANNEL;
    }
}
