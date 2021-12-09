import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part2 {

    public static final int LIMITE_BASSIN = 9;

    public static void main(String[] args) {
        int[][] carte = recupererCartePoints("entree.txt");
        long resultat = calculerProduitDesTroisPlusGrosBassins(carte);
        System.out.println("Réponse=" + resultat);
    }

    private static long calculerProduitDesTroisPlusGrosBassins(int[][] carte) {
        List<Point> pointsBas = determinerTousLesPointsBas(carte);
        List<Long> taillesBassins = calculerTaillesBassins(carte, pointsBas);
        List<Long> taillesBassinsTrieesDescendant = taillesBassins.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        return taillesBassinsTrieesDescendant.get(0) * taillesBassinsTrieesDescendant.get(1) * taillesBassinsTrieesDescendant.get(2);
    }

    private static List<Long> calculerTaillesBassins(int[][] carte, List<Point> pointsBas) {
        return pointsBas.stream().map(p -> calculeTailleBassin(carte, p)).collect(Collectors.toList());
    }

    private static long calculeTailleBassin(int[][] carte, Point pointDepart) {
        List<Point> pointsParcourus = new ArrayList<>();
        List<Point> pointsAParcourir = new ArrayList<>();

        pointsAParcourir.add(pointDepart);

        while (pointsAParcourir.size() > 0) {
            Point point = pointsAParcourir.get(0);
            pointsParcourus.add(point);
            pointsAParcourir.remove(0);
            if (carte[point.x][point.y] < LIMITE_BASSIN) {
                List<Point> casesAdjacentes = recupereCasesAdjacentes(point, carte.length - 1, carte[0].length - 1);
                for (Point caseAdjacente : casesAdjacentes) {
                    if (!pointsParcourus.contains(caseAdjacente) && !pointsAParcourir.contains(caseAdjacente)) {
                        pointsAParcourir.add(caseAdjacente);
                    }
                }
            }
        }

        return pointsParcourus.stream().filter(p -> carte[p.x][p.y] < 9).count();
    }

    private static List<Point> recupereCasesAdjacentes(Point point, int xMax, int yMax) {
        List<Point> casesAdjacentes = new ArrayList<>();
        recupereCaseOuest(point, casesAdjacentes);
        recupereCaseEst(point, xMax, casesAdjacentes);
        recupereCaseNord(point, casesAdjacentes);
        recupereCaseSud(point, yMax, casesAdjacentes);
        return casesAdjacentes;
    }

    private static void recupereCaseSud(Point point, int yMax, List<Point> casesAdjacentes) {
        if (point.y < yMax) {
            casesAdjacentes.add(new Point(point.x, point.y + 1));
        }
    }

    private static void recupereCaseNord(Point point, List<Point> casesAdjacentes) {
        if (point.y > 0) {
            casesAdjacentes.add(new Point(point.x, point.y - 1));
        }
    }

    private static void recupereCaseEst(Point point, int xMax, List<Point> casesAdjacentes) {
        if (point.x < xMax) {
            casesAdjacentes.add(new Point(point.x + 1, point.y));
        }
    }

    private static void recupereCaseOuest(Point point, List<Point> casesAdjacentes) {
        if (point.x > 0) {
            casesAdjacentes.add(new Point(point.x - 1, point.y));
        }
    }

    private static List<Point> determinerTousLesPointsBas(int[][] carte) {
        List<Point> pointsBas = new ArrayList<>();
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                if (estUnPointBas(carte, i, j)) {
                    pointsBas.add(new Point(i, j));
                }
            }
        }
        return pointsBas;
    }

    private static boolean estUnPointBas(int[][] carte, int x, int y) {
        return estOuestPlusHaut(carte, x, y) && estNordPlusHaut(carte, x, y) && estEstPlusHaut(carte, x, y) && estSudPlusHaut(carte, x, y);
    }

    private static boolean estSudPlusHaut(int[][] carte, int x, int y) {
        return y == carte[0].length - 1 || carte[x][y + 1] > carte[x][y];
    }

    private static boolean estEstPlusHaut(int[][] carte, int x, int y) {
        return x == carte.length - 1 || carte[x + 1][y] > carte[x][y];
    }

    private static boolean estNordPlusHaut(int[][] carte, int x, int y) {
        return y == 0 || carte[x][y - 1] > carte[x][y];
    }

    private static boolean estOuestPlusHaut(int[][] carte, int x, int y) {
        return x == 0 || carte[x - 1][y] > carte[x][y];
    }

    private static int[][] recupererCartePoints(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        int[][] carte = new int[lignes.size()][lignes.get(0).length()];
        int indexLigne = 0;
        for (String ligne : lignes) {
            char[] chars = ligne.toCharArray();
            int[] ligneEnEntiers = new int[ligne.length()];
            for (int i = 0; i < chars.length; i++) {
                ligneEnEntiers[i] = Character.getNumericValue(chars[i]);
            }
            carte[indexLigne] = ligneEnEntiers;
            indexLigne++;
        }
        return carte;
    }
}



