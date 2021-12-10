import java.util.*;
import java.util.stream.Collectors;

public class Day10Part2 {

    private static final Map<Character, Integer> VALEURS_CARACTERES = new HashMap<>();
    private static final Map<Character, Character> PAIRE_CARACTERES = new HashMap<>();

    static {
        VALEURS_CARACTERES.put(')', 1);
        VALEURS_CARACTERES.put(']', 2);
        VALEURS_CARACTERES.put('}', 3);
        VALEURS_CARACTERES.put('>', 4);
    }

    static {
        PAIRE_CARACTERES.put('(', ')');
        PAIRE_CARACTERES.put('[', ']');
        PAIRE_CARACTERES.put('{', '}');
        PAIRE_CARACTERES.put('<', '>');
    }

    public static void main(String[] args) {
        List<String> lignes = recupererLignes("entree.txt");
        long resultat = calculerMedianeScoresAutocompletion(lignes);
        System.out.println("Réponse=" + resultat);
    }

    private static long calculerMedianeScoresAutocompletion(List<String> lignes) {
        List<String> lignesValides = extraireLignesValides(lignes);
        List<Long> scores = lignesValides.stream().map(Day10Part2::calculerScoreAutoCompletion).collect(Collectors.toList());
        return calculerMediane(scores);
    }

    private static long calculerMediane(List<Long> scores) {
        List<Long> scoresTries = scores.stream().sorted().collect(Collectors.toList());
        return scoresTries.get(scoresTries.size() / 2);
    }

    private static Long calculerScoreAutoCompletion(String ligne) {
        List<Character> charsManquants = determinerCharsManquantPourToutFermer(ligne);

        long score = 0;
        for (char caractere : charsManquants) {
            score *= 5;
            score += calculerValeurCaractere(caractere);
        }

        return score;
    }

    private static int calculerValeurCaractere(char caractere) {
        return VALEURS_CARACTERES.get(caractere);
    }

    private static List<Character> determinerCharsManquantPourToutFermer(String ligne) {
        char[] chars = ligne.toCharArray();
        Stack<Character> caracteresOuverts = new Stack<>();

        for (char caractere : chars) {
            if (estCaractereOuvrant(caractere)) {
                caracteresOuverts.push(caractere);
            } else if (estCaractereFermant(caractere)) {
                if (estBienUnePaireDeCaracteresOuvrantEtFermant(caracteresOuverts.peek(), caractere)) {
                    caracteresOuverts.pop();
                } else {
                    throw new RuntimeException("On m'aurait menti ?");
                }
            }
        }
        List<Character> stackConvertieEnListe = caracteresOuverts.stream().map(Day10Part2::getCaractereFermant).collect(Collectors.toList());
        Collections.reverse(stackConvertieEnListe);
        return stackConvertieEnListe;
    }

    private static char getCaractereFermant(Character caractereOuvrant) {
        return PAIRE_CARACTERES.get(caractereOuvrant);
    }


    private static List<String> extraireLignesValides(List<String> lignes) {
        return lignes.stream().filter(Day10Part2::estLigneValide).collect(Collectors.toList());
    }

    private static boolean estLigneValide(String ligne) {
        return determinerPremierCaractereIllegal(ligne) == null;
    }

    private static Character determinerPremierCaractereIllegal(String ligne) {
        char[] chars = ligne.toCharArray();
        Stack<Character> caracteresOuverts = new Stack<>();

        for (char caractere : chars) {
            if (estCaractereOuvrant(caractere)) {
                caracteresOuverts.push(caractere);
            } else if (estCaractereFermant(caractere)) {
                if (estBienUnePaireDeCaracteresOuvrantEtFermant(caracteresOuverts.peek(), caractere)) {
                    caracteresOuverts.pop();
                } else {
                    return caractere;
                }
            }
        }
        return null;
    }

    private static boolean estBienUnePaireDeCaracteresOuvrantEtFermant(Character caractereOuvrant, char caractereFermant) {
        return PAIRE_CARACTERES.get(caractereOuvrant) == caractereFermant;
    }

    private static boolean estCaractereFermant(char caractere) {
        return PAIRE_CARACTERES.containsValue(caractere);
    }

    private static boolean estCaractereOuvrant(char caractere) {
        return PAIRE_CARACTERES.containsKey(caractere);
    }


    private static List<String> recupererLignes(String nomFichier) {
        return Util.lireFichier(nomFichier);
    }
}



