import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14Part2 {

    public static void main(String[] args) {
        TemplateEtReglesInsertion templateEtReglesInsertion = recuperePolymereEtReglesInsertion("entree.txt");
        long resultat = caculeDifferenceEntreElementLePlusCommunEtLeMoinsCommunApres40Etapes(templateEtReglesInsertion);
        System.out.println("Résultat=" + resultat);
    }

    private static long caculeDifferenceEntreElementLePlusCommunEtLeMoinsCommunApres40Etapes(TemplateEtReglesInsertion templateEtReglesInsertion) {
        Map<String, Long> template = templateEtReglesInsertion.template;
        for (int i = 0; i < 40; i++) {
            template = polymerise(template, templateEtReglesInsertion.reglesInsertion);
        }
        long max = calculerOccurenceDuPlusFrequentElement(template);
        long min = calculerOccurrenceDuMoinsFrequentElement(template);
        return max - min;
    }

    private static Map<String, Long> polymerise(Map<String, Long> template, Map<String, String> reglesInsertion) {
        return template.entrySet().stream().map(e -> polymeriserDuoLettres(e.getKey(), e.getValue(), reglesInsertion))
                .flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }

    private static Map<String, Long> polymeriserDuoLettres(String duoLettres, Long total, Map<String, String> reglesInsertion) {
        Map<String, Long> nouveauxDuos = new HashMap<>();

        String lettreAInserer = reglesInsertion.get(duoLettres);

        nouveauxDuos.put(duoLettres.charAt(0) + lettreAInserer, total);
        nouveauxDuos.put(lettreAInserer + duoLettres.charAt(1), total);

        return nouveauxDuos;
    }

    private static long calculerOccurrenceDuMoinsFrequentElement(Map<String, Long> template) {
        Long nbOccurences = template.entrySet().stream().map(Day14Part2::genereMapCaractereNombreOccurences).flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum)).values().stream().min(Long::compare).orElseThrow();
        return (nbOccurences + 1) / 2;
    }

    private static long calculerOccurenceDuPlusFrequentElement(Map<String, Long> template) {
        Long nbOccurences = template.entrySet().stream().map(Day14Part2::genereMapCaractereNombreOccurences).flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum)).values().stream().max(Long::compare).orElseThrow();
        return (nbOccurences + 1) / 2;
    }

    private static Map<Character, Long> genereMapCaractereNombreOccurences(Map.Entry<String, Long> entree) {
        Map<Character, Long> map = new HashMap<>();
        char caractere1 = entree.getKey().charAt(0);
        char caractere2 = entree.getKey().charAt(1);
        if (caractere1 != caractere2) {
            map.put(caractere1, entree.getValue());
            map.put(caractere2, entree.getValue());
        } else {
            map.put(caractere1, entree.getValue() * 2);
        }
        return map;
    }

    private static TemplateEtReglesInsertion recuperePolymereEtReglesInsertion(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        Map<String, Long> template = new HashMap<>();
        String templateString = lignes.get(0);
        for (int i = 0; i < templateString.length() - 1; i++) {
            String duoLettres = templateString.substring(i, i + 2);
            Long nombreOccurences = template.getOrDefault(duoLettres, 0L);
            nombreOccurences++;
            template.put(duoLettres, nombreOccurences);
        }
        lignes.remove(0);
        lignes.remove(0);

        Map<String, String> reglesInsertion = lignes.stream().collect(Collectors.toMap(s -> s.substring(0, 2), s -> s.substring(6, 7)));

        return new TemplateEtReglesInsertion(template, reglesInsertion);
    }

    record TemplateEtReglesInsertion(Map<String, Long> template, Map<String, String> reglesInsertion) {
    }
}



