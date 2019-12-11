package com.redecommunity.common.shared.databases.redis.handler;

import com.redecommunity.common.shared.Common;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

/**
 * Created by @SrGutyerrez
 */
public class JedisChannelMessageHandler extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel + " » " + message);
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