package Day11.Entree;

import Day11.Simulation.Modele.Operande.Operande;
import Day11.Simulation.Modele.Operande.OperandeInteger;
import Day11.Simulation.Modele.Operande.OperandeSelf;
import Day11.Simulation.Modele.Operation;
import Day11.Simulation.Modele.Singe;
import Day11.Simulation.Modele.Test;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class LecteurInput {
    public static final int TAILLE_BLOC_SINGE = 6;
    public static final int INDEX_LIGNE_ITEMS = 1;
    public static final int INDEX_LIGNE_OPERATION = 2;
    public static final int INDEX_TEST_DIVISEUR = 3;
    public static final int INDEX_TEST_SINGE_VRAI = 4;
    public static final int INDEX_TEST_SINGE_FAUX = 5;

    List<Singe> singes;

    int produitDeTousLesDiviseurs;

    public LecteurInput() {
        produitDeTousLesDiviseurs = 1;
        lireSinges();

        adapteToutesLesFonctionsDeCalculPourResterDansDesNombresRaisonnables();
    }

    private void adapteToutesLesFonctionsDeCalculPourResterDansDesNombresRaisonnables() {
        singes.forEach(this::adapteFonctionCalculSinge);
    }

    private void adapteFonctionCalculSinge(Singe singe) {
        Operation operation = singe.getOperation();
        BiFunction<Long, Long, Long> fonctionCalcul = operation.getFonctionCalcul();
        operation.setFonctionCalcul((a, b) -> fonctionCalcul.apply(a, b) % produitDeTousLesDiviseurs);
    }

    private void lireSinges() {
        List<String> lignes = Util.lireFichier("entree.txt");

        lignes = lignes.stream().filter(l -> !l.isBlank()).collect(Collectors.toList());

        singes = new ArrayList<>();

        for (int i = 0; i < lignes.size(); i += TAILLE_BLOC_SINGE) {
            singes.add(recupererSinge(lignes.subList(i, i + TAILLE_BLOC_SINGE)));
        }
    }

    private Singe recupererSinge(List<String> lignesSinge) {
        List<Integer> items = recupererItems(lignesSinge);
        Operation operation = recupererOperation(lignesSinge);
        Test test = recupererTest(lignesSinge);

        Singe singe = new Singe(operation, test);
        items.forEach(singe::receptionnerItem);

        return singe;
    }

    private Test recupererTest(List<String> lignesSinge) {
        String ligneDiviseur = lignesSinge.get(INDEX_TEST_DIVISEUR);
        int diviseur = recupereEntierSitueEnFinDeChaineApresTexte(ligneDiviseur, "by");

        String ligneSingeVrai = lignesSinge.get(INDEX_TEST_SINGE_VRAI);
        int singeVrai = recupereSingeDeLigneSinge(ligneSingeVrai);

        String ligneSingeFaux = lignesSinge.get(INDEX_TEST_SINGE_FAUX);
        int singeFaux = recupereSingeDeLigneSinge(ligneSingeFaux);

        produitDeTousLesDiviseurs *= diviseur;

        return new Test(singeFaux, singeVrai, diviseur);
    }

    private int recupereSingeDeLigneSinge(String ligneSingeVrai) {
        return recupereEntierSitueEnFinDeChaineApresTexte(ligneSingeVrai, "monkey");
    }

    private int recupereEntierSitueEnFinDeChaineApresTexte(String chaine, String texteAvantEntier) {
        return Integer.parseInt(chaine.substring(chaine.indexOf(texteAvantEntier) + texteAvantEntier.length()).trim());
    }

    private Operation recupererOperation(List<String> lignesSinge) {
        String ligneOperation = lignesSinge.get(INDEX_LIGNE_OPERATION);
        String operationString = ligneOperation.substring(ligneOperation.indexOf('=') + 1).trim();


        Operation operation;
        if (operationString.indexOf('*') != -1) {
            int indexOperateur = operationString.indexOf('*');
            BiFunction<Long, Long, Long> fonctionCalcul = (o1, o2) -> o1 * o2;
            operation = creeOperation(operationString, indexOperateur, fonctionCalcul);

        } else {
            int indexOperateur = operationString.indexOf('+');
            operation = creeOperation(operationString, indexOperateur, Long::sum);

        }

        return operation;
    }

    private Operation creeOperation(String operationString, int indexOperateur, BiFunction<Long, Long, Long> fonctionCalcul) {
        Operation operation;
        String operande1String = operationString.substring(0, indexOperateur).trim();
        String operande2String = operationString.substring(indexOperateur + 1).trim();

        Operande operande1;
        Operande operande2;

        if (estUnNombre(operande1String)) {
            operande1 = new OperandeInteger(Integer.parseInt(operande1String));
        } else {
            operande1 = new OperandeSelf();
        }

        if (estUnNombre(operande2String)) {
            operande2 = new OperandeInteger(Integer.parseInt(operande2String));
        } else {
            operande2 = new OperandeSelf();
        }

        operation = new Operation(operande1, operande2, fonctionCalcul);
        return operation;
    }

    private boolean estUnNombre(String string) {
        return string.chars().allMatch(Character::isDigit);
    }

    private List<Integer> recupererItems(List<String> lignesSinge) {
        String ligneItems = lignesSinge.get(INDEX_LIGNE_ITEMS);
        return Arrays.stream(ligneItems.substring(ligneItems.indexOf(':') + 1).split(",")).map(c -> Integer.parseInt(c.trim())).toList();
    }

    public List<Singe> recupererSinges() {
        return singes;
    }

}
