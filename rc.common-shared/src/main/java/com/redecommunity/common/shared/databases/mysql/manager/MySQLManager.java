package com.redecommunity.common.shared.databases.mysql.manager;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.Common;
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

    private DatabaseConfiguration databaseConfiguration;

    public MySQLManager(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;

        this.createConnection("general", "general_" + Common.getBranch().getName());
    }

    public MySQL createConnection(String name, String database) {
        JSONObject mysql = this.databaseConfiguration.getMySQL();

        String host = (String) mysql.get("host");
        String user = (String) mysql.get("user");
        String password = (String) mysql.get("password");

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
        return this.databases.get(
                name
        );
    }
}
