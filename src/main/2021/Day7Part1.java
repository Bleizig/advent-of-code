import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7Part1 {

    public static void main(String[] args) {
        List<Integer> crabes = recupererCrabes("entree.txt");
        long resultat = determinerPlusPetiteQuantiteDeFuelADepenser(crabes);
        System.out.println("Réponse=" + resultat);
    }

    private static long determinerPlusPetiteQuantiteDeFuelADepenser(List<Integer> crabes) {
        List<Integer> crabesTries = trieCrabes(crabes);
        int meilleurePosition = calculeMediane(crabesTries);
        return calculeSommeEcartsEntreChaqueCrabeEtPosition(crabesTries, meilleurePosition);
    }

    private static long calculeSommeEcartsEntreChaqueCrabeEtPosition(List<Integer> crabesTries, int meilleurePosition) {
        return crabesTries.stream().map(crabe -> Math.abs(crabe - meilleurePosition)).reduce(Integer::sum).get();
    }

    private static int calculeMediane(List<Integer> listeTriee) {
        return listeTriee.get(listeTriee.size() / 2);
    }

    private static List<Integer> trieCrabes(List<Integer> listeATrier) {
        return listeATrier.stream().sorted().collect(Collectors.toList());
    }

    private static List<Integer> recupererCrabes(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        String crabesChaine = lignes.get(0);
        String[] tableauCrabesChaine = crabesChaine.split(",");

        List<Integer> crabes = new ArrayList<>();
        for (String crabe : tableauCrabesChaine) {
            crabes.add(Integer.parseInt(crabe));
        }

        return crabes;
    }
}



