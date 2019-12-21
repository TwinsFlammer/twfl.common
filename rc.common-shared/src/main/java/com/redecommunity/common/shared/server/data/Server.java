package com.redecommunity.common.shared.server.data;

import com.redecommunity.common.shared.server.enums.ServerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Getter
public class Server {
    private Integer id, slots, port, status;
    private String name, displayName, description, address;

    @Setter
    private Integer playerCount;

    @Setter
    private Boolean online;

    private Integer[] normalStatus;

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

    public void updateData(Server server) {
        this.port = server.getPort();
        this.name = server.getName();
        this.slots = server.getSlots();
        this.status = server.getStatus();
        this.address = server.getAddress();
        this.description = server.getDescription();
        this.displayName = server.getDisplayName();
    }

    private ServerType getType() {
        return Arrays.stream(ServerType.values())
                .filter(Objects::nonNull)
                .filter(type -> type.isValid(this.name))
                .findFirst()
                .orElse(null);
    }

    public Boolean isLobby() {
        return this.getType() == ServerType.LOBBY;
    }

    public Boolean isFactions() {
        return this.getType() == ServerType.FACTIONS;
    }

    public Boolean isLoginServer() {
        return this.getType() == ServerType.LOGIN;
    }

    public Boolean isOnline() {
        return this.online;
    }
}
