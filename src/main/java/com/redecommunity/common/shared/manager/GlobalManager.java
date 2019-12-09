package com.redecommunity.common.shared.manager;

import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.dao.UserDao;

import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
public class GlobalManager {
    public GlobalManager() {
        this.start();
    }

    private void start() {
        new TableManager();
    }
}

class TableManager {
    TableManager() {
        Table userDao = new UserDao();

        try {
            userDao.createTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}