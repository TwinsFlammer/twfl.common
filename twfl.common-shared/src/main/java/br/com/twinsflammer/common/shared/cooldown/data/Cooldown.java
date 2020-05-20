package br.com.twinsflammer.common.shared.cooldown.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Cooldown<T> {
    @Getter
    private final Integer userId;
    @Getter
    private final Long endTime;
    @Getter
    private final T clazz;

    public Boolean inCooldown() {
        return this.endTime >= System.currentTimeMillis();
    }
}
