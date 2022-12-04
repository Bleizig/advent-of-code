import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class Day4Part22022 {

    public static void main(String[] args) {
        ListeDePaireDeSections pairesSections = recupererPairesSectionsNettoyage();
        long resultat = pairesSections.determinerNombrePairesSectionsQuiSeChevauchent();

        System.out.println("Réponse=" + resultat);
    }

    private static ListeDePaireDeSections recupererPairesSectionsNettoyage() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(Day4Part22022::convertirLigneEnPaireDeSections).collect(ListeDePaireDeSections.collector());
    }

    private static PaireDeSections convertirLigneEnPaireDeSections(String ligne) {
        ScannerLigneDePaireDeSections scanner = new ScannerLigneDePaireDeSections(ligne);
        return scanner.getPaireDeSections();
    }

    private static class ListeDePaireDeSections {
        List<PaireDeSections> listePaires;

        public ListeDePaireDeSections(List<PaireDeSections> listePaires) {
            this.listePaires = listePaires;
        }

        public long determinerNombrePairesSectionsQuiSeChevauchent() {
            return listePaires.stream().filter(PaireDeSections::lesPairesSeChevauchent).count();
        }

        public static Collector<PaireDeSections, Object, ListeDePaireDeSections> collector() {
            return collectingAndThen(Collectors.toList(), ListeDePaireDeSections::new);
        }
    }

    private static class PaireDeSections {
        Section s1;
        Section s2;

        public PaireDeSections(Section s1, Section s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        public boolean lesPairesSeChevauchent() {
            return s1.chevauche(s2);

        }
    }

    private static class Section {
        int idMin;
        int idMax;

        public Section(int idMin, int idMax) {
            this.idMin = idMin;
            this.idMax = idMax;
        }

        public boolean chevauche(Section autreSection) {
            return this.idMin <= autreSection.idMin && this.idMax >= autreSection.idMin
                    || this.idMax >= autreSection.idMax && this.idMin <= autreSection.idMax
                    || inclus(autreSection) || autreSection.inclus(this);
        }

        public boolean inclus(Section autreSection) {
            return this.idMin <= autreSection.idMin && this.idMax >= autreSection.idMax;
        }
    }

    private static class ScannerLigneDePaireDeSections {
        public static final String _VIRGULE_OU_TIRET = "[,\\-]";

        Scanner scanner;

        public ScannerLigneDePaireDeSections(String ligne) {
            scanner = new Scanner(ligne).useDelimiter(_VIRGULE_OU_TIRET);
        }

        public PaireDeSections getPaireDeSections() {
            return new PaireDeSections(lireSection(), lireSection());

        }

        private Section lireSection() {
            return new Section(scanner.nextInt(), scanner.nextInt());
        }
    }
}
