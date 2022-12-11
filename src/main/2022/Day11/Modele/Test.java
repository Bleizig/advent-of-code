package Day11.Modele;

public class Test {
    private final int singeTestFaux;
    private final int singeTestVrai;
    private final int diviseur;

    public Test(int singeTestFaux, int singeTestVrai, int diviseur) {
        this.singeTestFaux = singeTestFaux;
        this.singeTestVrai = singeTestVrai;
        this.diviseur = diviseur;
    }

    public int appliqueTest(Long item) {
        return item % diviseur == 0 ? singeTestVrai : singeTestFaux;
    }

    public int getDiviseur() {
        return diviseur;
    }
}
