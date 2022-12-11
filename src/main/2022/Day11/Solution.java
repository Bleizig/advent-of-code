package Day11;

import Day11.Entree.LecteurInput;
import Day11.Modele.Singes;

public class Solution {

    public static void main(String[] args) {
        LecteurInput lecteurInput = new LecteurInput();
        Singes singes = lecteurInput.recupererSinges();
        singes.jouerNRounds(10000);

        long resultat = singes.recupererNiveauDeMonkeyBusiness();
        System.out.println("Réponse=" + resultat);
    }
}
