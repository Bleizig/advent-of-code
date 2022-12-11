package Day11.Entree;

import Day11.Modele.Operande.Operande;
import Day11.Modele.Operande.OperandeInteger;
import Day11.Modele.Operande.OperandeSelf;
import Day11.Modele.Operation;
import Day11.Modele.Singe;
import Day11.Modele.Singes;
import Day11.Modele.Test;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LecteurInput {
    public static final int TAILLE_BLOC_SINGE = 6;
    public static final int INDEX_LIGNE_ITEMS = 1;
    public static final int INDEX_LIGNE_OPERATION = 2;
    public static final int INDEX_TEST_DIVISEUR = 3;
    public static final int INDEX_TEST_SINGE_VRAI = 4;
    public static final int INDEX_TEST_SINGE_FAUX = 5;
    public static final Pattern PATTERN_OPERATION = Pattern.compile("Operation: new = (.*) (.) (.*)$");

    List<Singe> singes;

    public LecteurInput() {
        lireSinges();
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

        return new Test(singeFaux, singeVrai, diviseur);
    }

    private int recupereSingeDeLigneSinge(String ligneSinge) {
        return recupereEntierSitueEnFinDeChaineApresTexte(ligneSinge, "monkey");
    }

    private int recupereEntierSitueEnFinDeChaineApresTexte(String chaine, String texteAvantEntier) {
        return Integer.parseInt(chaine.substring(chaine.indexOf(texteAvantEntier) + texteAvantEntier.length()).trim());
    }

    private Operation recupererOperation(List<String> lignesSinge) {
        String ligne = lignesSinge.get(INDEX_LIGNE_OPERATION);

        Matcher matcher = PATTERN_OPERATION.matcher(ligne);
        if (!matcher.find()) {
            throw new RuntimeException("Oh oh ...");
        }

        Operande operande1 = getOperandeFromString(matcher.group(1));
        Operande operande2 = getOperandeFromString(matcher.group(3));
        String operateur = matcher.group(2);

        return switch (operateur) {
            case "+" -> item -> operande1.getValeur(item) + operande2.getValeur(item);
            case "*" -> item -> operande1.getValeur(item) * operande2.getValeur(item);
            default -> throw new RuntimeException("L'opérateur '" + operateur + "' n'est pas géré");
        };
    }

    private Operande getOperandeFromString(String operandeString) {
        return estUnNombre(operandeString) ? new OperandeInteger(Integer.parseInt(operandeString)) : new OperandeSelf();
    }

    private boolean estUnNombre(String string) {
        return string.chars().allMatch(Character::isDigit);
    }

    private List<Integer> recupererItems(List<String> lignesSinge) {
        String ligneItems = lignesSinge.get(INDEX_LIGNE_ITEMS);
        return Arrays.stream(ligneItems.substring(ligneItems.indexOf(':') + 1).split(",")).map(c -> Integer.parseInt(c.trim())).toList();
    }

    public Singes recupererSinges() {
        return new Singes(singes);
    }

}
