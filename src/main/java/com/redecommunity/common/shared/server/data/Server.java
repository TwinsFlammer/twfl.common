package com.redecommunity.common.shared.server.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.*;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class Server {
    private final Integer id, slots, port, status;
    private final String name, displayName, description, address;

    @Setter
    private Integer playerCount;

    @Setter
    private Boolean online;

    private Integer[] normalStatus = new Integer[] { 0, 1, 2 };

    /**
     * All status number legend
     *
     * -1 = Non-listed
     * 0 = Open for all players
     * 1 = Beta
     * 2 = Beta VIP
     * 3 = Maintenance
     * 4 = Restarting
     */
    public Color getStatusColor() {
        if (this.status != 0) {
            switch (this.status) {
                case -1: return Color.getColor("AAAAAA");
                case 1: return Color.getColor("55FF55");
                case 2: return Color.getColor("FFFF55");
                case 3:
                case 4: return Color.getColor("FF5555");
            }
        }
        return this.online ? Color.getColor("FFFF55") : Color.getColor("FF5555");
    }

    public Boolean isSimilar(Server server) {
        return this.port.equals(server.getPort())
                && this.name.equals(server.getName())
                && this.slots.equals(server.getSlots())
                && this.status.equals(server.getStatus())
                && this.address.equals(server.getAddress())
                && this.description.equals(server.getDescription())
                && this.displayName.equals(server.getDisplayName());
    }
}
