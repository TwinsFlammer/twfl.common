package com.redecommunity.common.shared.manager;

import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
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
        new TableManager();
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