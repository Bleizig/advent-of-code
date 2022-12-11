import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day6Part1 {

    public static final int TIMER_ACCOUCHEMENT = 0;
    public static final int TIMER_NOUVEAU_BEBE = 8;
    public static final int TIMER_APRES_ACCOUCHEMENT = 6;

    public static void main(String[] args) {
        List<Integer> poissons = recupererPoissons("entree.txt");
        int resultat = calculerNombrePoissonsApres80Jours(poissons);
        System.out.println("Réponse=" + resultat);
    }

    private static int calculerNombrePoissonsApres80Jours(List<Integer> poissons) {
        List<Integer> poissonsActuels = new ArrayList<>(poissons);
        for (int i = 0; i < 80; i++) {
            poissonsActuels = faireEcoulerUnJour(poissonsActuels);
        }
        return poissonsActuels.size();
    }

    private static List<Integer> faireEcoulerUnJour(List<Integer> poissonsDepart) {
        List<Integer> poissonsArrivee = new ArrayList<>();
        for (Integer poissonDepart: poissonsDepart) {
            if (poissonDepart > TIMER_ACCOUCHEMENT) {
                poissonsArrivee.add(poissonDepart -1 );
            } else if (poissonDepart == 0) {
                poissonsArrivee.add(TIMER_APRES_ACCOUCHEMENT);
                poissonsArrivee.add(TIMER_NOUVEAU_BEBE);
            }
        }
        return poissonsArrivee;
    }

    private static List<Integer> recupererPoissons(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        String poissonsChaine = lignes.get(0);
        String[] tableauPoissonsChaine = poissonsChaine.split(",");

        List<Integer> poissons = new ArrayList<>();
        for (String poisson : tableauPoissonsChaine) {
            poissons.add(Integer.parseInt(poisson));
        }

        return poissons;
    }
}



