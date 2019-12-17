package com.redecommunity.common.shared.databases.redis.handler.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class JedisMessageEvent {
    private final String channel, message;
}
