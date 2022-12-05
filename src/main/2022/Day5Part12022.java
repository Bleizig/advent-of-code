import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5Part12022 {

    public static void main(String[] args) {
        LecteurInput lecteurInput = new LecteurInput("entree.txt");
        EtatPiles etatPiles = lecteurInput.getEtatPiles();
        List<MouvementsPiles> mouvementsPiles = lecteurInput.getMouvementsPiles();
        etatPiles.appliquerMouvements(mouvementsPiles);

        System.out.println("Réponse=" + etatPiles.recupererLettresPilesAuDessus());
    }

    private static class EtatPiles {
        List<Stack<Caisse>> pilesCaisses = new ArrayList<>();

        public void appliquerMouvements(List<MouvementsPiles> mouvementsPiles) {
            mouvementsPiles.forEach(this::appliquerMouvement);
        }

        private void appliquerMouvement(MouvementsPiles mouvements) {
            for (int i = 0; i < mouvements.nombreCaissesADeplacer; i++) {
                deplacerCaisse(mouvements.pileDepart, mouvements.pileArrivee);
            }
        }

        private void deplacerCaisse(int pileDepart, int pileArrivee) {
            //Le -1 est vraiment pourri, la premiere pile est la 1 et non la 0 ...
            Caisse caisseDeplacee = getPile(pileDepart - 1).pop();
            getPile(pileArrivee - 1).add(caisseDeplacee);
        }

        private Stack<Caisse> getPile(int pileDepart) {
            return pilesCaisses.get(pileDepart);
        }

        public String recupererLettresPilesAuDessus() {
            return pilesCaisses.stream().map(stack -> stack.peek().id).reduce("", String::concat);
        }

        public void ajouterPile(int indexPile) {
            pilesCaisses.add(new Stack<>());
        }

        public int getNombrePiles() {
            return pilesCaisses.size();
        }

        public void ajouterCaisse(int indexPile, String idCaisse) {
            pilesCaisses.get(indexPile).add(new Caisse(idCaisse));
        }


        private record Caisse(String id) {
        }
    }

    private record MouvementsPiles(int nombreCaissesADeplacer, int pileDepart, int pileArrivee) {
    }

    private static class LecteurInput {
        public static final int ESPACE_ENTRE_ID_CAISSES = 4;
        public static final String PATTERN_LIGNE_MOUVEMENT_REGEXP = "move (.*) from (.*) to (.*)";
        public static final Pattern PATTERN_LIGNE_MOUVEMENT = Pattern.compile(PATTERN_LIGNE_MOUVEMENT_REGEXP);
        public static final int POSITION_NOMBRE_CAISSES_DANS_PATTERN_MOUVEMENT = 1;
        public static final int POSITION_PILE_DEPART_DANS_PATTERN_MOUVEMENT = 2;
        public static final int POSITION_PILE_ARRIVEE_DANS_PATTERN_MOUVEMENT = 3;
        EtatPiles etatPiles;

        List<MouvementsPiles> mouvementsPiles;

        public LecteurInput(String nomFichier) {
            List<String> lignes = Util.lireFichier(nomFichier);

            mouvementsPiles = new ArrayList<>();

            Stack<String> lignesEtatPiles = new Stack<>();

            int indexLigne = 0;
            do {
                lignesEtatPiles.add(lignes.get(indexLigne));
                indexLigne++;
            } while (!lignes.get(indexLigne).isBlank());

            initialiserEtatPiles(lignesEtatPiles);

            indexLigne++; //ligne vide

            while (indexLigne < lignes.size()) {
                ajouteMouvement(lignes.get(indexLigne));
                indexLigne++;
            }
        }

        private void ajouteMouvement(String ligneMouvement) {
            //Hommage à toi Pattern !
            final Matcher m = PATTERN_LIGNE_MOUVEMENT.matcher(ligneMouvement);
            if (!m.find()) {
                throw new RuntimeException("Houston ... ");
            }

            int nombreCaissesADeplacer = Integer.parseInt(m.group(POSITION_NOMBRE_CAISSES_DANS_PATTERN_MOUVEMENT));
            int pileDepart = Integer.parseInt(m.group(POSITION_PILE_DEPART_DANS_PATTERN_MOUVEMENT));
            int pileArrivee = Integer.parseInt(m.group(POSITION_PILE_ARRIVEE_DANS_PATTERN_MOUVEMENT));

            MouvementsPiles mouvementPile = new MouvementsPiles(nombreCaissesADeplacer, pileDepart, pileArrivee);
            mouvementsPiles.add(mouvementPile);
        }

        private void initialiserEtatPiles(Stack<String> lignesEtatPiles) {
            etatPiles = new EtatPiles();

            String ligneNomsDePiles = lignesEtatPiles.pop();
            Scanner scanner = new Scanner(ligneNomsDePiles);

            while (scanner.hasNextInt()) {
                etatPiles.ajouterPile(scanner.nextInt());
            }

            while (!lignesEtatPiles.isEmpty()) {
                String ligne = lignesEtatPiles.pop();
                for (int i = 0; i < etatPiles.getNombrePiles(); i++) {
                    char idCaisse = ligne.charAt(1 + ESPACE_ENTRE_ID_CAISSES * i);
                    if (idCaisse != ' ') {
                        etatPiles.ajouterCaisse(i, String.valueOf(idCaisse));
                    }
                }
            }
        }

        public EtatPiles getEtatPiles() {
            return etatPiles;
        }

        public List<MouvementsPiles> getMouvementsPiles() {
            return mouvementsPiles;
        }
    }
}
