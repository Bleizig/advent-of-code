import util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class Day1Part1 {

    public static void main(String[] args) {
        List<Integer> mesures = recupererMesures("entree.txt");
        int resultat = compterNombreMesuresPlusGrandesQuePrecedente(mesures);
        System.out.println("Réponse=" + resultat);
    }

    static int compterNombreMesuresPlusGrandesQuePrecedente(List<Integer> mesures) {
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
        List<String> lignes = Util.lireFichier(nomFichier);
        return lignes.stream().map(Integer::valueOf).collect(Collectors.toList());
    }


}
