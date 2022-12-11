import util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15Part1 {

    public static void main(String[] args) {
        CarteDesRisques carteDesRisques = recupererCarteGrotte("entree.txt");
        long resultat = calculerScoreCheminMoinsRisque(carteDesRisques);
        System.out.println("Résultat=" + resultat);
    }

    private static long calculerScoreCheminMoinsRisque(CarteDesRisques carteDesRisques) {
        Point positionDepart = new Point(0, 0);

        List<CheminAParcourir> cheminsAParcourir = new ArrayList<>();
        cheminsAParcourir.add(new CheminAParcourir(positionDepart, 0L));

        Map<Point, Long> plusPetitsRisquesPourAllerAuxPoints = new HashMap<>();

        while (!cheminsAParcourir.isEmpty()) {
            //Pas de stack pour être plus BFS que DFS.
            CheminAParcourir cheminAParcourir = cheminsAParcourir.get(0);
            cheminsAParcourir.remove(0);
            if (!carteDesRisques.estPointArrivee(cheminAParcourir.position)) {
                List<Point> pointsPossibles = carteDesRisques.recupererCasesAdjacentes(cheminAParcourir.position);

                for (Point point : pointsPossibles) {
                    long risqueTotalPourAllerACePoint = cheminAParcourir.risqueTotal + carteDesRisques.getRisque(point);
                    if (risqueTotalPourAllerACePoint < plusPetitsRisquesPourAllerAuxPoints.getOrDefault(point, Long.MAX_VALUE)) {
                        plusPetitsRisquesPourAllerAuxPoints.put(point, risqueTotalPourAllerACePoint);
                        cheminsAParcourir.add(new CheminAParcourir(point, risqueTotalPourAllerACePoint));
                    }
                }
            }
        }
        return plusPetitsRisquesPourAllerAuxPoints.get(carteDesRisques.getPointArrivee());
    }

    private static CarteDesRisques recupererCarteGrotte(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        CarteDesRisques carteDesRisques = new CarteDesRisques();

        for (int i = 0; i < lignes.size(); i++) {
            String[] valeurs = lignes.get(i).split("");
            for (int j = 0; j < valeurs.length; j++) {
                carteDesRisques.ajouterRisqueAPoint(new Point(i, j), Integer.parseInt(valeurs[j]));
            }
        }
        return carteDesRisques;
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

        public Point getPointArrivee() {
            return pointArrivee;
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
    }
}



