import util.Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day6Part2 {

    public static void main(String[] args) {
        List<Integer> poissons = recupererPoissons("entree.txt");
        BigInteger resultat = calculerNombrePoissonsApresXJours(poissons, 256);
        System.out.println("Réponse=" + resultat);
    }

    private static BigInteger calculerNombrePoissonsApresXJours(List<Integer> poissons, int nbJours) {
        EtatGlobal etatGlobal = new EtatGlobal();
        etatGlobal.initialiserEtatGestation(poissons);
        for (int i = 0; i < nbJours; i++) {
            etatGlobal.avancerDeUnJour();
        }
        return etatGlobal.getNombreTotalDePoissons();
    }

    private static List<Integer> recupererPoissons(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);
        String poissonsChaine = lignes.get(0);
        String[] tableauPoissonsChaine = poissonsChaine.split(",");

        List<Integer> poissons = new ArrayList<>();
        for (String poisson : tableauPoissonsChaine) {
            poissons.add(Integer.parseInt(poisson));
        }

        return poissons;
    }

    private static class EtatGlobal {
        public static final int TEMPS_ARRIVEE_AGE_ADULTE = 2;
        public static final int TEMPS_GESTATION = 7;
        private BigInteger[] gestation = new BigInteger[TEMPS_GESTATION];
        private int indexJourCourant;
        private BigInteger[] couveuse = new BigInteger[TEMPS_ARRIVEE_AGE_ADULTE];

        public EtatGlobal() {
            for (int i = 0; i < TEMPS_GESTATION; i++) {
                gestation[i] = BigInteger.ZERO;
            }
            for (int i = 0; i < TEMPS_ARRIVEE_AGE_ADULTE; i++) {
                couveuse[i] = BigInteger.ZERO;
            }
        }

        public void avancerDeUnJour() {
            BigInteger nombreBebesANaitres = recupererNombreBebesANaitre();
            vieillirBebes();
            decalerIndexJour();
            rajouterBebes(nombreBebesANaitres);
        }

        private void rajouterBebes(BigInteger nombreBebesANaitres) {
            couveuse[1] = nombreBebesANaitres;
        }

        private void decalerIndexJour() {
            indexJourCourant++;
            indexJourCourant %= TEMPS_GESTATION;
        }

        private void vieillirBebes() {
            gestation[indexJourCourant] = gestation[indexJourCourant].add(couveuse[0]);
            couveuse[0] = couveuse[1];
        }

        private BigInteger recupererNombreBebesANaitre() {
            return gestation[indexJourCourant];
        }

        public void initialiserEtatGestation(List<Integer> poissons) {
            for (Integer poisson : poissons) {
                gestation[poisson] = gestation[poisson].add(BigInteger.ONE);
            }
        }

        public BigInteger getNombreTotalDePoissons() {
            BigInteger total = BigInteger.ZERO;
            for (int i = 0; i < 7; i++) {
                total = total.add(gestation[i]);
            }
            total = total.add(couveuse[0]).add(couveuse[1]);

            return total;
        }
    }
}



