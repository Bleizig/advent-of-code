package Day11;

import Day11.Entree.LecteurInput;
import Day11.Simulation.Modele.Singe;
import Day11.Simulation.Simulation;

import java.util.List;

public class Solution {

    public static void main(String[] args) {
        LecteurInput lecteurInput = new LecteurInput();
        List<Singe> singes = lecteurInput.recupererSinges();

        Simulation simulation = new Simulation(singes);
        simulation.jouerNRounds(10000);

        long resultat = simulation.recupererNiveauDeMonkeyBusiness();
        System.out.println("Réponse=" + resultat);
    }
}
