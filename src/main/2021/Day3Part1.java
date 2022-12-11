import util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class Day3Part1 {

    public static void main(String[] args) {
        List<String> diagnostique = recupererDiagnostique("entree.txt");
        int resultat = calculerConsommation(diagnostique);
        System.out.println("Réponse=" + resultat);
    }

    private static int calculerConsommation(List<String> diagnostique) {
        List<int[]> diagnostiqueTableau = convertirDiagnostiqueEnTableauEntiers(diagnostique);
        int[] totauxDesUns = calculerTotauxDeUnsDeChaqueBit(diagnostiqueTableau);
        return calculerConsommation(totauxDesUns, diagnostiqueTableau.size());
    }

    private static int calculerConsommation(int[] totauxDesUns, int size) {
        StringBuilder gammaString = new StringBuilder();
        StringBuilder epsilonString = new StringBuilder();
        int moitieTaille = size / 2;
        for (int total : totauxDesUns) {
            if (total > moitieTaille) {
                gammaString.append(1);
                epsilonString.append(0);
            } else {
                gammaString.append(0);
                epsilonString.append(1);
            }
        }
        int gammaRate = Integer.parseInt(gammaString.toString(), 2);
        int epsilonRate = Integer.parseInt(epsilonString.toString(), 2);
        return gammaRate * epsilonRate;
    }

    private static int[] calculerTotauxDeUnsDeChaqueBit(List<int[]> diagnostiqueTableau) {
        return diagnostiqueTableau.stream().reduce(Day3Part1::additionneEntiersUnAUn).get();
    }

    private static int[] additionneEntiersUnAUn(int[] t1, int[] t2) {
        int[] resultat = new int[t1.length];
        for (int i = 0; i < t1.length; i++) {
            resultat[i] = t1[i] + t2[i];
        }
        return resultat;
    }

    private static List<int[]> convertirDiagnostiqueEnTableauEntiers(List<String> diagnostique) {
        return diagnostique.stream().map(Day3Part1::convertirLigneDiagnostiqueEnTableauEntiers).collect(Collectors.toList());
    }

    private static int[] convertirLigneDiagnostiqueEnTableauEntiers(String ligne) {
        int[] resultat = new int[ligne.length()];
        for (int i = 0; i < ligne.length(); i++) {
            resultat[i] = Integer.parseInt(ligne.substring(i, i + 1));
        }
        return resultat;
    }

    private static List<String> recupererDiagnostique(String nomFichier) {
        return Util.lireFichier(nomFichier);
    }
}
