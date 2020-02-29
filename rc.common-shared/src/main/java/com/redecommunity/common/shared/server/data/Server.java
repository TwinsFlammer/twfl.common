package com.redecommunity.common.shared.server.data;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.server.dao.ServerDao;
import com.redecommunity.common.shared.server.enums.ServerType;
import com.redecommunity.common.shared.server.util.ServerStatus;
import com.redecommunity.common.shared.util.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Getter
public class Server {
    private final Integer id;
    private Integer slots, port, status;
    private String name, displayName, description, address;

    @Setter
    private Integer playerCount;

    @Setter
    private Boolean online;

    private Integer[] normalStatus;

    public String getDescription() {
        if (this.description.contains("\\n")) {
            String[] descriptions = this.description.split("\\\\n");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < descriptions.length; i++) {
                String description = descriptions[i];

                stringBuilder.append(description)
                        .append(i+1 >= descriptions.length ? "" : "\n");
            }

            return stringBuilder.toString();
        }
        return this.description;
    }

    /**
     * All status number legend
     *
     * -1 = Non-listed
     * 0 = Open for all players
     * 1 = Beta
     * 2 = Beta VIP
     * 3 = Maintenance
     * 4 = Restarting
     * 5 = Development
     *
     * @return String
     */
    public String getStatusColor() {
        if (this.status != 0) {
            switch (this.status) {
                case -1: return Helper.colorize("&7") ;
                case 1: return Helper.colorize("&a");
                case 2: return Helper.colorize("&e");
                case 3:
                case 4:
                case 5: return Helper.colorize("&c");
            }
        }
        return this.online ? Helper.colorize("&a") : Helper.colorize("&c") ;
    }

    public String getStatusString() {
        switch (this.status) {
            case -1: return "[RESTRITO]";
            case 0: return null;
            case 1: return "§eBeta aberta!";
            case 2: return "§eBeta VIP";
            case 3: return "§cEm manutenção";
            case 4: return "§cReiniciando";
            case 5: return "§cEm desenvolvimento";
            default: return this.online ? "§eClique para entrar!" : "§cOffline";
        }
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

    public void setStatus(Integer status) {
        this.status = status;

        ServerDao serverDao = new ServerDao();

        HashMap<String, Integer> keys = Maps.newHashMap();

        keys.put("status", this.status);

        serverDao.update(
                keys,
                "id",
                this.id
        );
    }

    private ServerType getType() {
        return Arrays.stream(ServerType.values())
                .filter(Objects::nonNull)
                .filter(type -> type.isValid(this.name))
                .findFirst()
                .orElse(null);
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(this.address, this.port);
    }

    public void update() {
        ServerStatus serverStatus = new ServerStatus(
                this.getInetSocketAddress()
        );

        this.setOnline(serverStatus.isOnline());
        this.setPlayerCount(serverStatus.getPlayerCount());
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

    public Boolean isAccessible() {
        return this.status == 0 || this.status == 1 || this.status == 2;
    }

    public Boolean inMaintenance() {
        return this.status == 3;
    }

    public Boolean isRestarting() {
        return this.status == 4;
    }

    public Boolean inDevelopment() {
        return this.status == 5;
    }
}
