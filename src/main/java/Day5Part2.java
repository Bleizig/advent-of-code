import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5Part2 {

    public static final String SEPARATEUR_POINTS = "->";
    public static final String SEPARATEUR_COORDONNES = ",";

    public static void main(String[] args) {
        List<Droite> lignes = recupererDroites("entree.txt");
        long resultat = calculerNombreCasesAvecAuMoinsUnRecouvrement(lignes);
        System.out.println("Réponse=" + resultat);
    }

    private static long calculerNombreCasesAvecAuMoinsUnRecouvrement(List<Droite> lignes) {
        Map<Point, Integer> pointsParcourus = dessinerToutesLesDroites(lignes);
        return determinerNombrePointsParcourusAuMoinsDeuxFois(pointsParcourus);
    }

    private static long determinerNombrePointsParcourusAuMoinsDeuxFois(Map<Point, Integer> pointsParcourus) {
        return pointsParcourus.values().stream().filter(i -> i > 1).count();
    }

    private static Map<Point, Integer> dessinerToutesLesDroites(List<Droite> lignes) {
        Map<Point, Integer> pointsParcourus = new HashMap<>();
        for (Droite ligne : lignes) {
            dessinerDroite(pointsParcourus, ligne);
        }
        return pointsParcourus;
    }

    private static void dessinerDroite(Map<Point, Integer> espace, Droite ligne) {
        int incrementX = 0;
        if (ligne.p1.x < ligne.p2.x) {
            incrementX = 1;
        } else if (ligne.p1.x > ligne.p2.x) {
            incrementX = -1;
        }

        int incrementY = 0;
        if (ligne.p1.y < ligne.p2.y) {
            incrementY = 1;
        } else if (ligne.p1.y > ligne.p2.y) {
            incrementY = -1;
        }

        dessinerDroite(espace, ligne.p1, ligne.p2, incrementX, incrementY);
    }

    private static void dessinerDroite(Map<Point, Integer> espace, Point depart, Point arrivee, int incrementX, int incrementY) {
        int x = depart.x;
        int y = depart.y;

        ajouterPoint(espace, new Point(x, y));
        while (!(x == arrivee.x && y == arrivee.y)) {
            x += incrementX;
            y += incrementY;
            ajouterPoint(espace, new Point(x, y));
        }
    }

    private static void ajouterPoint(Map<Point, Integer> espace, Point point) {
        Integer totalPassages = espace.get(point);
        if (totalPassages == null) {
            totalPassages = 0;
        }
        totalPassages++;
        espace.put(point, totalPassages);
    }


    private static List<Droite> recupererDroites(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        List<Droite> droites = new ArrayList<>();
        lignes.forEach(l -> {
            Droite droite = recupererDroite(l);
            droites.add(droite);
        });

        return droites;
    }

    private static Droite recupererDroite(String droiteString) {
        String[] points = droiteString.split(SEPARATEUR_POINTS);
        Point p1 = recupererPoint(points[0]);
        Point p2 = recupererPoint(points[1]);

        return new Droite(p1, p2);
    }

    private static Point recupererPoint(String pointChaine) {
        String[] coordonnees = pointChaine.split(SEPARATEUR_COORDONNES);
        int x = Integer.parseInt(coordonnees[0].trim());
        int y = Integer.parseInt(coordonnees[1].trim());

        return new Point(x, y);
    }

    private static class Droite {
        Point p1;
        Point p2;

        public Droite(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
}



