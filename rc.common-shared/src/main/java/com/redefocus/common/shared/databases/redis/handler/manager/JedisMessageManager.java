package com.redefocus.common.shared.databases.redis.handler.manager;

import com.google.common.collect.Lists;
import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.util.ClassGetter;

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

    public JedisMessageManager() {
        ClassGetter.getClassesForPackage(Common.class).forEach(clazz -> {
            if (JedisMessageListener.class.isAssignableFrom(clazz) && !clazz.equals(JedisMessageListener.class)) {
                try {
                    JedisMessageListener jedisMessageListener = (JedisMessageListener) clazz.newInstance();

                    this.registerListener(jedisMessageListener);
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public Boolean registerListener(JedisMessageListener listener) {
        return this.listeners.add(listener);
    }

    public Boolean unregisterListener(JedisMessageListener listener) {
        return this.listeners.remove(listener);
    }

    public void callEvent(String channel, String message) {
        JedisMessageEvent event = new JedisMessageEvent(
                channel,
                message
        );

        this.listeners.forEach(jedisMessageListener -> {
            try {
                Class clazz = jedisMessageListener.getClass();

                List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.getAnnotation(ChannelName.class) != null)
                        .collect(Collectors.toList());

                for (Method method : methods) {
                    ChannelName channelName = method.getAnnotation(ChannelName.class);
                    if (channelName.name().equalsIgnoreCase(channel))
                        method.invoke(jedisMessageListener, event);
                }
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        });
    }
}
