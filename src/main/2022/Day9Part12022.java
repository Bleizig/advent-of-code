import java.util.*;
import java.util.stream.Collectors;

public class Day9Part12022 {

    public static void main(String[] args) {
        List<Mouvement> mouvements = recupererMouvements();
        Simulation simulation = new Simulation();
        simulation.appliquerMouvementsTete(mouvements);
        int resultat = simulation.recupererNombreDePositionsUniquesDeLaQueueContre(); //Pierre, Claude, je sais que vous avez souri :)
        System.out.println("Réponse=" + resultat);
    }


    private static List<Mouvement> recupererMouvements() {
        List<String> lignes = Util.lireFichier("entree.txt");

        return lignes.stream().map(Day9Part12022::convertirLigneEnMouvement).collect(Collectors.toList());
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
        Point positionQueue;
        Point positionTete;

        Set<Point> positionsUniquesDeLaQueue;

        public Simulation() {
            positionQueue = new Point(0, 0);
            positionTete = new Point(0, 0);
            positionsUniquesDeLaQueue = new HashSet<>();
            positionsUniquesDeLaQueue.add(positionQueue);
        }

        public void appliquerMouvementsTete(List<Mouvement> mouvements) {
            List<Mouvement> mouvementsUneCase = convertirMouvementsEnMouvementsDeUneCase(mouvements);
            mouvementsUneCase.forEach(this::appliquerMouvementTete);
        }

        private List<Mouvement> convertirMouvementsEnMouvementsDeUneCase(List<Mouvement> mouvements) {
            return mouvements.stream().map(Mouvement::convertirMouvementEnMouvementsDeUneCase).flatMap(Collection::stream).collect(Collectors.toList());
        }


        private void appliquerMouvementTete(Mouvement mouvement) {
            positionTete = mouvement.recupererPositionArrivee(positionTete);
            appliquerMouvementQueue();
        }

        private void appliquerMouvementQueue() {
            int ecartX = calculerEcartXTeteQueue();
            int ecartY = calculerEcartYTeteQueue();

            int directionX = 0;
            int directionY = 0;

            //TODO renommer ? decouper ? pas assez lisible !
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

            Mouvement mouvementQueue = new Mouvement(directionX, directionY);
            deplacerQueueVers(mouvementQueue.recupererPositionArrivee(positionQueue));
        }

        private int calculerEcartYTeteQueue() {
            return positionTete.y - positionQueue.y;
        }

        private int calculerEcartXTeteQueue() {
            return positionTete.x - positionQueue.x;
        }

        private void deplacerQueueVers(Point nouvellePosition) {
            positionQueue = nouvellePosition;
            positionsUniquesDeLaQueue.add(positionQueue);
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
