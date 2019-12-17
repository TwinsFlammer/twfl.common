package com.redecommunity.common.shared.databases.redis.channel.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.databases.redis.handler.JedisChannelMessageHandler;
import com.redecommunity.common.shared.util.ClassGetter;
import com.redecommunity.common.shared.databases.redis.manager.RedisManager;

import java.util.Collections;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class ChannelManager {
    private List<Channel> channels = Lists.newArrayList();

    private RedisManager redisManager;

    public ChannelManager(RedisManager redisManager) {
        this.redisManager = redisManager;

        this.load();

        this.registerAll();
    }

    public boolean add(Channel channel) {
        return this.channels.add(channel);
    }

    private void load() {
        List<Class> blacklisted = Collections.singletonList(Channel.class);

        ClassGetter.getClassesForPackage(Common.class).forEach(clazz -> {
            if (Channel.class.isAssignableFrom(clazz) && !blacklisted.contains(clazz)) {
                try {
                    Channel channel = (Channel) clazz.newInstance();

                    this.channels.add(channel);
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void registerAll() {
        String[] channelsArray = new String[this.channels.size()];

        for (int i = 0; i < this.channels.size(); i++) {
            Channel channel = this.channels.get(i);

            channelsArray[i] = channel.getName();
        }

        JedisChannelMessageHandler jedisChannelMessageHandler = new JedisChannelMessageHandler();


        new Thread(() -> {
            this.redisManager
                    .getDatabases()
                    .values()
                    .forEach(redis ->
                            redis.register(
                                    jedisChannelMessageHandler,
                                    channelsArray
                            )
                    );
        }).start();
    }
}