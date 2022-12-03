import java.util.List;

public class Day11Part2 {

    public static final int TAILLE_MATRICE = 10;

    public static void main(String[] args) {
        int[][] lignes = recupererMatricePieuvres("entree.txt");
        long resultat = determinerEtapeDeFlashesTousSimultanes(lignes);
        System.out.println("Réponse=" + resultat);
    }

    private static long determinerEtapeDeFlashesTousSimultanes(int[][] matricePieuvresDepart) {
        int[][] matriceActuelle = matricePieuvresDepart;
        long etape = 0;
        while (calculerNombrePieuvresQuiViennentDeFlasher(matriceActuelle) < TAILLE_MATRICE * TAILLE_MATRICE) {
            matriceActuelle = faireAvancerDUneEtape(matriceActuelle);
            etape++;
        }
        return etape;
    }

    private static long calculerNombrePieuvresQuiViennentDeFlasher(int[][] matricePieuvresDepart) {
        long resultat = 0;
        for (int i = 0; i < TAILLE_MATRICE; i++) {
            for (int j = 0; j < TAILLE_MATRICE; j++) {
                if (matricePieuvresDepart[i][j] == 0) {
                    resultat++;
                }
            }
        }
        return resultat;
    }

    private static int[][] faireAvancerDUneEtape(int[][] matricePieuvre) {
        int[][] matricePieuvreResultat = augmenterEnergieDeToutesLesPieuvres(matricePieuvre);
        gererLesFlashesEnCascade(matricePieuvreResultat);

        return matricePieuvreResultat;
    }

    private static void gererLesFlashesEnCascade(int[][] matricePieuvre) {
        boolean aEuUnFlash = true;
        while (aEuUnFlash) {
            aEuUnFlash = false;
            for (int i = 0; i < matricePieuvre.length; i++) {
                for (int j = 0; j < matricePieuvre[i].length; j++) {
                    if (matricePieuvre[i][j] > 9) {
                        gererFlash(matricePieuvre, i, j);
                        aEuUnFlash = true;
                    }
                }
            }
        }
    }

    private static void gererFlash(int[][] matricePieuvres, int x, int y) {
        matricePieuvres[x][y] = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(matricePieuvres.length - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(matricePieuvres.length - 1, y + 1); j++) {
                incrementerSiPasDejaFlashe(matricePieuvres, i, j);
            }
        }
    }

    private static void incrementerSiPasDejaFlashe(int[][] matricePieuvres, int i, int j) {
        if (matricePieuvres[i][j] > 0) {
            matricePieuvres[i][j]++;
        }
    }

    private static int[][] augmenterEnergieDeToutesLesPieuvres(int[][] matricePieuvre) {
        int[][] matriceResultat = new int[matricePieuvre.length][matricePieuvre[0].length];
        for (int i = 0; i < matricePieuvre.length; i++) {
            for (int j = 0; j < matricePieuvre[i].length; j++) {
                matriceResultat[i][j] = matricePieuvre[i][j] + 1;
            }
        }
        return matriceResultat;
    }

    private static int[][] recupererMatricePieuvres(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        int[][] matrice = new int[TAILLE_MATRICE][TAILLE_MATRICE];
        for (int i = 0; i < TAILLE_MATRICE; i++) {
            matrice[i] = convertirLigneEnTableauEntiers(lignes.get(i));
        }

        return matrice;
    }

    private static int[] convertirLigneEnTableauEntiers(String ligne) {
        int[] tableau = new int[TAILLE_MATRICE];
        for (int i = 0; i < TAILLE_MATRICE; i++) {
            tableau[i] = Integer.parseInt(ligne.substring(i, i + 1));
        }
        return tableau;
    }
}



