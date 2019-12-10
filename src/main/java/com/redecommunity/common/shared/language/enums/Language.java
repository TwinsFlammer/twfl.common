package com.redecommunity.common.shared.language.enums;

import com.redecommunity.common.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public enum Language {
    PORTUGUESE(
            1,
            "pt_br",
            "Português"
    );

    private final Integer id;
    private final String name, displayName;

    public String getMessage(String key) {
        return Common.getInstance().getLanguageFactory().getMessage(this, key);
    }

    public Object get(String key) {
        return Common.getInstance().getLanguageFactory().get(this, key);
    }

    public boolean createFile() throws IOException {
        File path = new File(Common.SERVER_HOME + "/languages");

        if (!path.exists()) path.mkdirs();

        File file = new File(Common.SERVER_HOME + "/languages/" + this.name + ".json");

        return file.createNewFile();
    }
}
