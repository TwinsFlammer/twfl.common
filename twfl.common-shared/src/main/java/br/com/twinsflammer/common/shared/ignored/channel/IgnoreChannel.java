package br.com.twinsflammer.common.shared.ignored.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class IgnoreChannel extends Channel {
    @Override
    public String getName() {
        return Constants.IGNORE_CHANNEL;
    }
}
