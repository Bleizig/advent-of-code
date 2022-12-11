import util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class Day2Part22022 {

    public static void main(String[] args) {
        List<Strategie> strategies = lireStrategies();
        int resultat = determinerScoreTotal(strategies);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerScoreTotal(List<Strategie> strategies) {
        return strategies.stream().map(Day2Part22022::determinerScore).mapToInt(Integer::intValue).sum();
    }

    private static int determinerScore(Strategie s) {
        int score = 0;
        score += determinerScoreBataille(s);
        score += determinerScoreMonCoup(s);
        return score;
    }

    private static int determinerScoreMonCoup(Strategie s) {
        Symbole monCoup = determineMonCoup(s);
        return monCoup.scoreCoup();
    }

    private static Symbole determineMonCoup(Strategie s) {
        return switch (s.resultat) {
            case NULL -> s.coupAdversaire;
            case DEFAITE -> s.coupAdversaire.getBat();
            case VICTOIRE -> s.coupAdversaire.getEstBattuPar();
        };
    }

    private static int determinerScoreBataille(Strategie s) {
        return switch (s.resultat) {
            case VICTOIRE -> 6;
            case NULL -> 3;
            case DEFAITE -> 0;
        };
    }

    private static List<Strategie> lireStrategies() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(Day2Part22022::convertirLigneEnStrategie).collect(Collectors.toList());
    }

    private static Strategie convertirLigneEnStrategie(String ligne) {
        Symbole coupAdversaire = convertirCoupAdversaire(ligne.charAt(0));
        Resultat resultatAttendu = convertirResultatAttendu(ligne.charAt(2));

        return new Strategie(coupAdversaire, resultatAttendu);
    }


    private static Symbole convertirCoupAdversaire(char coupAdversaire) {
        return switch (coupAdversaire) {
            case 'A' -> Symbole.PIERRE;
            case 'B' -> Symbole.FEUILLE;
            case 'C' -> Symbole.CISEAUX;
            default -> throw new IllegalStateException("Coup adversaire non géré: " + coupAdversaire);
        };
    }

    private static Resultat convertirResultatAttendu(char resultatAttendu) {
        return switch (resultatAttendu) {
            case 'X' -> Resultat.DEFAITE;
            case 'Y' -> Resultat.NULL;
            case 'Z' -> Resultat.VICTOIRE;
            default -> throw new IllegalStateException("Type de résultat de bataille non géré: " + resultatAttendu);
        };
    }

    record Strategie(Symbole coupAdversaire, Resultat resultat) {
    }

    enum Symbole {
        PIERRE,
        FEUILLE,
        CISEAUX;

        public int scoreCoup() {
            return switch (this) {
                case PIERRE -> 1;
                case FEUILLE -> 2;
                case CISEAUX -> 3;
            };
        }

        public Symbole getBat() {
            return switch (this) {
                case PIERRE -> CISEAUX;
                case FEUILLE -> PIERRE;
                case CISEAUX -> FEUILLE;
            };
        }

        public Symbole getEstBattuPar() {
            return switch (this) {
                case PIERRE -> FEUILLE;
                case FEUILLE -> CISEAUX;
                case CISEAUX -> PIERRE;
            };
        }
    }

    enum Resultat {
        VICTOIRE,
        DEFAITE,
        NULL
    }
}
