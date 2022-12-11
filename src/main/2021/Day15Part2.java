import util.Util;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Day15Part2 {

    public static void main(String[] args) {
        CarteDesRisques carteDesRisques = recupererCarteGrotte("entree.txt");
        //carteDesRisques.afficher();
        long resultat = calculerScoreCheminMoinsRisque(carteDesRisques);
        System.out.println("Résultat=" + resultat);
    }

    private static long calculerScoreCheminMoinsRisque(CarteDesRisques carteDesRisques) {
        Point positionDepart = new Point(0, 0);

        List<CheminAParcourir> cheminsAParcourir = new ArrayList<>();
        cheminsAParcourir.add(new CheminAParcourir(positionDepart, 0L));

        int max = 0;
        CheminAParcourir meilleurChemin = null;

        List<Point> pointsDejaParcourus = new ArrayList<>();

        while (meilleurChemin == null) {
            max = Math.max(max, cheminsAParcourir.size());

            CheminAParcourir cheminAParcourir = recupererCheminLePlusPrometteur(cheminsAParcourir, carteDesRisques);
            cheminsAParcourir = supprimerTousLesAutresCheminsAParcourirACettePosition(cheminAParcourir, cheminsAParcourir);
            cheminsAParcourir.remove(cheminAParcourir);

            if (carteDesRisques.estPointArrivee(cheminAParcourir.position)) {
                //forcément le meilleur score
                meilleurChemin = cheminAParcourir;
            } else {
                pointsDejaParcourus.add(cheminAParcourir.position);
                List<Point> pointsPossibles = carteDesRisques.recupererCasesAdjacentes(cheminAParcourir.position);

                for (Point point : pointsPossibles) {
                    if (!pointsDejaParcourus.contains(point)) {
                        long risqueTotalPourAllerACePoint = cheminAParcourir.risqueTotal + carteDesRisques.getRisque(point);
                        cheminsAParcourir.add(new CheminAParcourir(point, risqueTotalPourAllerACePoint));
                    }
                }
            }
        }
        System.out.println("Max=" + max);
        return meilleurChemin.risqueTotal;
    }

    private static List<CheminAParcourir> supprimerTousLesAutresCheminsAParcourirACettePosition(CheminAParcourir cheminAParcourir, List<CheminAParcourir> cheminsAParcourir) {
        return cheminsAParcourir.stream().filter(c -> !c.position.equals(cheminAParcourir.position)).collect(Collectors.toList());
    }

    private static CheminAParcourir recupererCheminLePlusPrometteur(List<CheminAParcourir> cheminsAParcourir, CarteDesRisques carte) {
        return cheminsAParcourir.stream().min(Comparator.comparing(c -> c.risqueTotal + carte.distanceDeLArrivee(c.position))).orElseThrow();
    }

    private static CarteDesRisques recupererCarteGrotte(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        CarteDesRisques carteDesRisques = new CarteDesRisques();

        for (int i = 0; i < lignes.size(); i++) {
            String[] valeurs = lignes.get(i).split("");
            for (int j = 0; j < valeurs.length; j++) {
                ajouterLes25Points(i, j, Integer.parseInt(valeurs[j]), carteDesRisques, lignes.size(), valeurs.length);
            }
        }
        return carteDesRisques;
    }

    private static void ajouterLes25Points(int x, int y, int risque, CarteDesRisques carteDesRisques, int tailleX, int tailleY) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                carteDesRisques.ajouterRisqueAPoint(new Point(x + i * tailleX, y + j * tailleY), (risque - 1 + i + j) % 9 + 1);
            }
        }
    }

    record CheminAParcourir(Point position, long risqueTotal) {
    }

    private static class CarteDesRisques {
        //Débat oh toi qui lit ces lignes: tu aurais fait un array ? une liste de liste ? Viens m'en parler !
        private final Map<Point, Integer> mapPointRisques = new HashMap<>();
        private final Point pointArrivee = new Point();

        public int getRisque(Point point) {
            return mapPointRisques.get(point);
        }

        public boolean estPointArrivee(Point point) {
            return pointArrivee.equals(point);
        }

        public List<Point> recupererCasesAdjacentes(Point point) {
            List<Point> casesAdjacentes = new ArrayList<>();
            if (point.x > 0) {
                casesAdjacentes.add(new Point(point.x - 1, point.y));
            }
            if (point.x < pointArrivee.x) {
                casesAdjacentes.add(new Point(point.x + 1, point.y));
            }
            if (point.y > 0) {
                casesAdjacentes.add(new Point(point.x, point.y - 1));
            }
            if (point.y < pointArrivee.y) {
                casesAdjacentes.add(new Point(point.x, point.y + 1));
            }
            return casesAdjacentes;
        }

        public void ajouterRisqueAPoint(Point point, int risque) {
            mapPointRisques.put(point, risque);
            pointArrivee.x = Math.max(pointArrivee.x, point.x);
            pointArrivee.y = Math.max(pointArrivee.y, point.y);
        }

        public long distanceDeLArrivee(Point point) {
            return pointArrivee.x - point.x + pointArrivee.y - point.y;
        }

        public void afficher() {
            for (int i = 0; i <= pointArrivee.x; i++) {
                for (int j = 0; j <= pointArrivee.y; j++) {
                    System.out.print(mapPointRisques.get(new Point(i, j)));
                }
                System.out.println();
            }
        }
    }
}



