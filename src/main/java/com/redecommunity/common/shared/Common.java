package com.redecommunity.common.shared;

import com.redecommunity.common.shared.branches.Branch;
import com.redecommunity.common.shared.databases.manager.DatabaseManager;
import com.redecommunity.common.shared.language.factory.LanguageFactory;
import com.redecommunity.common.shared.manager.GlobalManager;
import com.redecommunity.common.shared.scheduler.SchedulerManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static Branch getBranch() {
        try {
            File file = new File(Common.SERVER_HOME + "/configuration/configuration.json");

            if (!file.exists()) file.createNewFile();

            FileReader fileReader = new FileReader(file);

            JSONObject jsonObject = (JSONObject) JSONValue.parse(fileReader);

            Boolean develop = (Boolean) jsonObject.get("develop");

            return develop ? Branch.DEVELOP : Branch.MASTER;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void log(Level level, String message) {
        Logger logger = Logger.getGlobal();

        logger.log(level, message);
    }
}
