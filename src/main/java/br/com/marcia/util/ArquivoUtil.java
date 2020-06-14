package br.com.marcia.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoUtil {

    public static final String NOME_ARQUIVO = "answer.json";

    public static void salvar(String texto)  {
        try {
            Path path = Paths.get(System.getProperty("user.dir").concat(File.separator).concat(NOME_ARQUIVO));
            Files.write(path, texto.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
