package br.com.twinsflammer.common.shared.permissions.group.data;

import br.com.twinsflammer.common.shared.util.Helper;
import br.com.twinsflammer.common.shared.permissions.permission.data.Permission;
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

    public String getFancyPrefix() {
        return this.getColor() + (this.prefix.matches("[\\[\\]]") ? this.prefix : this.prefix.split("\\[")[1].split("]")[0]);
    }

    public Boolean isDefault() {
        return this.priority == 0;
    }

    public Boolean isHigherOrEqual(Group group) {
        return this.priority >= group.getPriority();
    }

    public Boolean hasPermission(String permission) {
        return this.permissions
                .stream()
                .anyMatch(permission1 -> permission1.getName().equals(permission));
    }
}