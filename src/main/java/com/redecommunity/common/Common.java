package com.redecommunity.common;

import com.redecommunity.common.shared.databases.manager.DatabaseManager;
import com.redecommunity.common.shared.scheduler.SchedulerManager;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by @SrGutyerrez
 */
public class Common {
    private static Common instance;

    /**
     * All project static manager
     */
    private DatabaseManager databaseManager;
    private SchedulerManager schedulerManager;

    public Common() {
        Common.instance = this;

        this.databaseManager = new DatabaseManager();
        this.schedulerManager = new SchedulerManager();
    }

    public static Common getInstance() {
        return Common.instance;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public ScheduledExecutorService getScheduler() {
        return this.schedulerManager.getScheduledExecutorService();
    }
}
