package com.redecommunity.common.shared.permissions.group.data;

import com.redecommunity.common.shared.permissions.permission.data.Permission;
import com.redecommunity.common.shared.util.Helper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class Group {
    private final Integer id;
    private final String name, prefix, suffix, color;
    private final Integer priority, tabListListOrder;
    private final Long discordGroupId;
    private final Integer serverId;
    private final List<Permission> permissions;

    public String getColor() {
        return Helper.colorize("&" + this.color);
    }

    public Boolean isDefault() {
        return this.priority == 0;
    }
}