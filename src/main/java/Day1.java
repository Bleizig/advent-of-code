import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
    private static final String REPERTOIRE_FICHIERS = "c:/dev/projets/advent-of-code/src/main/resources/";

    public static void main(String[] args) {
        List<Integer> mesures = recupererMesures("entree.txt");
        int resultat = compterNombreMesuresPlusGrandesQuePrecedente(mesures);
        System.out.println("Réponse=" + resultat);
    }

    private static int compterNombreMesuresPlusGrandesQuePrecedente(List<Integer> mesures) {
        int nombreMesuresPlusGrandesQuePrecedentes = 0;
        Integer mesurePrecedente = null;
        for (Integer mesure : mesures) {
            if (mesurePrecedente != null && mesure > mesurePrecedente) {
                nombreMesuresPlusGrandesQuePrecedentes++;
            }
            mesurePrecedente = mesure;
        }
        return nombreMesuresPlusGrandesQuePrecedentes;
    }

    private static List<Integer> recupererMesures(String nomFichier) {
        List<String> lignes = lireFichier(nomFichier);
        return lignes.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    private static List<String> lireFichier(String nomFichier) {
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
