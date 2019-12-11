package com.redecommunity.common.shared.databases.redis.channel.data;

/**
 * Created by @SrGutyerrez
 */
public abstract class Channel implements IChannel {
    public Channel(String name) {

    }

    @Override
    public abstract String getName();
}