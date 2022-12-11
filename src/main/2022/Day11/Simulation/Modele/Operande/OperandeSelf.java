package Day11.Simulation.Modele.Operande;

public class OperandeSelf implements Operande {

    @Override
    public Long getValeur(Long item) {
        return item;
    }
}