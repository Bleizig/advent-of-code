import java.util.ArrayList;
import java.util.List;

public class Day9Part1 {

    public static void main(String[] args) {
        int[][] carte = recupererCartePoints("entree.txt");
        int resultat = calculerSommeDeToutesLesRisques(carte);
        System.out.println("Réponse=" + resultat);
    }

    private static int calculerSommeDeToutesLesRisques(int[][] carte) {
        List<Integer> pointsBas = determinerTousLesPointsBas(carte);

        return pointsBas.stream().map(p -> p + 1).reduce(Integer::sum).orElseThrow();
    }

    private static List<Integer> determinerTousLesPointsBas(int[][] carte) {
        List<Integer> pointsBas = new ArrayList<>();
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                if (estUnPointBas(carte, i, j)) {
                    pointsBas.add(carte[i][j]);
                }
            }
        }
        return pointsBas;
    }

    private static boolean estUnPointBas(int[][] carte, int x, int y) {
        return estOuestPlusHaut(carte, x, y) && estNordPlusHaut(carte, x, y) && estEstPlusHaut(carte, x, y) && estSudPlusHaut(carte, x, y);
    }

    private static boolean estSudPlusHaut(int[][] carte, int x, int y) {
        return y == carte[0].length -1 || carte[x][y + 1] > carte[x][y];
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



