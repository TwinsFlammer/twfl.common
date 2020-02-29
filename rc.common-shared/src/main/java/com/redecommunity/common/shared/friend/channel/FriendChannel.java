package com.redecommunity.common.shared.friend.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class FriendChannel extends Channel {
    @Override
    public String getName() {
        return Constants.FRIEND_CHANNEL;
    }
}
