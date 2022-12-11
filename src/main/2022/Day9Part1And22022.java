import util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day9Part1And22022 {

    public static void main(String[] args) {
        List<Mouvement> mouvements = recupererMouvements();
        Simulation simulation = new Simulation();
        simulation.appliquerMouvementsTete(mouvements);
        int resultat = simulation.recupererNombreDePositionsUniquesDeLaQueueContre(); //Pierre, Claude, je sais que vous avez souri :)
        System.out.println("Réponse=" + resultat);
    }

    private static List<Mouvement> recupererMouvements() {
        List<String> lignes = Util.lireFichier("entree.txt");

        return lignes.stream().map(Day9Part1And22022::convertirLigneEnMouvement).collect(Collectors.toList());
    }

    private static Mouvement convertirLigneEnMouvement(String ligne) {
        Scanner scanner = new Scanner(ligne);
        String direction = scanner.next();
        int nombreCases = scanner.nextInt();

        return switch (direction) {
            case "R" -> new Mouvement(nombreCases, 0);
            case "L" -> new Mouvement(-nombreCases, 0);
            case "U" -> new Mouvement(0, nombreCases);
            case "D" -> new Mouvement(0, -nombreCases);
            default -> throw new IllegalArgumentException("La direction '" + direction + "' n'est pas reconnue");
        };
    }

    private static class Simulation {
        final static int TAILLE_CORDE = 10;

        List<Point> positions;

        Set<Point> positionsUniquesDeLaQueue;

        public Simulation() {
            positions = new ArrayList<>();
            for (int i = 0; i < TAILLE_CORDE; i++) {
                positions.add(new Point(0, 0));
            }
            positionsUniquesDeLaQueue = new HashSet<>();
            positionsUniquesDeLaQueue.add(positions.get(positions.size() - 1));
        }

        public void appliquerMouvementsTete(List<Mouvement> mouvements) {
            List<Mouvement> mouvementsUneCase = convertirMouvementsEnMouvementsDeUneCase(mouvements);
            mouvementsUneCase.forEach(this::appliquerMouvementTete);
        }

        private List<Mouvement> convertirMouvementsEnMouvementsDeUneCase(List<Mouvement> mouvements) {
            return mouvements.stream().map(Mouvement::convertirMouvementEnMouvementsDeUneCase).flatMap(Collection::stream).collect(Collectors.toList());
        }


        private void appliquerMouvementTete(Mouvement mouvement) {
            positions.set(0, mouvement.recupererPositionArrivee(positions.get(0)));
            for (int i = 1; i < TAILLE_CORDE; i++) {
                appliquerMouvementNoeud(i);
            }
        }

        private void appliquerMouvementNoeud(int indexNoeud) {
            Point positionNoeudPrecedent = positions.get(indexNoeud - 1);
            Point positionNoeudABouger = positions.get(indexNoeud);

            Mouvement mouvementQueue = determinerMouvementAAppliquer(positionNoeudPrecedent, positionNoeudABouger);

            Point nouvellePositionNoeudABouger = mouvementQueue.recupererPositionArrivee(positionNoeudABouger);
            positions.set(indexNoeud, nouvellePositionNoeudABouger);

            if (indexNoeud == TAILLE_CORDE - 1) {
                positionsUniquesDeLaQueue.add(nouvellePositionNoeudABouger);
            }

        }

        private Mouvement determinerMouvementAAppliquer(Point positionNoeudCible, Point positionNoeudABouger) {
            int ecartX = calculerEcartXNoeudPrecedentNoeudABouger(positionNoeudCible, positionNoeudABouger);
            int ecartY = calculerEcartYNoeudPrecedentNoeudABouger(positionNoeudCible, positionNoeudABouger);

            int directionX = 0;
            int directionY = 0;

            if (Math.abs(ecartX) == 2) {
                directionX = ecartX / 2;
                if (Math.abs(ecartY) == 1) {
                    directionY = ecartY;
                }
            }
            if (Math.abs(ecartY) == 2) {
                directionY = ecartY / 2;
                if (Math.abs(ecartX) == 1) {
                    directionX = ecartX;
                }
            }

            return new Mouvement(directionX, directionY);
        }

        private int calculerEcartXNoeudPrecedentNoeudABouger(Point positionNoeudPrecedent, Point positionNoeudABouger) {
            return positionNoeudPrecedent.x - positionNoeudABouger.x;
        }

        private int calculerEcartYNoeudPrecedentNoeudABouger(Point positionNoeudPrecedent, Point positionNoeudABouger) {
            return positionNoeudPrecedent.y - positionNoeudABouger.y;
        }


        public int recupererNombreDePositionsUniquesDeLaQueueContre() {
            return positionsUniquesDeLaQueue.size();
        }

    }

    private static class Mouvement {
        int directionX;
        int directionY;

        public Mouvement(int directionX, int directionY) {
            this.directionX = directionX;
            this.directionY = directionY;
        }

        public Point recupererPositionArrivee(Point positionDepart) {
            return new Point(positionDepart.x + directionX, positionDepart.y + directionY);
        }

        public List<Mouvement> convertirMouvementEnMouvementsDeUneCase() {
            if (directionX != 0 && directionY == 0) {
                return creerMouvementsDeUneCase(Math.abs(directionX), Integer.signum(directionX), 0);
            } else if (directionY != 0 && directionX == 0) {
                return creerMouvementsDeUneCase(Math.abs(directionY), 0, Integer.signum(directionY));
            } else {
                throw new RuntimeException("Non géré");
            }
        }

        private List<Mouvement> creerMouvementsDeUneCase(int nombreMouvements, int dx, int dy) {
            List<Mouvement> mouvements = new ArrayList<>();
            for (int i = 0; i < nombreMouvements; i++) {
                mouvements.add(new Mouvement(dx, dy));
            }
            return mouvements;
        }
    }

    private record Point(int x, int y) {
    }

}
