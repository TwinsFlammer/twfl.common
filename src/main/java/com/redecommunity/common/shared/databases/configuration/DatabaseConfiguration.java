package com.redecommunity.common.shared.databases.configuration;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.redecommunity.common.shared.Common;
import org.apache.commons.io.Charsets;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseConfiguration {
    private File folder, file;

    public DatabaseConfiguration() {
        this.folder = new File(Common.SERVER_HOME + "/configuration");
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
        if (!this.file.exists()) {
            this.file.createNewFile();

            ByteSource byteSource = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return null;
                }
            };

            String fileValues = byteSource.asCharSource(Charsets.UTF_8).read();

            Files.write(fileValues, this.file, Charsets.UTF_8);
        }
    }

    private void create() throws IOException {
        this.createFolder();
        this.createFile();
    }

    public JSONObject getConfiguration() throws FileNotFoundException {
        FileReader fileReader = new FileReader(this.file);

        return (JSONObject) JSONValue.parse(fileReader);
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
