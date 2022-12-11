import util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class Day2Part12022 {

    public static void main(String[] args) {
        List<Strategie> strategies = lireStrategies();
        int resultat = determinerScoreTotal(strategies);
        System.out.println("Réponse=" + resultat);
    }

    private static int determinerScoreTotal(List<Strategie> strategies) {
        return strategies.stream().map(Day2Part12022::determinerScore).mapToInt(Integer::intValue).sum();
    }

    private static int determinerScore(Strategie s) {
        int score = 0;
        score += determinerScoreBataille(s);
        score += determinerScoreMonCoup(s);
        return score;
    }

    private static int determinerScoreMonCoup(Strategie s) {
        return s.monCoup.scoreCoup();
    }

    private static int determinerScoreBataille(Strategie s) {
        if (s.monCoup == s.coupAdversaire) {
            return 3;
        } else if (s.monCoup.estMeilleurQue(s.coupAdversaire)) {
            return 6;
        } else if (s.coupAdversaire.estMeilleurQue(s.monCoup)) {
            return 0;
        } else {
            throw new RuntimeException("Ne devrait pas arriver");
        }
    }

    private static List<Strategie> lireStrategies() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(Day2Part12022::convertirLigneEnStrategie).collect(Collectors.toList());
    }

    private static Strategie convertirLigneEnStrategie(String ligne) {
        Symbole coupAdversaire = convertirCoupAdversaire(ligne.charAt(0));
        Symbole monCoup = convertirMonCoup(ligne.charAt(2));

        return new Strategie(coupAdversaire, monCoup);
    }


    private static Symbole convertirCoupAdversaire(char coupAdversaire) {
        return switch (coupAdversaire) {
            case 'A' -> Symbole.PIERRE;
            case 'B' -> Symbole.FEUILLE;
            case 'C' -> Symbole.CISEAUX;
            default -> throw new IllegalStateException("Coup adversaire non géré: " + coupAdversaire);
        };
    }

    private static Symbole convertirMonCoup(char monCoup) {
        return switch (monCoup) {
            case 'X' -> Symbole.PIERRE;
            case 'Y' -> Symbole.FEUILLE;
            case 'Z' -> Symbole.CISEAUX;
            default -> throw new IllegalStateException("Coup adversaire non géré: " + monCoup);
        };
    }

    record Strategie(Symbole coupAdversaire, Symbole monCoup) {
    }

    enum Symbole {
        PIERRE,
        FEUILLE,
        CISEAUX;

        public boolean estMeilleurQue(Symbole autreSymbole) {
            return this == PIERRE && autreSymbole == CISEAUX || this == FEUILLE && autreSymbole == PIERRE || this == CISEAUX && autreSymbole == FEUILLE;
        }

        public int scoreCoup() {
            return switch (this) {
                case PIERRE -> 1;
                case FEUILLE -> 2;
                case CISEAUX -> 3;
            };
        }
    }
}
