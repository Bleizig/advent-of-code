import util.Util;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class Day16Part1 {

    public static final int TYPE_PAQUET_NOMBRE = 4;
    public static final int MODE_OPERATEUR_LONGUEUR_FIXE = 0;
    public static final int NB_BITS_DECLARER_LONGUEUR_SOUS_PAQUETS = 15;
    public static final int NB_BITS_DECLARER_NOMBRE_SOUS_PAQUETS = 11;

    public static void main(String[] args) {
        String transmissionHexa = recupererTransmissionHexa("entree.txt");
        int resultat = calculerSommeVersionsPaquets(transmissionHexa);
        System.out.println("Résultat=" + resultat);
    }

    private static int calculerSommeVersionsPaquets(String transmissionHexa) {
        String transmissionRestante = convertirTransmissionHexaEnBinaire(transmissionHexa);

        int sommeVersions = 0;
        while (aAutreChoseQueDesZeros(transmissionRestante)) {
            String versionBinaire = lireVersion(transmissionRestante);
            int version = convertirBinaireEnDecimal(versionBinaire);
            sommeVersions += version;

            transmissionRestante = supprimerVersion(transmissionRestante);

            String typePaquetBinaire = lireTypePaquet(transmissionRestante);
            int typePaquet = convertirBinaireEnDecimal(typePaquetBinaire);

            transmissionRestante = supprimerTypePaquet(transmissionRestante);

            transmissionRestante = supprimerBitsRestantsAvantProchainPaquet(transmissionRestante, typePaquet);
        }

        return sommeVersions;
    }

    private static String supprimerBitsRestantsAvantProchainPaquet(String transmission, int typePaquet) {
        if (typePaquet == TYPE_PAQUET_NOMBRE) {
            transmission = supprimerNombre(transmission);
        } else {
            transmission = supprimerDeclarationOperateur(transmission);
        }
        return transmission;
    }

    private static String supprimerDeclarationOperateur(String transmission) {
        int modeOperateur = lireModeOperateur(transmission);
        transmission = supprimerModeOperateur(transmission);
        if (modeOperateur == MODE_OPERATEUR_LONGUEUR_FIXE) {
            transmission = supprimerBits(transmission, NB_BITS_DECLARER_LONGUEUR_SOUS_PAQUETS);
        } else {
            transmission = supprimerBits(transmission, NB_BITS_DECLARER_NOMBRE_SOUS_PAQUETS);
        }
        return transmission;
    }

    private static String convertirTransmissionHexaEnBinaire(String transmissionHexa) {
        return transmissionHexa.chars().mapToObj(Day16Part1::convertirCaractereHexaEnChaineBinaire).collect(Collectors.joining());
    }

    private static String convertirCaractereHexaEnChaineBinaire(int c) {
        String s = BigInteger.valueOf(Integer.valueOf(String.valueOf((char) c), 16)).toString(2);
        return rajoute0DevantPourArriverAUneLongueurDe4(s);
    }

    private static String rajoute0DevantPourArriverAUneLongueurDe4(String s) {
        return "0".repeat(Math.max(0, 4 - s.length())) + s;
    }

    private static int convertirBinaireEnDecimal(String versionBinaire) {
        return Integer.valueOf(versionBinaire, 2);
    }

    private static String supprimerBits(String transmission, int nbBits) {
        return transmission.substring(nbBits);
    }

    private static int lireModeOperateur(String transmission) {
        return Integer.parseInt(transmission.substring(0, 1));
    }

    private static String supprimerModeOperateur(String transmission) {
        return transmission.substring(1);
    }


    private static String supprimerNombre(String transmission) {
        String transmissionSansNombre = transmission;
        while (transmissionSansNombre.charAt(0) == '1') {
            transmissionSansNombre = transmissionSansNombre.substring(5);
        }
        transmissionSansNombre = transmissionSansNombre.substring(5);
        return transmissionSansNombre;
    }

    private static String supprimerTypePaquet(String transmission) {
        return transmission.substring(3);
    }

    private static String lireTypePaquet(String transmission) {
        return transmission.substring(0, 3);
    }

    private static String supprimerVersion(String transmission) {
        return transmission.substring(3);
    }

    private static String lireVersion(String transmission) {
        return transmission.substring(0, 3);
    }

    private static boolean aAutreChoseQueDesZeros(String transmissionRestante) {
        String s = transmissionRestante.replaceAll("0", "");
        return !s.isEmpty();
    }


    private static String recupererTransmissionHexa(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        return lignes.get(0);
    }
}



