package br.com.twinsflammer.common.shared.branches;

import br.com.twinsflammer.common.shared.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public enum Branch {
    MASTER("master"),
    DEVELOP("develop");

    private final String name;

    public File getPath() {
        File file = new File(Common.SERVER_HOME + "/files/jars/" + this.name);

        if (file.exists()) file.mkdirs();

        return file;
    }
}
