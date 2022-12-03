import java.util.List;

public class Day17Part1 {

    public static void main(String[] args) {
        int yMax = recupererYMax("entree.txt");
        int resultat = calculerMeilleureHauteur(yMax);
        System.out.println("Résultat=" + resultat);
    }

    private static int calculerMeilleureHauteur(int yMax) {
        return Math.abs(yMax) * (Math.abs(yMax) - 1) / 2;

    }

    private static int recupererYMax(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        String ligne0 = lignes.get(0);
        return Integer.parseInt(ligne0.substring(ligne0.indexOf("y=") + 2, ligne0.indexOf("..", ligne0.indexOf("y="))));
    }
}



