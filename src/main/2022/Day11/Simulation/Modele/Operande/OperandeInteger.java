package Day11.Simulation.Modele.Operande;

public class OperandeInteger implements Operande {
    int valeur;

    public OperandeInteger(int valeur) {
        this.valeur = valeur;
    }

    @Override
    public Integer getValeur(Integer item) {
        return valeur;
    }
}

