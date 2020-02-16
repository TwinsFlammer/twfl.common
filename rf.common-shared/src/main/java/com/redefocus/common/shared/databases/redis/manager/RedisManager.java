package com.redefocus.common.shared.databases.redis.manager;

import com.google.common.collect.Maps;
import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.databases.configuration.DatabaseConfiguration;
import com.redefocus.common.shared.databases.redis.data.Redis;
import org.json.simple.JSONObject;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class RedisManager {
    private HashMap<String, Redis> databases = Maps.newHashMap();

    public RedisManager(DatabaseConfiguration databaseConfiguration) {
        JSONObject redis = databaseConfiguration.getRedis();

        String host = (String) redis.get("host");
        String password = (String) redis.get("password");

        this.createConnection("general", host, password);
    }

    public Redis createConnection(String name, String host, String password) {
        return this.databases.put(name, new Redis(host, password));
    }

    public void refresh() {
        this.databases.values().forEach(redis -> {
            try {
                redis.refresh();
            } catch (JedisConnectionException exception) {
                exception.printStackTrace();
            }
        });
    }

    public HashMap<String, Redis> getDatabases() {
        return this.databases;
    }

    public Redis getDatabase(String name) {
        return this.databases.get(name);
    }

    public static Redis getDefaultRedis() {
        return Common.getInstance().getDatabaseManager().getRedisManager().getDatabase("general");
    }
}
