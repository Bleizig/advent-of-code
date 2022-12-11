package Day11.Modele.Operande;

public class OperandeSelf implements Operande {

    @Override
    public Long getValeur(Long item) {
        return item;
    }
}