package Day11.Modele.Operande;

public class OperandeInteger implements Operande {
    int valeur;

    public OperandeInteger(int valeur) {
        this.valeur = valeur;
    }

    @Override
    public Long getValeur(Long item) {
        return (long) valeur;
    }
}

