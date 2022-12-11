import util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1Part22022 {

    public static void main(String[] args) {
        List<Integer> calories = recupererTotauxCalories("entree.txt");
        int resultat = determinerSommeDesTroisPlusGrandsNombres(calories);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerSommeDesTroisPlusGrandsNombres(List<Integer> calories) {
        return calories.stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).limit(3).mapToInt(Integer::intValue).sum();
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
