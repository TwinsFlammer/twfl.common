package com.redecommunity.common.shared.language.factory;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.language.enums.Language;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class LanguageFactory<L extends Language> {
    private HashMap<L, JSONObject> languages = Maps.newHashMap();

    public LanguageFactory() {
        try {
            this.createFiles();

            this.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createFiles() throws IOException {
        for (Language language : Language.values()) language.createFile();
    }

    public HashMap<L, JSONObject> load() throws IOException {
        HashMap<L, JSONObject> languages = Maps.newHashMap();

        for (Language language : Language.values()) {
            File file = new File(Common.SERVER_HOME + File.separator + "languages" + File.separator + language.getName() + ".json");

            if (!file.exists()) file.createNewFile();

            FileReader fileReader = new FileReader(file);

            JSONObject jsonObject = (JSONObject) JSONValue.parse(fileReader);

            languages.put((L) language, jsonObject);
        }

        return this.languages = languages;
    }

    public L getLanguage(Integer id) {
        Language language = Arrays.stream(Language.values())
                .filter(language1 -> language1.getId() == id)
                .findFirst()
                .orElse(null);

        return (L) language;
    }

    public L getLanguage(String name) {
        Language language = Arrays.stream(Language.values())
                .filter(language1 -> language1.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        return (L) language;
    }

    public String getMessage(L language, String key) {
        return (String) this.languages.get(language).get(key);
    }

    public Object get(L language, String key) {
        return this.languages.get(language).get(key);
    }
}
