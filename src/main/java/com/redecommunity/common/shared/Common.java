package com.redecommunity.common.shared;

import com.redecommunity.common.shared.databases.manager.DatabaseManager;
import com.redecommunity.common.shared.language.factory.LanguageFactory;
import com.redecommunity.common.shared.manager.GlobalManager;
import com.redecommunity.common.shared.scheduler.SchedulerManager;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by @SrGutyerrez
 */
public class Common {
    private static Common instance;

    public static final String SERVER_HOME = "/home/redecommunity";

    /**
     * All project static manager
     */
    private DatabaseManager databaseManager;
    private SchedulerManager schedulerManager;

    /**
     * All project static factory
     */

    private LanguageFactory languageFactory;

    public Common() {
        Common.instance = this;

        this.schedulerManager = new SchedulerManager();
        this.databaseManager = new DatabaseManager();

        this.languageFactory = new LanguageFactory();

        new GlobalManager();
    }

    public static void main(String[] args) {
        new Common();
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

    public LanguageFactory getLanguageFactory() {
        return this.languageFactory;
    }
}