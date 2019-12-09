package com.redecommunity.common.shared.databases.manager;

import com.redecommunity.common.Common;
import com.redecommunity.common.shared.databases.configuration.DatabaseConfiguration;
import com.redecommunity.common.shared.databases.mysql.manager.MySQLManager;
import com.redecommunity.common.shared.databases.runnable.DatabaseRefreshRunnable;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseManager {

    private DatabaseConfiguration databaseConfiguration;

    private MySQLManager mySQLManager;

    public DatabaseManager() {
        setupDatabaseConfiguration();

        this.mySQLManager = new MySQLManager(this.databaseConfiguration);

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
    }

    public MySQLManager getMySQLManager() {
        return this.mySQLManager;
    }

    private void setupDatabaseConfiguration() {
        this.databaseConfiguration = new DatabaseConfiguration();
    }
}
