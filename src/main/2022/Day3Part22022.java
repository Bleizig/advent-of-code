import java.util.ArrayList;
import java.util.List;

public class Day3Part22022 {

    public static void main(String[] args) {
        List<SacsGroupe> sacsGroupes = lireSacADos();
        int resultat = determinerSommePriorites(sacsGroupes);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerSommePriorites(List<SacsGroupe> sacsGroupes) {
        return sacsGroupes.stream().mapToInt(Day3Part22022::determinerPriorite).sum();
    }

    private static Integer determinerPriorite(SacsGroupe sacsGroupe) {
        for (char itemSac1 : sacsGroupe.sac1.toCharArray()) {
            if (sacsGroupe.sac2.indexOf(itemSac1) != -1 && sacsGroupe.sac3.indexOf(itemSac1) != -1) {
                return calculerScoreItem(itemSac1);
            }
        }
        throw new RuntimeException("Pas d'item en triple");
    }

    private static int calculerScoreItem(char itemCompartiment) {
        if (itemCompartiment >= 'a') {
            return itemCompartiment - 'a' + 1;
        } else {
            return itemCompartiment - 'A' + 27;
        }
    }

    private static List<SacsGroupe> lireSacADos() {
        List<String> lignes = Util.lireFichier("entree.txt");

        List<SacsGroupe> sacsGroupes = new ArrayList<>();
        for (int i = 0; i < lignes.size(); i = i + 3) {
            SacsGroupe sacsGroupe = new SacsGroupe(lignes.get(i), lignes.get(i + 1), lignes.get(i + 2));
            sacsGroupes.add(sacsGroupe);
        }
        return sacsGroupes;
    }

    record SacsGroupe(String sac1, String sac2, String sac3) {
    }
}
