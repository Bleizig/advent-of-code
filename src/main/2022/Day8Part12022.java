import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8Part12022 {

    public static void main(String[] args) {
        List<List<Integer>> hauteursArbres = recupererHauteursArbres();
        long resultat = determinerNombreArbresVisiblesDeLExterieur(hauteursArbres);
        System.out.println("Réponse=" + resultat);
    }

    private static List<List<Integer>> recupererHauteursArbres() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(l -> l.chars().boxed().map(Character::getNumericValue).collect(Collectors.toList())).collect(Collectors.toList());
    }

    private static long determinerNombreArbresVisiblesDeLExterieur(List<List<Integer>> hauteursArbres) {
        Boolean[][] visibilitesArbres = new Boolean[hauteursArbres.size()][hauteursArbres.get(0).size()];

        visibilitesArbres = metAJourVisibilitesAvecParcoursOuestEst(hauteursArbres, visibilitesArbres);
        visibilitesArbres = metAJourVisibilitesAvecParcoursEstOuest(hauteursArbres, visibilitesArbres);
        visibilitesArbres = metAJourVisibilitesAvecParcoursNordSud(hauteursArbres, visibilitesArbres);
        visibilitesArbres = metAJourVisibilitesAvecParcoursSudNord(hauteursArbres, visibilitesArbres);

        return Arrays.stream(visibilitesArbres).flatMap(Arrays::stream).filter(b -> b != null && b).count();
    }


    private static Boolean[][] metAJourVisibilitesAvecParcoursOuestEst(List<List<Integer>> hauteursArbres, Boolean[][] visibilitesArbresInitiales) {
        Boolean[][] visibilitesArbres = visibilitesArbresInitiales.clone();

        for (int i = 0; i < visibilitesArbres.length; i++) {
            int maxHauteurArbre = -1;
            for (int j = 0; j < visibilitesArbres[i].length; j++) {
                Integer hauteurArbre = hauteursArbres.get(i).get(j);
                if (hauteurArbre > maxHauteurArbre) {
                    visibilitesArbres[i][j] = true;
                    maxHauteurArbre = hauteurArbre;
                }
            }
        }

        return visibilitesArbres;
    }

    private static Boolean[][] metAJourVisibilitesAvecParcoursEstOuest(List<List<Integer>> hauteursArbres, Boolean[][] visibilitesArbresInitiales) {
        Boolean[][] visibilitesArbres = visibilitesArbresInitiales.clone();

        for (int i = 0; i < visibilitesArbres.length; i++) {
            int maxHauteurArbre = -1;
            for (int j = visibilitesArbres[i].length - 1; j >= 0; j--) {
                Integer hauteurArbre = hauteursArbres.get(i).get(j);
                if (hauteurArbre > maxHauteurArbre) {
                    visibilitesArbres[i][j] = true;
                    maxHauteurArbre = hauteurArbre;
                }
            }
        }

        return visibilitesArbres;
    }


    private static Boolean[][] metAJourVisibilitesAvecParcoursNordSud(List<List<Integer>> hauteursArbres, Boolean[][] visibilitesArbresInitiales) {
        Boolean[][] visibilitesArbres = visibilitesArbresInitiales.clone();

        for (int i = 0; i < visibilitesArbres.length; i++) {
            int maxHauteurArbre = -1;
            for (int j = 0; j < visibilitesArbres[i].length; j++) {
                Integer hauteurArbre = hauteursArbres.get(j).get(i);
                if (hauteurArbre > maxHauteurArbre) {
                    visibilitesArbres[j][i] = true;
                    maxHauteurArbre = hauteurArbre;
                }
            }
        }

        return visibilitesArbres;
    }

    private static Boolean[][] metAJourVisibilitesAvecParcoursSudNord(List<List<Integer>> hauteursArbres, Boolean[][] visibilitesArbresInitiales) {
        Boolean[][] visibilitesArbres = visibilitesArbresInitiales.clone();

        for (int i = 0; i < visibilitesArbres.length; i++) {
            int maxHauteurArbre = -1;
            for (int j = visibilitesArbres[i].length - 1; j >= 0; j--) {
                Integer hauteurArbre = hauteursArbres.get(j).get(i);
                if (hauteurArbre > maxHauteurArbre) {
                    visibilitesArbres[j][i] = true;
                    maxHauteurArbre = hauteurArbre;
                }
            }
        }

        return visibilitesArbres;
    }
}
