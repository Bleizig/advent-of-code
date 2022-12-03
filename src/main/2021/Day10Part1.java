import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day10Part1 {

    private static final Map<Character, Integer> SCORES = new HashMap<>();
    private static final Map<Character, Character> PAIRE_CARACTERES = new HashMap<>();

    static {
        SCORES.put(')', 3);
        SCORES.put(']', 57);
        SCORES.put('}', 1197);
        SCORES.put('>', 25137);
    }

    static {
        PAIRE_CARACTERES.put('(', ')');
        PAIRE_CARACTERES.put('[', ']');
        PAIRE_CARACTERES.put('{', '}');
        PAIRE_CARACTERES.put('<', '>');
    }

    public static void main(String[] args) {
        List<String> lignes = recupererLignes("entree.txt");
        long resultat = calculerSyntaxErrorScore(lignes);
        System.out.println("Réponse=" + resultat);
    }

    private static long calculerSyntaxErrorScore(List<String> lignes) {
        long scoreTotal = 0;
        for (String ligne : lignes) {
            scoreTotal += calculerSyntaxErrorScore(ligne);
        }
        return scoreTotal;
    }

    private static long calculerSyntaxErrorScore(String ligne) {
        Character premierCarIllegal = determinerPremierCaractereIllegal(ligne);
        if (premierCarIllegal == null) {
            return 0;
        } else {
            return calculerScore(premierCarIllegal);
        }
    }

    private static long calculerScore(Character premierCarIllegal) {
        return SCORES.get(premierCarIllegal);
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



