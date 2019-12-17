package com.redecommunity.common.shared.databases.redis.handler.event;

import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class JedisMessageEvent {
    private final String channel, message;
}
