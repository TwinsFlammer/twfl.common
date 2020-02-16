package com.redefocus.common.bungee.listeners.general;

import com.redefocus.common.shared.permissions.group.data.Group;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by @SrGutyerrez
 */
public class GroupPermissionCheckListener implements Listener {
    @EventHandler
    public void onCheck(PermissionCheckEvent event) {
        CommandSender commandSender = event.getSender();

        if (commandSender.equals(ProxyServer.getInstance().getConsole())) {
            event.setHasPermission(true);
            return;
        }

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

        User user = UserManager.getUser(proxiedPlayer.getUniqueId());
        Group group = user.getHighestGroup();

        event.setHasPermission(group.hasPermission(event.getPermission()));
    }
}
