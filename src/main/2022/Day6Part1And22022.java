import java.util.List;
import java.util.stream.Collectors;

public class Day6Part1And22022 {

    public static void main(String[] args) {
        DecodeurDeCompete decodeur = new DecodeurDeCompete("entree.txt");

        int resultat = decodeur.determinerIndexDebutMarqueurProtocole(14);
        System.out.println("Réponse=" + resultat);
    }

    private static class DecodeurDeCompete {
        String code;

        public DecodeurDeCompete(String nomFichierADecoder) {
            List<String> lignes = Util.lireFichier(nomFichierADecoder);
            code = lignes.get(0);
        }

        public int determinerIndexDebutMarqueurProtocole(int longueurMarqueur) {
            int indexLu = 0;
            while (!estUnMarqueur(code.substring(indexLu, indexLu + longueurMarqueur), longueurMarqueur) && indexLu < code.length() - longueurMarqueur) {
                indexLu++;
            }
            return indexLu + longueurMarqueur;
        }

        private boolean estUnMarqueur(String chaine, int longueurMarqueur) {
            return chaine.chars().boxed().collect(Collectors.toSet()).size() == longueurMarqueur;
        }
    }
}
