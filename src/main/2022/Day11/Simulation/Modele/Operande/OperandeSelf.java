package Day11.Simulation.Modele.Operande;

public class OperandeSelf implements Operande {

    @Override
    public Integer getValeur(Integer item) {
        return item;
    }
}