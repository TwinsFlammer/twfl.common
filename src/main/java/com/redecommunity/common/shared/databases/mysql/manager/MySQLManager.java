package com.redecommunity.common.shared.databases.mysql.manager;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.databases.configuration.DatabaseConfiguration;
import com.redecommunity.common.shared.databases.mysql.data.MySQL;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class MySQLManager {
    private HashMap<String, MySQL> databases = Maps.newHashMap();

    public MySQLManager(DatabaseConfiguration databaseConfiguration) {
        JSONObject mysql = databaseConfiguration.getMySQL();

        String host = (String) mysql.get("host");
        String user = (String) mysql.get("user");
        String password = (String) mysql.get("password");

        this.createConnection("general", host, user, password, "general");
    }

    public MySQL createConnection(String name, String host, String user, String password, String database) {
        return this.databases.put(name, new MySQL(host, user, password, database));
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

    public HashMap<String, MySQL> getDatabases() {
        return this.databases;
    }

    public MySQL getDatabase(String name) {
        return this.databases.get(name);
    }
}
