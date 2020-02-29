package com.redecommunity.common.shared.ignored.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class IgnoreChannel extends Channel {
    @Override
    public String getName() {
        return Constants.IGNORE_CHANNEL;
    }
}
