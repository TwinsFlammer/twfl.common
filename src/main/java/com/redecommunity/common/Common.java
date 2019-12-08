package com.redecommunity.common;

import com.redecommunity.common.shared.databases.manager.DatabaseManager;

/**
 * Created by @SrGutyerrez
 */
public class Common {
    private static Common instance;
    private DatabaseManager databaseManager;

    public Common() {
        Common.instance = this;

        this.databaseManager = new DatabaseManager();
    }

    public static Common getInstance() {
        return Common.instance;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }
}
