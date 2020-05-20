package br.com.twinsflammer.common.shared.friend.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class FriendChannel extends Channel {
    @Override
    public String getName() {
        return Constants.FRIEND_CHANNEL;
    }
}
