package com.redecommunity.common.shared.permissions.permission.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Permission {
    @Getter
    private final String name;
    @Getter
    private final Integer groupId;
    private final Boolean grantToHigher;
    @Getter
    private final Integer serverId;

    public Boolean grantToHigher() {
        return this.grantToHigher;
    }
}