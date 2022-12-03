import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14Part1 {

    public static void main(String[] args) {
        TemplateEtReglesInsertion templateEtReglesInsertion = recuperePolymereEtReglesInsertion("entree.txt");
        long resultat = caculeDifferenceEntreElementLePlusCommunEtLeMoinsCommunApres10Etapes(templateEtReglesInsertion);
        System.out.println("Résultat=" + resultat);
    }

    private static long caculeDifferenceEntreElementLePlusCommunEtLeMoinsCommunApres10Etapes(TemplateEtReglesInsertion templateEtReglesInsertion) {
        String template = templateEtReglesInsertion.template;
        for (int i = 0; i < 10; i++) {
            template = polymerise(template, templateEtReglesInsertion.reglesInsertion);
        }
        long max = calculerOccurenceDuPlusFrequentElement(template);
        long min = calculerOccurrenceDuMoinsFrequentElement(template);

        return max - min;
    }

    private static String polymerise(String template, Map<String, String> reglesInsertion) {
        StringBuilder templatePolymerise = new StringBuilder();

        for (int i = 0; i < template.length() - 1; i++) {
            String elementAInserer = reglesInsertion.get(template.substring(i, i + 2));
            if (elementAInserer != null) {
                templatePolymerise.append(template.charAt(i)).append(elementAInserer);
            } else {
                templatePolymerise.append(template, i, i + 1);
            }
        }
        templatePolymerise.append(template, template.length() - 1, template.length());

        return templatePolymerise.toString();
    }

    private static long calculerOccurrenceDuMoinsFrequentElement(String template) {
        return Arrays.stream(template.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().min(Long::compareTo).orElseThrow();
    }

    private static long calculerOccurenceDuPlusFrequentElement(String template) {
        return Arrays.stream(template.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().max(Long::compareTo).orElseThrow();
    }


    private static TemplateEtReglesInsertion recuperePolymereEtReglesInsertion(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        String template = lignes.get(0);

        lignes.remove(0);
        lignes.remove(0);

        Map<String, String> reglesInsertion = lignes.stream().collect(Collectors.toMap(s -> s.substring(0, 2), s -> s.substring(6, 7)));

        return new TemplateEtReglesInsertion(template, reglesInsertion);
    }

    record TemplateEtReglesInsertion(String template, Map<String, String> reglesInsertion) {
    }
}



