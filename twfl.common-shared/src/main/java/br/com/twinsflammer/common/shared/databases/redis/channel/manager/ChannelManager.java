package br.com.twinsflammer.common.shared.databases.redis.channel.manager;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisChannelMessageHandler;
import br.com.twinsflammer.common.shared.util.ClassGetter;
import com.google.common.collect.Lists;
import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;

import java.util.Collections;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class ChannelManager {
    private List<Channel> channels = Lists.newArrayList();

    private JedisChannelMessageHandler jedisChannelMessageHandler;

    public ChannelManager() {
        this.load();

        this.jedisChannelMessageHandler = new JedisChannelMessageHandler();

        this.registerAll();
    }

    public void register(Channel channel) {
        this.channels.add(channel);

        new Thread(() -> {
            Common.getInstance().getDatabaseManager().getRedisManager()
                    .getDatabases()
                    .values()
                    .forEach(redis ->
                            redis.register(
                                    this.jedisChannelMessageHandler,
                                    channel.getName()
                            )
                    );
        }).start();
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
        if (this.channels.size() == 0) return;

        String[] channelsArray = new String[this.channels.size()];

        for (int i = 0; i < this.channels.size(); i++) {
            Channel channel = this.channels.get(i);

            channelsArray[i] = channel.getName();
        }

        new Thread(() -> {
            Common.getInstance().getDatabaseManager().getRedisManager()
                    .getDatabases()
                    .values()
                    .forEach(redis ->
                            redis.register(
                                    this.jedisChannelMessageHandler,
                                    channelsArray
                            )
                    );
        }).start();
    }
}