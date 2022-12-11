package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<String> lireFichier(String nomFichier) {
        File fichier = new File(Util.class.getClassLoader().getResource(nomFichier).getFile());
        try {
            return Files.readAllLines(fichier.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
