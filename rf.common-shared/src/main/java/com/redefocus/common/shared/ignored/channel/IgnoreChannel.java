package com.redefocus.common.shared.ignored.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import com.redefocus.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class IgnoreChannel extends Channel {
    @Override
    public String getName() {
        return Constants.IGNORE_CHANNEL;
    }
}
