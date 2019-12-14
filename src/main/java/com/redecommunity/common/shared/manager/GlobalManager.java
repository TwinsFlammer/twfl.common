package com.redecommunity.common.shared.manager;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.group.dao.GroupDao;
import com.redecommunity.common.shared.permissions.permission.dao.PermissionDao;
import com.redecommunity.common.shared.permissions.user.dao.UserDao;
import com.redecommunity.common.shared.util.ClassGetter;

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
//        Table userDao = new UserDao();
//        Table groupDao = new GroupDao();
//        Table permissionDao = new PermissionDao();
//
//        userDao.createTable();
//        groupDao.createTable();
//        permissionDao.createTable();
        for (Class<?> aClass : ClassGetter.getClassesForPackage(Common.class)) {
            System.out.println(aClass);
        }
    }
}