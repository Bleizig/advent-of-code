package Day11.Simulation.Modele;

import Day11.Simulation.Modele.Operande.Operande;

import java.util.function.BiFunction;

public class Operation {

    Operande operande1;
    Operande operande2;

    BiFunction<Integer, Integer, Integer> fonctionCalcul;

    public Operation(Operande operande1, Operande operande2, BiFunction<Integer, Integer, Integer> fonctionCalcul) {
        this.operande1 = operande1;
        this.operande2 = operande2;
        this.fonctionCalcul = fonctionCalcul;
    }

    Integer appliqueOperation(Integer item) {
        return fonctionCalcul.apply(operande1.getValeur(item), operande2.getValeur(item));
    }
}
