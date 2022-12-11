package Day11.Simulation;

import Day11.Simulation.Modele.Lancer;
import Day11.Simulation.Modele.Singe;

import java.util.Comparator;
import java.util.List;

public class Simulation {

    List<Singe> singes;

    //TODO passage par référence ...
    public Simulation(List<Singe> singes) {
        this.singes = singes;

    }

    public int recupererNiveauDeMonkeyBusiness() {
        return singes.stream().map(Singe::nombreInspectionsEffectuees).sorted(Comparator.reverseOrder()).limit(2).reduce((a, b) -> a * b).orElseThrow();
    }

    public void jouerNRounds(int nbRounds) {
        for (int i = 0; i < nbRounds; i++) {
            joueUnRound();
        }
    }

    private void joueUnRound() {
        singes.forEach(this::joueUnRoundPourUnSinge);
    }

    private void joueUnRoundPourUnSinge(Singe singe) {
        while (singe.aAuMoinsUnItem()) {
            Lancer lancer = singe.effectueLancerSuivant();
            singes.get(lancer.singeDestinataire()).receptionnerItem(lancer.item());
        }
    }
}
