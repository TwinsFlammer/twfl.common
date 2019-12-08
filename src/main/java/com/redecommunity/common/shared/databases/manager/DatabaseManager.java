package com.redecommunity.common.shared.databases.manager;

import com.redecommunity.common.shared.databases.mysql.manager.MySQLManager;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseManager {
    private MySQLManager mySQLManager;

    public DatabaseManager() {
        this.mySQLManager = new MySQLManager();
    }

    public MySQLManager getMySQLManager() {
        return this.mySQLManager;
    }
}
