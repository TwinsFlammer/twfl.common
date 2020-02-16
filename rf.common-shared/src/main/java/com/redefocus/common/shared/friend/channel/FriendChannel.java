package com.redefocus.common.shared.friend.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import com.redefocus.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class FriendChannel extends Channel {
    @Override
    public String getName() {
        return Constants.FRIEND_CHANNEL;
    }
}
