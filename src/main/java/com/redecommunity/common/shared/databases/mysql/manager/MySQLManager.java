package com.redecommunity.common.shared.databases.mysql.manager;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.databases.mysql.data.MySQL;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class MySQLManager {
    private HashMap<String, MySQL> databases = Maps.newHashMap();

    public MySQLManager() {
        this.createConnection("general", "", "", "", "");
    }

    private MySQL createConnection(String name, String host, String user, String password, String database) {
        return this.databases.put(name, new MySQL(host, user, password, database));
    }

    public void start() {
        this.databases.values().forEach(mySQL -> {
            try {
                mySQL.start();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void refresh() {
        this.databases.values().forEach(mySQL -> {
            try {
                mySQL.refresh();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public MySQL getDatabase(String name) {
        return this.databases.get(name);
    }
}
