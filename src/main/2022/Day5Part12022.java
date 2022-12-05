import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
            Caisse caisseDeplacee = getPile(pileDepart-1).pop();
            getPile(pileArrivee-1).add(caisseDeplacee);
        }

        private Stack<Caisse> getPile(int pileDepart) {
            return pilesCaisses.get(pileDepart);
        }

        public String recupererLettresPilesAuDessus() {
            return pilesCaisses.stream().map(stack -> stack.peek().id).reduce("", String::concat);
        }

        public void ajouterPile() {
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
        EtatPiles etatPiles;

        List<MouvementsPiles> mouvementsPiles;

        public LecteurInput(String nomFichier) {
            List<String> lignes = Util.lireFichier(nomFichier);
            etatPiles = new EtatPiles();
            mouvementsPiles = new ArrayList<>();

            Stack<String> lignesEtatPiles = new Stack<>();

            int indexLigne = 0;
            do {
                lignesEtatPiles.add(lignes.get(indexLigne));
                indexLigne++;
            } while (lignes.get(indexLigne).trim().startsWith("["));

            Scanner scanner = new Scanner(lignes.get(indexLigne));
            while (scanner.hasNextInt()) {
                scanner.nextInt(); //TODO: on se fout de l'identifiant de la pile.
                etatPiles.ajouterPile();
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
            indexLigne++; //ligne des identifiants de piles
            indexLigne++; //ligne vide

            while (indexLigne < lignes.size()) {
                String ligneMouvement = lignes.get(indexLigne);
                Scanner scannerLigneMouvement = new Scanner(ligneMouvement);
                scannerLigneMouvement.skip("[a-zA-Z\s]*");
                int nombreCaissesADeplacer = scannerLigneMouvement.nextInt();
                scannerLigneMouvement.skip("[a-zA-Z\s]*");
                int pileDepart = scannerLigneMouvement.nextInt();
                scannerLigneMouvement.skip("[a-zA-Z\s]*");
                int pileArrivee = scannerLigneMouvement.nextInt();
                MouvementsPiles mouvementPile = new MouvementsPiles(nombreCaissesADeplacer, pileDepart, pileArrivee);
                mouvementsPiles.add(mouvementPile);
                indexLigne++;
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
