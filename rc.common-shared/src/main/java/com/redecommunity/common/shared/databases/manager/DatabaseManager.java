package com.redecommunity.common.shared.databases.manager;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.configuration.DatabaseConfiguration;
import com.redecommunity.common.shared.databases.mongo.manager.MongoManager;
import com.redecommunity.common.shared.databases.redis.manager.RedisManager;
import com.redecommunity.common.shared.databases.runnable.DatabaseRefreshRunnable;
import com.redecommunity.common.shared.databases.mysql.manager.MySQLManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseManager {

    private DatabaseConfiguration databaseConfiguration;

    private MySQLManager mySQLManager;
    private RedisManager redisManager;
    private MongoManager mongoManager;

    public DatabaseManager() {
        this.databaseConfiguration = new DatabaseConfiguration();

        this.mySQLManager = new MySQLManager(this.databaseConfiguration);
        this.redisManager = new RedisManager(this.databaseConfiguration);
        this.mongoManager = new MongoManager(this.databaseConfiguration);

        this.refresh();

        this.startScheduler();
    }

    private void startScheduler() {
        Common.getInstance().getScheduler().scheduleAtFixedRate(
                new DatabaseRefreshRunnable(),
                0,
                5,
                TimeUnit.SECONDS
        );
    }

    public void refresh() {
        this.mySQLManager.refresh();
        this.redisManager.refresh();
        this.mongoManager.refresh();
    }

    public MySQLManager getMySQLManager() {
        return this.mySQLManager;
    }

    public RedisManager getRedisManager() {
        return this.redisManager;
    }

    public MongoManager getMongoManager() {
        return this.mongoManager;
    }
}
