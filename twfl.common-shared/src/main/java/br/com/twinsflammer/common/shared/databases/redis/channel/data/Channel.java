package br.com.twinsflammer.common.shared.databases.redis.channel.data;

import br.com.twinsflammer.common.shared.Common;
import redis.clients.jedis.JedisPool;

/**
 * Created by @SrGutyerrez
 */
public abstract class Channel implements IChannel {
    @Override
    public abstract String getName();

    public void sendMessage(String message) {
        // TODO auto-generated method
    }

    public final JedisPool getJedisPool() {
        return Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general").getJedisPool();
    }
}