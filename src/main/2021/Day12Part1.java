import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12Part1 {

    public static final String ETAPE_DEPART = "start";
    public static final String ETAPE_FIN = "end";

    public static void main(String[] args) {
        Map<String, List<String>> mapSegments = recupererMapSegmentsDuGraphe("entree.txt");
        long resultat = determinerNombreCheminsAvecAuPlusUnePetiteGrotte(mapSegments);
        System.out.println("Réponse=" + resultat);
    }

    private static Map<String, List<String>> recupererMapSegmentsDuGraphe(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        Map<String, List<String>> mapSegments = new HashMap<>();
        for (String ligne : lignes) {
            String[] etapes = ligne.split("-");
            ajouteSegment(mapSegments, etapes[0], etapes[1]);
            ajouteSegment(mapSegments, etapes[1], etapes[0]);
        }
        return mapSegments;
    }

    private static void ajouteSegment(Map<String, List<String>> mapSegments, String etapeDepart, String etapeArrivee) {
        List<String> etapesDestination = mapSegments.get(etapeDepart);
        if (etapesDestination == null) {
            etapesDestination = new ArrayList<>();
        }
        etapesDestination.add(etapeArrivee);
        mapSegments.put(etapeDepart, etapesDestination);
    }

    private static long determinerNombreCheminsAvecAuPlusUnePetiteGrotte(Map<String, List<String>> mapSegments) {
        List<Chemin> cheminsPossibles = new ArrayList<>();


        List<Chemin> cheminsAParcourir = new ArrayList<>();
        Chemin chemin = new Chemin();
        chemin.ajouterEtape(ETAPE_DEPART);
        cheminsAParcourir.add(chemin);

        while (cheminsAParcourir.size() > 0) {
            cheminsPossibles.addAll(cheminsAParcourir);
            cheminsAParcourir = rajouteUneEtapeATousLesCheminsQuiLePeuvent(cheminsAParcourir, mapSegments);
        }

        List<Chemin> cheminsViables = conserverUniquementCheminsDuDebutALafin(cheminsPossibles);
        return cheminsViables.size();
    }

    private static List<Chemin> conserverUniquementCheminsDuDebutALafin(List<Chemin> nouveauxCheminsPossibles) {
        return nouveauxCheminsPossibles.stream().filter(Chemin::estDuDebutALaFin).collect(Collectors.toList());
    }

    private static List<Chemin> rajouteUneEtapeATousLesCheminsQuiLePeuvent(List<Chemin> chemins, Map<String, List<String>> mapSegments) {
        List<Chemin> nouveauxChemins = new ArrayList<>();
        for (Chemin chemin : chemins) {
            List<String> etapeSuivantesPossibles = determinerEtapeSuivantesPossibles(chemin, mapSegments);
            for (String etapeSuivante : etapeSuivantesPossibles) {
                Chemin nouveauChemin = new Chemin(chemin);
                nouveauChemin.ajouterEtape(etapeSuivante);
                nouveauxChemins.add(nouveauChemin);
            }
        }
        return nouveauxChemins;
    }

    private static List<String> determinerEtapeSuivantesPossibles(Chemin chemin, Map<String, List<String>> mapSegments) {
        String derniereEtape = chemin.getDerniereEtape();
        List<String> etapesSuivantesPossibles = mapSegments.get(derniereEtape);
        return etapesSuivantesPossibles.stream().filter(etape -> estEtapeVisitablePlusieursFois(etape) || chemin.estEtapeNonVisitee(etape)).collect(Collectors.toList());

    }

    private static boolean estEtapeVisitablePlusieursFois(String etape) {
        return etape.toUpperCase().equals(etape);
    }

    private static class Chemin {

        private List<String> etapes;

        public Chemin() {
            this.etapes = new ArrayList<>();
        }

        public Chemin(Chemin chemin) {
            this.etapes = new ArrayList<>(chemin.etapes);
        }

        public void ajouterEtape(String etape) {
            etapes.add(etape);
        }

        public boolean estDuDebutALaFin() {
            return etapes.get(0).equals(ETAPE_DEPART) && etapes.get(etapes.size() - 1).equals((ETAPE_FIN));
        }

        public String getDerniereEtape() {
            return etapes.get(etapes.size() - 1);
        }

        public boolean estEtapeNonVisitee(String etape) {
            return !etapes.contains(etape);
        }

        @Override
        public String toString() {
            return "Chemin {" +
                    "etapes=" + etapes +
                    '}';
        }
    }
}



