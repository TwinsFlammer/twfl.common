package com.redecommunity.common.shared;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.redecommunity.common.shared.branches.Branch;
import com.redecommunity.common.shared.commands.GroupCommand;
import com.redecommunity.common.shared.databases.manager.DatabaseManager;
import com.redecommunity.common.shared.language.factory.LanguageFactory;
import com.redecommunity.common.shared.manager.GlobalManager;
import com.redecommunity.common.shared.scheduler.SchedulerManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

    private ClassLoader classLoader;

    public Common() {
        this.classLoader = this.getClass().getClassLoader();

        Common.instance = this;

        this.log(Level.INFO, "Starting using " + Common.getBranch().getName() + " branch.");

        this.schedulerManager = new SchedulerManager();
        this.databaseManager = new DatabaseManager();

        this.languageFactory = new LanguageFactory();

        new GlobalManager();

        new GroupCommand();
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

            if (!file.exists()) {
                file.createNewFile();

                ByteSource byteSource = new ByteSource() {
                    @Override
                    public InputStream openStream() {
                        return Common.getInstance().getResource("configuration.json");
                    }
                };

                String fileValues = byteSource.asCharSource(Charsets.UTF_8).read();

                Files.write(fileValues, file, Charsets.UTF_8);
            }

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

    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        } else {
            try {
                URL url = this.classLoader.getResource(filename);
                if (url == null) {
                    return null;
                } else {
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    return connection.getInputStream();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }
}
