package com.redecommunity.common.shared.databases.redis.channel.data;

import com.redecommunity.common.shared.Common;
import redis.clients.jedis.JedisPool;

/**
 * Created by @SrGutyerrez
 */
public abstract class Channel implements IChannel {
    @Override
    public abstract String getName();

    public void sendMessage(String message) {

    }

    public JedisPool getJedisPool() {
        return Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general").getJedisPool();
    }
}