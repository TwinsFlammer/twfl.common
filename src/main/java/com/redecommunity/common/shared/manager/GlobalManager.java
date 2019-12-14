package com.redecommunity.common.shared.manager;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.manager.ServerManager;
import com.redecommunity.common.shared.util.ClassGetter;

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