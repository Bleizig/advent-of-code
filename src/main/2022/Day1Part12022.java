import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day1Part12022 {

    public static void main(String[] args) {
        List<Integer> calories = recupererTotauxCalories("entree.txt");
        int resultat = determinerPlusGrandNombre(calories);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerPlusGrandNombre(List<Integer> calories) {
        return calories.stream().max(Integer::compare).get();
    }

    private static List<Integer> recupererTotauxCalories(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        List<Integer> calories = new ArrayList<>();
        int caloriesElfEnCours = 0;
        for (String ligne : lignes) {
            if (ligne.isBlank()) {
                calories.add(caloriesElfEnCours);
                caloriesElfEnCours = 0;
            } else {
                caloriesElfEnCours += Integer.parseInt(ligne);
            }
        }
        return calories;
    }


}
