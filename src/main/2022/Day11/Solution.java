package Day11;

import Day11.Entree.LecteurInput;
import Day11.Simulation.Modele.Singe;
import Day11.Simulation.Simulation;

import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<Singe> singes = LecteurInput.recupererSinges();

        Simulation simulation = new Simulation(singes);
        simulation.jouerNRounds(20);

        int resultat = simulation.recupererNiveauDeMonkeyBusiness();
        System.out.println("Réponse=" + resultat);
    }
}
