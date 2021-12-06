import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) {
        List<Integer> mesures = recupererMesures("entree.txt");
        int resultat = compterNombreSommesFenetresDeTroisPlusGrandesQuePrecedente(mesures);
        System.out.println("Réponse=" + resultat);
    }

    private static int compterNombreSommesFenetresDeTroisPlusGrandesQuePrecedente(List<Integer> mesures) {
        List<Integer> sommesDeFenetresDeTrois = calculerSommesFenetresDeTrois(mesures);
        return Day1.compterNombreMesuresPlusGrandesQuePrecedente(sommesDeFenetresDeTrois);
    }

    private static List<Integer> calculerSommesFenetresDeTrois(List<Integer> mesures) {
        List<Integer> resultat = new ArrayList<>();
        for (int i = 0; i < mesures.size() - 2; i++) {
            int somme = mesures.get(i) + mesures.get(i + 1) + mesures.get(i + 2);
            resultat.add(somme);
        }
        return resultat;
    }

    private static List<Integer> recupererMesures(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        return lignes.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

}
