import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Util {
    private static final String REPERTOIRE_FICHIERS = "c:/dev/projets/advent-of-code/src/main/resources/";

    public static List<String> lireFichier(String nomFichier) {
        //TODO: Faudra bien trouver un moyen plus propre que de réferencer le répertoire.
        try {
            return Files.readAllLines(Paths.get(REPERTOIRE_FICHIERS + nomFichier), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
