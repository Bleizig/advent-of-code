package Day11.Modele;

import java.util.Comparator;
import java.util.List;

public class Singes {

    List<Singe> singes;

    public Singes(List<Singe> singes) {
        this.singes = singes;
        adapteOperationsPourEviterGrandsNombres();
    }

    private void adapteOperationsPourEviterGrandsNombres() {
        int produitDeTousLesDiviseurs = singes.stream().map(Singe::getDiviseurDeTest).reduce((a, b) -> a * b).orElseThrow();

        singes.forEach(singe -> {
            Operation vieilleOperation = singe.getOperation();
            singe.setOperation(item -> vieilleOperation.appliqueOperation(item) % produitDeTousLesDiviseurs);
        });
    }

    public long recupererNiveauDeMonkeyBusiness() {
        return singes.stream().map(s -> (long) s.nombreInspectionsEffectuees()).sorted(Comparator.reverseOrder()).limit(2).reduce((a, b) -> a * b).orElseThrow();
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
