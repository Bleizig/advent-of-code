import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8Part2 {

    private static final Map<String, String> TABLE_CONVERSION_SEGMENTS_DIGIT = new HashMap<>();

    static {
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("abcefg", "0");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("cf", "1");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("acdeg", "2");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("acdfg", "3");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("bcdf", "4");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("abdfg", "5");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("abdefg", "6");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("acf", "7");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("abcdefg", "8");
        TABLE_CONVERSION_SEGMENTS_DIGIT.put("abcdfg", "9");
    }

    public static void main(String[] args) {
        List<Entree> entrees = recupererEntrees("entree.txt");
        int resultat = calculerSommeDeToutesLesSorties(entrees);
        System.out.println("Réponse=" + resultat);
    }

    private static int calculerSommeDeToutesLesSorties(List<Entree> entrees) {
        return entrees.stream().map(Day8Part2::determinerSortie).reduce(Integer::sum).orElseThrow();
    }

    private static int determinerSortie(Entree entree) {
        Map<Character, Character> tableCorrespondance = determinerTableCorrespondance(entree.patterns);
        return decoderLaSortie(entree, tableCorrespondance);
    }

    private static Map<Character, Character> determinerTableCorrespondance(List<String> digitsCodes) {
        Map<Character, Character> tableCorrespondance = new HashMap<>();

        Map<Character, Integer> nbApparitionsDeChaqueLettre = calculerNombreApparitionsDeChaqueLettre(digitsCodes);

        char bCode = identifierLaLettreQuiApparaitXFois(nbApparitionsDeChaqueLettre, 6);
        tableCorrespondance.put(bCode, 'b');

        char eCode = identifierLaLettreQuiApparaitXFois(nbApparitionsDeChaqueLettre, 4);
        tableCorrespondance.put(eCode, 'e');

        char fCode = identifierLaLettreQuiApparaitXFois(nbApparitionsDeChaqueLettre, 9);
        tableCorrespondance.put(fCode, 'f');

        char aCode = identifierLeA(digitsCodes);
        tableCorrespondance.put(aCode, 'a');

        char cCode = identifierLeC(nbApparitionsDeChaqueLettre, aCode);
        tableCorrespondance.put(cCode, 'c');

        char dCode = identifierLeD(digitsCodes, nbApparitionsDeChaqueLettre);
        tableCorrespondance.put(dCode, 'd');

        char gCode = identifierLeG(nbApparitionsDeChaqueLettre, dCode);
        tableCorrespondance.put(gCode, 'g');

        return tableCorrespondance;
    }

    private static char identifierLeG(Map<Character, Integer> nbApparitionsDeChaqueLettre, char dCode) {
        List<Character> DEtG = identifierLesLettresQuiApparaissentXFois(nbApparitionsDeChaqueLettre, 7);
        return DEtG.stream().filter(character -> character != dCode).findFirst().orElseThrow();
    }


    private static char identifierLeD(List<String> digitsCodes, Map<Character, Integer> nbApparitionsDeChaqueLettre) {
        String quatreCode = recupererLeQuatreCode(digitsCodes);
        List<Character> characters = identifierLesLettresQuiApparaissentXFois(nbApparitionsDeChaqueLettre, 7);
        return characters.stream().filter(character -> quatreCode.indexOf(character) != -1).findFirst().orElseThrow();
    }

    private static String recupererLeQuatreCode(List<String> digitsCodes) {
        return digitsCodes.stream().filter(digit -> digit.length() == 4).findFirst().orElseThrow();
    }

    private static char identifierLeC(Map<Character, Integer> nbApparitionsDeChaqueLettre, char cCode) {
        List<Character> AEtC = identifierLesLettresQuiApparaissentXFois(nbApparitionsDeChaqueLettre, 8);
        return AEtC.stream().filter(character -> character != cCode).findFirst().orElseThrow();
    }

    private static char identifierLeA(List<String> digitsCodes) {
        List<String> unEtSept = recupererLeUnEtSeptCodes(digitsCodes);
        Map<Character, Integer> nbApparitionsLettres = calculerNombreApparitionsDeChaqueLettre(unEtSept);
        return identifierLaLettreQuiApparaitXFois(nbApparitionsLettres, 1);
    }

    private static List<String> recupererLeUnEtSeptCodes(List<String> digitsCodes) {
        return digitsCodes.stream().filter(digit -> digit.length() == 2 || digit.length() == 3).collect(Collectors.toList());
    }

    private static Map<Character, Integer> calculerNombreApparitionsDeChaqueLettre(List<String> digits) {
        Map<Character, Integer> nbApparitions = new HashMap<>();
        for (String digit : digits) {
            for (char caractere : digit.toCharArray()) {
                Integer nbFois = nbApparitions.get(caractere);
                if (nbFois == null) {
                    nbFois = 0;
                }
                nbFois++;
                nbApparitions.put(caractere, nbFois);
            }
        }
        return nbApparitions;
    }

    private static char identifierLaLettreQuiApparaitXFois(Map<Character, Integer> nbApparitionsDeChaqueLettre, int nbFois) {
        List<Character> lettres = identifierLesLettresQuiApparaissentXFois(nbApparitionsDeChaqueLettre, nbFois);
        return lettres.get(0);
    }

    private static List<Character> identifierLesLettresQuiApparaissentXFois(Map<Character, Integer> nbApparitionsDeChaqueLettre, int nbFois) {
        return nbApparitionsDeChaqueLettre.keySet().stream().filter(lettre -> nbApparitionsDeChaqueLettre.get(lettre) == nbFois).collect(Collectors.toList());
    }

    private static int decoderLaSortie(Entree entree, Map<Character, Character> tableCorrespondance) {
        StringBuilder sortieDecodeeString = new StringBuilder();
        for (String digitSortie : entree.sortie) {
            String digitDecode = decoderDigit(digitSortie, tableCorrespondance);
            sortieDecodeeString.append(digitDecode);
        }
        return Integer.parseInt(sortieDecodeeString.toString());
    }

    private static String decoderDigit(String digitSortie, Map<Character, Character> tableCorrespondance) {
        String segmentsDecodes = decoderTousLesSegments(digitSortie, tableCorrespondance);
        return convertirEnDigit(segmentsDecodes);
    }

    private static String convertirEnDigit(String segments) {
        String segmentsTries = trierSegments(segments);
        return TABLE_CONVERSION_SEGMENTS_DIGIT.get(segmentsTries);
    }

    private static String trierSegments(String segments) {
        char[] chars = segments.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static String decoderTousLesSegments(String digitSortie, Map<Character, Character> tableCorrespondance) {
        StringBuilder segmentsDecodes = new StringBuilder();
        for (char segmentCode : digitSortie.toCharArray()) {
            Character segmentDecode = tableCorrespondance.get(segmentCode);
            segmentsDecodes.append(segmentDecode);
        }
        return segmentsDecodes.toString();
    }

    private static List<Entree> recupererEntrees(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        return lignes.stream().map(Day8Part2::convertirLigneEnEntree).collect(Collectors.toList());
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



