import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3Part2 {

    public static void main(String[] args) {
        List<String> diagnostique = recupererDiagnostique("entree.txt");
        int resultat = calculerSupportVie(diagnostique);
        System.out.println("Réponse=" + resultat);
    }

    private static int calculerSupportVie(List<String> diagnostique) {
        int tauxGenerationO2 = calculerTauxGenerationO2(diagnostique);
        int tauxSuppressionCO2 = calculerTauxSuppressionC02(diagnostique);
        return tauxGenerationO2 * tauxSuppressionCO2;
    }

    private static int calculerTauxGenerationO2(List<String> diagnostique) {
        String tauxChaine = lancerHighlanderAvecStrategieDeSelection(diagnostique, Day3Part2::determinerLaValeurLaPlusPresenteAuBitDonne);
        return Integer.parseInt(tauxChaine, 2);
    }

    private static int calculerTauxSuppressionC02(List<String> diagnostiqueEntiers) {
        String tauxChaine = lancerHighlanderAvecStrategieDeSelection(diagnostiqueEntiers, Day3Part2::determinerLaValeurLaMoinsPresenteAuBitDonne);
        return Integer.parseInt(tauxChaine, 2);
    }

    private static char determinerLaValeurLaPlusPresenteAuBitDonne(List<String> liste, int position) {
        long nombreDeUnsAPosition = getNombreDeUnsAPosition(liste, position);
        long nombreDeZerosAPosition = liste.size() - nombreDeUnsAPosition;
        return (nombreDeUnsAPosition >= nombreDeZerosAPosition) ? '1' : '0';
    }

    private static char determinerLaValeurLaMoinsPresenteAuBitDonne(List<String> liste, int position) {
        long nombreDeUnsAPosition = getNombreDeUnsAPosition(liste, position);
        long nombreDeZerosAPosition = liste.size() - nombreDeUnsAPosition;
        return (nombreDeZerosAPosition <= nombreDeUnsAPosition) ? '0' : '1';
    }

    private static long getNombreDeUnsAPosition(List<String> liste, int position) {
        return liste.stream().filter(chaine -> (chaine.charAt(position) == '1')).count();
    }

    private static String lancerHighlanderAvecStrategieDeSelection(List<String> liste, StrategieSelection strategie) {
        List<String> candidatsPossibles = new ArrayList<>(liste);
        int position = 0;
        while (candidatsPossibles.size() > 1) {
            if (position == liste.get(0).length()) {
                throw new RuntimeException("Houston, we have a problem");
            }
            char valeurAConserver = strategie.determinerValeurAConserver(candidatsPossibles, position);
            candidatsPossibles = conserverUniquementValeursCorrespondantes(candidatsPossibles, position, valeurAConserver);
            position++;
        }
        return candidatsPossibles.get(0);
    }

    private static List<String> conserverUniquementValeursCorrespondantes(List<String> liste, int position, char valeurACorrespondre) {
        return liste.stream().filter(chaine -> chaine.charAt(position) == valeurACorrespondre).collect(Collectors.toList());
    }

    @FunctionalInterface
    interface StrategieSelection {
        char determinerValeurAConserver(List<String> liste, int positionAAnalyser);
    }

    private static List<String> recupererDiagnostique(String nomFichier) {
        return Util.lireFichier(nomFichier);
    }
}
