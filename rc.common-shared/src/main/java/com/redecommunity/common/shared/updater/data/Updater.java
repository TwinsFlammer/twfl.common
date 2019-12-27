package com.redecommunity.common.shared.updater.data;

import com.redecommunity.common.shared.branches.Branch;
import com.redecommunity.common.shared.util.Printer;
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
        File remoteFile = new File(this.branch.getPath() + "/" + this.file.getName());

        if (!this.file.exists() || !remoteFile.exists()) throw new FileNotFoundException("Não foi possível localizar o arquivo " + file.getName());

        Printer.INFO.coloredPrint("&eVerificando se há atualizações do arquivo " + this.file.getName());

        if (remoteFile.lastModified() > this.file.lastModified()) {
            Printer.INFO.coloredPrint("&eAtualização encontrada, baixando nova versão do arquivo " + this.file.getName());

            Files.copy(remoteFile.toPath(), this.file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Printer.INFO.coloredPrint("&aArquivo " + this.file.getName() + " atualizado com sucesso!");
        }
    }
}
