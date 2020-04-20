package com.redefocus.common.shared.databases.mongo.manager;

import com.google.common.collect.Maps;
import com.mongodb.MongoException;
import com.redefocus.common.shared.databases.configuration.DatabaseConfiguration;
import com.redefocus.common.shared.databases.mongo.data.Mongo;
import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class MongoManager {
    private HashMap<String, Mongo> databases = Maps.newHashMap();

    public MongoManager(DatabaseConfiguration databaseConfiguration) {
        JSONObject mongodb = databaseConfiguration.getMongodb();

        String host = (String) mongodb.get("host");
        String user = (String) mongodb.get("user");
        String password = (String) mongodb.get("password");

        this.createConnection("general", host, user, password, "general");
    }

    public Mongo createConnection(String name, String host, String user, String password, String database) {
        return this.databases.put(name, new Mongo(host, user, password, database));
    }

    public void refresh() {
        this.databases.values().forEach(mongo -> {
            try {
                mongo.refresh();
            } catch (MongoException exception) {
                exception.printStackTrace();
            }
        });
    }

    public HashMap<String, Mongo> getDatabases() {
        return this.databases;
    }

    public Mongo getDatabase(String name) {
        return this.databases.get(name);
    }
}
