package com.redecommunity.common.shared.databases.redis.handler.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redecommunity.common.shared.databases.redis.handler.event.JedisMessageEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class JedisMessageManager {
    private List<JedisMessageListener> listeners = Lists.newArrayList();

    public Boolean registerListener(JedisMessageListener listener) {
        return this.listeners.add(listener);
    }

    public Boolean unregisterListener(JedisMessageListener listener) {
        return this.listeners.remove(listener);
    }

    public void callEvent(String channel, String message) {
        JedisMessageEvent event = new JedisMessageEvent(channel, message);

        this.listeners.forEach(jedisMessageListener -> {
            try {
                Class clazz = jedisMessageListener.getClass();

                List<Method> methods = Arrays.stream(clazz.getMethods())
                        .filter(method -> method.getAnnotation(ChannelName.class) != null)
                        .collect(Collectors.toList());

                for (Method method : methods) {
                    ChannelName channelName = method.getAnnotation(ChannelName.class);
                    if (channelName.name().equalsIgnoreCase(channel))
                        method.invoke(event);
                }
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        });
    }
}
