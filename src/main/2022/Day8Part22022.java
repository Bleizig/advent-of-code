import java.util.List;
import java.util.stream.Collectors;

public class Day8Part22022 {

    public static void main(String[] args) {
        List<List<Integer>> hauteursArbres = recupererHauteursArbres();
        long resultat = determinerMeilleurScoreDeVue(hauteursArbres);
        System.out.println("Réponse=" + resultat);
    }

    private static long determinerMeilleurScoreDeVue(List<List<Integer>> hauteursArbres) {
        long meilleurScoreDeVue = 0;
        for (int i = 0; i < hauteursArbres.size(); i++) {
            for (int j = 0; j < hauteursArbres.get(i).size(); j++) {
                long scoreDeVue = calculerScoreDeVue(hauteursArbres, i, j);
                meilleurScoreDeVue = Math.max(meilleurScoreDeVue, scoreDeVue);
            }
        }
        return meilleurScoreDeVue;
    }

    private static long calculerScoreDeVue(List<List<Integer>> hauteursArbres, int xArbre, int yArbre) {
        int hauteurArbre = hauteursArbres.get(xArbre).get(yArbre);

        int scoreDeVue = 1;
        scoreDeVue *= calculerScoreVueVersEst(hauteursArbres, xArbre, yArbre, hauteurArbre);
        scoreDeVue *= calculerScoreVueVersOuest(hauteursArbres, xArbre, yArbre, hauteurArbre);
        scoreDeVue *= calculerScoreVueVersSud(hauteursArbres, xArbre, yArbre, hauteurArbre);
        scoreDeVue *= calculerScoreVueVersNord(hauteursArbres, xArbre, yArbre, hauteurArbre);

        return scoreDeVue;
    }

    //Sud
    private static int calculerScoreVueVersSud(List<List<Integer>> hauteursArbres, int xArbre, int yArbre, int hauteurArbre) {
        int scoreDeVueVersSud = 0;
        for (int i = xArbre + 1; i < hauteursArbres.size(); i++) {
            scoreDeVueVersSud++;
            Integer hauteurArbreVu = hauteursArbres.get(i).get(yArbre);
            if (hauteurArbreVu >= hauteurArbre) {
                break;
            }
        }
        return scoreDeVueVersSud;
    }

    private static int calculerScoreVueVersNord(List<List<Integer>> hauteursArbres, int xArbre, int yArbre, int hauteurArbre) {
        int scoreDeVueVersNord = 0;
        for (int i = xArbre - 1; i >= 0; i--) {
            scoreDeVueVersNord++;
            Integer hauteurArbreVu = hauteursArbres.get(i).get(yArbre);
            if (hauteurArbreVu >= hauteurArbre) {
                break;
            }
        }
        return scoreDeVueVersNord;
    }

    private static int calculerScoreVueVersEst(List<List<Integer>> hauteursArbres, int xArbre, int yArbre, int hauteurArbre) {
        int scoreDeVueVersEst = 0;
        for (int i = yArbre + 1; i < hauteursArbres.get(xArbre).size(); i++) {
            scoreDeVueVersEst++;
            Integer hauteurArbreVu = hauteursArbres.get(xArbre).get(i);
            if (hauteurArbreVu >= hauteurArbre) {
                break;
            }
        }
        return scoreDeVueVersEst;
    }

    private static int calculerScoreVueVersOuest(List<List<Integer>> hauteursArbres, int xArbre, int yArbre, int hauteurArbre) {
        int scoreDeVueVersOuest = 0;
        for (int i = yArbre - 1; i >= 0; i--) {
            scoreDeVueVersOuest++;
            Integer hauteurArbreVu = hauteursArbres.get(xArbre).get(i);
            if (hauteurArbreVu >= hauteurArbre) {
                break;
            }
        }
        return scoreDeVueVersOuest;
    }

    private static List<List<Integer>> recupererHauteursArbres() {
        List<String> lignes = Util.lireFichier("entree.txt");
        return lignes.stream().map(l -> l.chars().boxed().map(Character::getNumericValue).collect(Collectors.toList())).collect(Collectors.toList());
    }

}
