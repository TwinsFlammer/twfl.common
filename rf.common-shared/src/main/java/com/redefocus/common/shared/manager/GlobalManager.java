package com.redefocus.common.shared.manager;

import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.databases.mysql.dao.Table;
import com.redefocus.common.shared.report.manager.ReportReasonManager;
import com.redefocus.common.shared.server.manager.ServerManager;
import com.redefocus.common.shared.twitter.manager.TwitterManager;
import com.redefocus.common.shared.util.ClassGetter;
import com.redefocus.common.shared.permissions.group.manager.GroupManager;
import com.redefocus.common.shared.permissions.user.manager.UserManager;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by @SrGutyerrez
 */
public class GlobalManager {
    public GlobalManager() {
        this.start();
    }

    private void start() {
        // CREATE ALL TABLES
        new TableManager();
        // CONSTRUCT ALL DATA MANAGER
        new DataManager();
    }
}

class DataManager {
    DataManager() {
        new GroupManager();
        new UserManager();
        new ServerManager();
        new TwitterManager();
        new ReportReasonManager();
    }
}

class TableManager {
    TableManager() {
        Collection<Class> blacklisted = Collections.singletonList(Table.class);

        for (Class<?> clazz : ClassGetter.getClassesForPackage(Common.class)) {
            try {
                if (Table.class.isAssignableFrom(clazz) && !blacklisted.contains(clazz)) {
                    Table table = (Table) clazz.newInstance();

                    table.createTable();
                }
            } catch (IllegalAccessException | InstantiationException exception) {
                exception.printStackTrace();
            }
        }
    }
}