package com.redefocus.common.shared.server.util;

import java.net.InetSocketAddress;

/**
 * @author SrGutyerrez
 **/
public class ServerStatus {

    protected StatusResponse response;

    public ServerStatus(InetSocketAddress inetSocketAddress){
        try {
            MinecraftServerPinger pinger = new MinecraftServerPinger(inetSocketAddress);
            response = pinger.fetchData();
        } catch (Exception e) {}
    }

    public Boolean isOnline(){
        return response != null;
    }

    public Integer getPlayerCount() {
        return (response == null ? 0 : response.getPlayerOnline());
    }
}
