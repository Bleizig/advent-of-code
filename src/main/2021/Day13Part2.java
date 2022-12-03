import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day13Part2 {

    public static void main(String[] args) {
        TransparentEtPliures transparentEtPliures = recupererTransparentEtPliures("entree.txt");
        Boolean[][] transparent = determinerTransparentApresToutesLesPliures(transparentEtPliures);
        afficherTransparent(transparent);
    }

    private static void afficherTransparent(Boolean[][] transparent) {
        for (int j = 0; j < transparent[0].length; j++) {
            for (int i = 0; i < transparent.length; i++) {
                System.out.print(afficherBooleen(transparent[i][j]));
            }
            System.out.println();
        }
    }

    private static String afficherBooleen(Boolean aBoolean) {
        if (aBoolean == Boolean.TRUE) {
            return "#";
        } else {
            return ".";
        }
    }

    private static Boolean[][] determinerTransparentApresToutesLesPliures(TransparentEtPliures transparentEtPliures) {
        while (transparentEtPliures.aEncoreDesPliures()) {
            transparentEtPliures.plierUneFoisEnDeuxPartiesEgales();
        }
        return transparentEtPliures.transparent;
    }

    private static TransparentEtPliures recupererTransparentEtPliures(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        List<Point> points = new ArrayList<>();

        {
            String ligne = lignes.get(0);
            while (!ligne.isBlank()) {
                Point p = convertirLigneEnPoint(ligne);
                points.add(p);
                lignes.remove(0);
                ligne = lignes.get(0);
            }
            lignes.remove(0);
        }

        List<Pliure> pliures = new ArrayList<>();
        for (String ligne : lignes) {
            Pliure pliure = convertirLigneEnPliure(ligne);
            pliures.add(pliure);
        }

        Boolean[][] transparent = convertirListePointsEnTransparent(points);

        return new TransparentEtPliures(transparent, pliures);
    }

    private static Boolean[][] convertirListePointsEnTransparent(List<Point> points) {
        int maxX = points.stream().map(p -> p.x).max(Integer::compare).orElseThrow();
        int maxY = points.stream().map(p -> p.y).max(Integer::compare).orElseThrow();
        Boolean[][] transparent = new Boolean[maxX + 1][maxY + 1];
        for (Point p : points) {
            transparent[p.x][p.y] = true;
        }
        return transparent;
    }

    private static Pliure convertirLigneEnPliure(String ligne) {
        int positionEgal = ligne.indexOf("=");

        Pliure pliure = new Pliure();
        pliure.axe = AxePliure.valueOf(ligne.substring(positionEgal - 1, positionEgal).toUpperCase());
        pliure.position = Integer.parseInt(ligne.substring(positionEgal + 1));

        return pliure;
    }

    private static Point convertirLigneEnPoint(String ligne) {
        String[] valeurs = ligne.split(",");
        return new Point(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]));
    }


    private static class TransparentEtPliures {
        private Boolean[][] transparent;
        private List<Pliure> pliures;

        public TransparentEtPliures(Boolean[][] transparent, List<Pliure> pliures) {
            this.transparent = transparent;
            this.pliures = pliures;
        }

        public void plierUneFoisEnDeuxPartiesEgales() {
            Pliure pliure = recupererProchainePliure();
            transparent = pliure.axe.appliquerPliureSurTransparent(pliure, transparent);
        }

        private Pliure recupererProchainePliure() {
            Pliure pliure = pliures.get(0);
            pliures.remove(0);
            return pliure;
        }

        public long getNombrePoints() {
            return Stream.of(transparent).flatMap(Stream::of).filter(b -> b == Boolean.TRUE).count();
        }

        public boolean aEncoreDesPliures() {
            return pliures.size() > 0;
        }
    }

    private static class Pliure {
        public AxePliure axe;
        public int position;
    }

    enum AxePliure {
        X {
            @Override
            public Boolean[][] appliquerPliureSurTransparent(Pliure pliure, Boolean[][] transparent) {
                Boolean[][] nouveauTransparent = new Boolean[pliure.position][transparent[0].length];
                for (int j = 0; j < transparent[0].length; j++) {
                    for (int i = 0; i < pliure.position; i++) {
                        nouveauTransparent[i][j] = transparent[i][j];
                    }
                    for (int i = pliure.position + 1; i < transparent.length; i++) {
                        if (transparent[i][j] == Boolean.TRUE) {
                            int distanceDeAxePliure = i - pliure.position;
                            nouveauTransparent[pliure.position - distanceDeAxePliure][j] = true;
                        }
                    }
                }
                return nouveauTransparent;
            }
        },
        Y {
            @Override
            public Boolean[][] appliquerPliureSurTransparent(Pliure pliure, Boolean[][] transparent) {
                Boolean[][] nouveauTransparent = new Boolean[transparent.length][pliure.position];

                for (int i = 0; i < transparent.length; i++) {
                    for (int j = 0; j < pliure.position; j++) {
                        nouveauTransparent[i][j] = transparent[i][j];
                    }
                    for (int j = pliure.position + 1; j < transparent[0].length; j++) {
                        if (transparent[i][j] == Boolean.TRUE) {
                            int distanceDeAxePliure = j - pliure.position;
                            nouveauTransparent[i][pliure.position - distanceDeAxePliure] = true;
                        }
                    }
                }
                return nouveauTransparent;
            }
        };

        public abstract Boolean[][] appliquerPliureSurTransparent(Pliure pliure, Boolean[][] transparent);
    }
}



