import java.util.List;
import java.util.stream.Collectors;

public class Day3Part12022 {

    public static void main(String[] args) {
        List<SacADos> sacsADos = lireSacADos();
        int resultat = determinerSommePriorites(sacsADos);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerSommePriorites(List<SacADos> sacsADos) {
        return sacsADos.stream().map(Day3Part12022::determinerPriorite).mapToInt(Integer::valueOf).sum();
    }

    private static int determinerPriorite(SacADos sacADos) {
        for (char itemCompartiment1 : sacADos.compartiment1.toCharArray()) {
            if (sacADos.compartiment2.indexOf(itemCompartiment1) != -1) {
                return calculerScoreItem(itemCompartiment1);
            }
        }
        throw new RuntimeException("Pas d'item en double");
    }

    private static int calculerScoreItem(char itemCompartiment) {
        if (itemCompartiment >= 'a') {
            return itemCompartiment - 'a' + 1;
        } else {
            return itemCompartiment - 'A' + 27;
        }
    }


    private static List<SacADos> lireSacADos() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(Day3Part12022::convertirLigneEnSacADos).collect(Collectors.toList());
    }

    private static SacADos convertirLigneEnSacADos(String ligne) {
        return new SacADos(ligne.substring(0, ligne.length() / 2), ligne.substring(ligne.length() / 2));
    }

    record SacADos(String compartiment1, String compartiment2) {
    }
}
