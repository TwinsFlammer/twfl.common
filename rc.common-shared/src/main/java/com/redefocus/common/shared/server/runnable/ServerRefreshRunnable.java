package com.redefocus.common.shared.server.runnable;

import com.redefocus.common.shared.server.dao.ServerDao;
import com.redefocus.common.shared.server.data.Server;
import com.redefocus.common.shared.server.manager.ServerManager;

import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class ServerRefreshRunnable implements Runnable {
    @Override
    public void run() {
        ServerDao serverDao = new ServerDao();

        Set<Server> servers = serverDao.findAll();

        servers.forEach(server -> {
            Server server1 = ServerManager.getServer(server.getId());

            if (server1 != null) {
                if (!server1.isSimilar(server)) server1.updateData(server);

                server1.update();
            } else {
                server.update();

                ServerManager.addServer(server);
            }
        });
    }
}
