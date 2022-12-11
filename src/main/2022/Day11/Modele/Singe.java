package Day11.Modele;

import java.util.LinkedList;
import java.util.Queue;

public class Singe {
    private static final int FACTEUR_DE_BAISSE_INQUIETUDE = 3;
    private final Queue<Long> items;
    private Operation operation;
    private final Test test;

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

        Long item = items.poll();

        item = effectueOperationInspection(item);
        //item = effectueBaisseInquietude(item);
        int prochainSinge = determineProchainSinge(item);

        return new Lancer(prochainSinge, item);
    }

    public void receptionnerItem(long item) {
        items.add(item);
    }

    private int determineProchainSinge(long item) {
        return test.appliqueTest(item);
    }

    private Long effectueBaisseInquietude(long item) {
        return item / FACTEUR_DE_BAISSE_INQUIETUDE;
    }

    private Long effectueOperationInspection(long item) {
        item = operation.appliqueOperation(item);
        nombreInspectionsEffectuees++;
        return item;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getDiviseurDeTest() {
        return test.getDiviseur();
    }
}
