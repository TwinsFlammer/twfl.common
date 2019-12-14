package com.redecommunity.common.shared.updater.data;

import com.redecommunity.common.shared.branches.Branch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class Updater {
    private final File file;
    private final Branch branch;

    public void download() throws IOException {
        if (!this.file.exists()) throw new FileNotFoundException("Não foi possível localizar o arquivo " + file.getName());

        File remoteFile = new File(this.branch.getPath() + "/" + this.file.getName());

        if (remoteFile.lastModified() > this.file.lastModified()) {
            System.out.println("Atualização encontrada, baixando nova versão do arquivo " + this.file.getName());

            Files.copy(remoteFile.toPath(), this.file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Arquivo " + this.file.getName() + " atualizado com sucesso!");
        }
    }
}
