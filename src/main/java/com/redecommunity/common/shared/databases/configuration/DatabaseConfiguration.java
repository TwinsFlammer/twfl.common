package com.redecommunity.common.shared.databases.configuration;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseConfiguration {
    private File folder, file;

    public DatabaseConfiguration() {
        this.folder = new File("/home/redecommunity/configuration");
        this.file = new File(folder + File.separator + "_databases.json");

        try {
            this.create();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createFolder() {
        if (!this.folder.exists()) this.folder.mkdirs();
    }

    private void createFile() throws IOException {
        if (!this.file.exists()) this.file.createNewFile();
    }

    private void create() throws IOException {
        this.createFolder();
        this.createFile();
    }

    public JSONObject getConfiguration() throws FileNotFoundException {
        return (JSONObject) JSONValue.parse(new FileReader(this.file));
    }

    private JSONObject getDatabases() {
        try {
            JSONObject jsonObject = this.getConfiguration();

            return (JSONObject) jsonObject.get("databases");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public JSONObject getMySQL() {
        return (JSONObject) this.getDatabases().get("mysql");
    }

    public JSONObject getMongodb() {
        return (JSONObject) this.getDatabases().get("mongodb");
    }

    public JSONObject getRedis() {
        return (JSONObject) this.getDatabases().get("redis");
    }
}
