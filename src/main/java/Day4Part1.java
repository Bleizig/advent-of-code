import java.util.ArrayList;
import java.util.List;

public class Day4Part1 {

    public static final int TAILLE_CARRE_BINGO = 5;
    public static final int NB_LIGNES_ESPACEMENT_ENTRE_CARTES = 1;

    public static void main(String[] args) {
        List<String> lignes = recupererLignes("entree.txt");
        List<Integer> numerosTires = recupererNumerosTires(lignes.get(0));
        lignes.remove(0);
        List<CarteBingo> cartesBingo = recupererCartesBingo(lignes);
        int resultat = calculerScoreCarteGagnante(numerosTires, cartesBingo);
        System.out.println("Réponse=" + resultat);
    }

    private static List<CarteBingo> recupererCartesBingo(List<String> lignes) {
        List<CarteBingo> cartesBingo = new ArrayList<>();
        int positionLecture = 0;
        while (positionLecture < lignes.size()) {
            CarteBingo carteBingo = convertirEnCarteBingo(lignes.subList(positionLecture + NB_LIGNES_ESPACEMENT_ENTRE_CARTES, positionLecture + NB_LIGNES_ESPACEMENT_ENTRE_CARTES + TAILLE_CARRE_BINGO));
            positionLecture += TAILLE_CARRE_BINGO + NB_LIGNES_ESPACEMENT_ENTRE_CARTES;
            cartesBingo.add(carteBingo);
        }

        return cartesBingo;
    }

    private static CarteBingo convertirEnCarteBingo(List<String> lignes) {
        int[][] numeros = new int[TAILLE_CARRE_BINGO][TAILLE_CARRE_BINGO];

        for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
            String[] numerosChaines = lignes.get(i).split(" ");
            List<String> numerosChainesSansEspacesALaCon = supprimerChainesVidesALaCon(numerosChaines);
            for (int j = 0; j < TAILLE_CARRE_BINGO; j++) {
                numeros[i][j] = Integer.parseInt(numerosChainesSansEspacesALaCon.get(j));
            }
        }

        return new CarteBingo(numeros);
    }

    private static List<String> supprimerChainesVidesALaCon(String[] numerosChaines) {
        List<String> resultat = new ArrayList<>();
        for (String numeroChaine : numerosChaines) {
            if (!numeroChaine.isEmpty()) {
                resultat.add(numeroChaine);
            }
        }
        return resultat;
    }

    private static List<Integer> recupererNumerosTires(String chaine) {
        String[] numerosChaines = chaine.split(",");
        List<Integer> numerosInt = new ArrayList<>();
        for (String numero : numerosChaines) {
            numerosInt.add(Integer.valueOf(numero));
        }

        return numerosInt;
    }

    private static int calculerScoreCarteGagnante(List<Integer> numerosTires, List<CarteBingo> cartesBingo) {
        for (Integer numeroTire : numerosTires) {
            for (CarteBingo carteBingo : cartesBingo) {
                carteBingo.appliquerTirage(numeroTire);
                if (carteBingo.estBingo()) {
                    return carteBingo.calculerSommeNumerosNonTires() * numeroTire;
                }
            }
        }
        throw new RuntimeException("Aucune carte gagnante");
    }

    private static List<String> recupererLignes(String nomFichier) {
        return Util.lireFichier(nomFichier);
    }

    private static class CarteBingo {
        int[][] numeros;
        boolean[][] casesGagnantes = new boolean[TAILLE_CARRE_BINGO][TAILLE_CARRE_BINGO];

        public CarteBingo(int[][] numeros) {
            this.numeros = numeros;
        }

        public void appliquerTirage(Integer numeroTire) {
            for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
                for (int j = 0; j < TAILLE_CARRE_BINGO; j++) {
                    if (numeros[i][j] == numeroTire) {
                        marquerNumeroGagnant(i, j);
                        numeros[i][j] = 0;
                    }
                }
            }
        }

        private void marquerNumeroGagnant(int i, int j) {
            casesGagnantes[i][j] = true;
        }

        public boolean estBingo() {
            return estUneLigneGagnante() || estUneColonneGagnante();
        }

        private boolean estUneColonneGagnante() {
            for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
                if (estColonneGagnante(i)) {
                    return true;
                }
            }
            return false;

        }

        private boolean estColonneGagnante(int indexColonne) {
            for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
                if (!casesGagnantes[i][indexColonne]) {
                    return false;
                }
            }
            return true;
        }

        private boolean estUneLigneGagnante() {
            for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
                if (estLigneGagnante(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean estLigneGagnante(int indexLigne) {
            for (int j = 0; j < TAILLE_CARRE_BINGO; j++) {
                if (!casesGagnantes[indexLigne][j]) {
                    return false;
                }
            }
            return true;
        }

        public int calculerSommeNumerosNonTires() {
            int somme = 0;
            for (int i = 0; i < TAILLE_CARRE_BINGO; i++) {
                for (int j = 0; j < TAILLE_CARRE_BINGO; j++) {
                    somme += numeros[i][j];
                }
            }
            return somme;
        }
    }
}



