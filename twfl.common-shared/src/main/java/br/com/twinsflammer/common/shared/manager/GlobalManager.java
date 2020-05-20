package br.com.twinsflammer.common.shared.manager;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.cooldown.manager.CooldownManager;
import br.com.twinsflammer.common.shared.databases.mysql.dao.Table;
import br.com.twinsflammer.common.shared.twitter.manager.TwitterManager;
import br.com.twinsflammer.common.shared.util.ClassGetter;
import br.com.twinsflammer.common.shared.report.manager.ReportReasonManager;
import br.com.twinsflammer.common.shared.server.manager.ServerManager;
import br.com.twinsflammer.common.shared.permissions.group.manager.GroupManager;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;

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

        new CooldownManager();
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