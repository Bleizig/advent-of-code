package Day11.Simulation.Modele;

import java.util.LinkedList;
import java.util.Queue;

public class Singe {
    private static final int FACTEUR_DE_BAISSE_INQUIETUDE = 3;
    private final Queue<Integer> items;
    private Operation operation;
    private Test test;

    private int nombreInspectionsEffectuees;

    public Singe(Operation operation, Test test) {
        this.operation = operation;
        this.test = test;
        items = new LinkedList<>();
        nombreInspectionsEffectuees = 0;
    }

    public int nombreInspectionsEffectuees() {
        return nombreInspectionsEffectuees;
    }

    public boolean aAuMoinsUnItem() {
        return !items.isEmpty();
    }

    public Lancer effectueLancerSuivant() {
        if (items.isEmpty()) {
            throw new RuntimeException("Ce cas n'est pas prévu, ne pas oublier d'utiliser aAuMoinsUnItem() préalablement");
        }

        Integer item = items.poll();

        item = effectueOperationInspection(item);
        item = effectueBaisseInquietude(item);
        int prochainSinge = determineProchainSinge(item);

        return new Lancer(prochainSinge, item);
    }

    public void receptionnerItem(int item) {
        items.add(item);
    }

    private int determineProchainSinge(Integer item) {
        return test.appliqueTest(item);
    }

    private Integer effectueBaisseInquietude(Integer item) {
        return item / FACTEUR_DE_BAISSE_INQUIETUDE;
    }

    private Integer effectueOperationInspection(Integer item) {
        item = operation.appliqueOperation(item);
        nombreInspectionsEffectuees++;
        return item;
    }

}
