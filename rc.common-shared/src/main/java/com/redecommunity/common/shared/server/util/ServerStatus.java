package com.redecommunity.common.shared.server.util;

import java.net.InetSocketAddress;

/**
 * @author SrGutyerrez
 **/
public class ServerStatus {

    protected StatusResponse response;

    public ServerStatus(String address, Integer port){
        try {
            MinecraftServerPinger pinger = new MinecraftServerPinger(new InetSocketAddress(address, port));
            response = pinger.fetchData();
        } catch (Exception e) {}
    }

    public Boolean isOnline(){
        return response != null;
    }

    public Integer getPlayers() {
        return (response == null ? 0 : response.getPlayerOnline());
    }
}
