import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Day7Part2 {

    public static void main(String[] args) {
        List<Integer> crabes = recupererCrabes("entree.txt");
        long resultat = determinerPlusPetiteQuantiteDeFuelADepenser(crabes);
        System.out.println("Réponse=" + resultat);
    }

    private static long determinerPlusPetiteQuantiteDeFuelADepenser(List<Integer> crabes) {
        float moyenne = calculeMoyenne(crabes);
        int entierAvant =  (int) Math.floor(moyenne);
        int entierApres =  (int) Math.ceil(moyenne);
        long fuelEntierAvant = calculeSommeEcartsEntreChaqueCrabeEtPosition(crabes, entierAvant);
        long fuelEntierApres = calculeSommeEcartsEntreChaqueCrabeEtPosition(crabes, entierApres);
        return Math.min(fuelEntierAvant, fuelEntierApres);
    }

    private static long calculeSommeEcartsEntreChaqueCrabeEtPosition(List<Integer> crabesTries, int meilleurePosition) {
        return crabesTries.stream().map(crabe -> calculerSommeDeTousLesEntiersJusquA(Math.abs(crabe - meilleurePosition))).reduce(Integer::sum).get();
    }

    private static int calculerSommeDeTousLesEntiersJusquA(int n) {
        return (n) * (n + 1) / 2;
    }

    private static float calculeMoyenne(List<Integer> listeTriee) {
        return (float) listeTriee.stream().reduce(Integer::sum).get() / listeTriee.size();
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



