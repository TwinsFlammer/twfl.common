package br.com.twinsflammer.common.shared.databases.redis.handler;

import br.com.twinsflammer.common.shared.Common;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

/**
 * Created by @SrGutyerrez
 */
public class JedisChannelMessageHandler extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        Common.getInstance().getJedisMessageManager().callEvent(channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        Common.getInstance().log(
                Level.INFO,
                String.format(
                        "[Redis] channel %s is now subscribed.",
                        channel
                )
        );
    }
}