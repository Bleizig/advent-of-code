import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8Part1 {

    public static final int NB_SEGMENTS_UN = 2;
    public static final int NB_SEGMENTS_QUATRE = 4;
    public static final int NB_SEGMENTS_SEPT = 3;
    public static final int NB_SEGMENTS_HUIT = 7;

    public static void main(String[] args) {
        List<Entree> entrees = recupererEntrees("entree.txt");
        long resultat = calculerNombreDigitsEvidentsDansLaSortie(entrees);
        System.out.println("Réponse=" + resultat);
    }

    private static Long calculerNombreDigitsEvidentsDansLaSortie(List<Entree> entrees) {
        return entrees.stream().map(Day8Part1::calculerNombreDigitsEvidentsDansLaSortie).reduce(Long::sum).get();
    }

    private static long calculerNombreDigitsEvidentsDansLaSortie(Entree entree) {
        return entree.sortie.stream().filter(Day8Part1::utiliseUnNombreEvidentDeSegments).count();
    }

    private static boolean utiliseUnNombreEvidentDeSegments(String sortie) {
        return sortie.length() == NB_SEGMENTS_UN
                || sortie.length() == NB_SEGMENTS_QUATRE
                || sortie.length() == NB_SEGMENTS_SEPT
                || sortie.length() == NB_SEGMENTS_HUIT;
    }

    private static List<Entree> recupererEntrees(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        return lignes.stream().map(Day8Part1::convertirLigneEnEntree).collect(Collectors.toList());
    }

    private static Entree convertirLigneEnEntree(String ligne) {
        String[] ligneDecoupeeEnDeux = ligne.split("\\|");
        Entree entree = new Entree();
        entree.patterns = decouperAvecSeparateurEspace(ligneDecoupeeEnDeux[0]);
        entree.sortie = decouperAvecSeparateurEspace(ligneDecoupeeEnDeux[1]);

        return entree;
    }

    private static List<String> decouperAvecSeparateurEspace(String chaineEntree) {
        String[] chaineDecoupee = chaineEntree.trim().split(" ");
        return Arrays.stream(chaineDecoupee).toList();
    }

    private static class Entree {
        public List<String> patterns;
        public List<String> sortie;
    }
}



