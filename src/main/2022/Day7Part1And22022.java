import java.util.*;
import java.util.stream.Collectors;

public class Day7Part1And22022 {

    public static void main(String[] args) {
        Repertoire racine = recupererArborescence();
        long resultatPart1 = determinerSommesDeTousLesRepertoiresDe100kOuMoins(racine);
        long resultatPart2 = determinerTailleRepertoireASupprimerPourAvoir30MilDePlace(racine);
        System.out.println("Réponse part1=" + resultatPart1 + " part2= " + resultatPart2);
    }

    private static long determinerSommesDeTousLesRepertoiresDe100kOuMoins(Repertoire racine) {
        List<Long> tailles = racine.recupererTaillesDeTousLesRepertoires();

        return tailles.stream().filter(l -> l <= 100000).reduce(Long::sum).orElse(0L);
    }

    private static long determinerTailleRepertoireASupprimerPourAvoir30MilDePlace(Repertoire racine) {
        List<Long> tailles = racine.recupererTaillesDeTousLesRepertoires();

        long placeMinALiberer = 30000000 - (70000000 - racine.tailleTotaleDesFichiers);

        tailles.sort(Collections.reverseOrder());

        List<Long> collect = tailles.stream().filter(t -> t >= placeMinALiberer).collect(Collectors.toList());

        return tailles.stream().filter(t -> t >= placeMinALiberer).min(Long::compareTo).get();
    }


    private static Repertoire recupererArborescence() {
        List<String> lignes = Util.lireFichier("entree.txt");

        assert lignes.get(0).equals("$ cd /") : "Le fichier doit commencer par un cd de la racine";
        assert lignes.get(1).equals("$ ls") : "La deuxième ligne doit demander un contenu de la racine";

        Repertoire racine = new Repertoire("root");

        //TODO : passage par référence ok ?
        ContexteParcoursArborescence contexteParcoursArborescence = new ContexteParcoursArborescence(racine);

        for (int i = 2; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            LigneTerminal ligneTerminal = convertirLigneEnLigneTerminal(ligne);
            ligneTerminal.appliquerLigne(contexteParcoursArborescence);
        }

        //TODO : passage par référence ok ?
        return racine;
    }

    private static LigneTerminal convertirLigneEnLigneTerminal(String ligne) {
        if (ligne.startsWith("$")) {
            return convertirLigneCommandeEnLigneTerminal(ligne);
        } else {
            return convertirLigneRetourCommandeEnLigneTerminal(ligne);
        }
    }

    private static LigneTerminal convertirLigneRetourCommandeEnLigneTerminal(String ligne) {
        if (ligne.startsWith("dir")) {
            return new LigneTerminalDescriptionRepertoire(ligne.substring(4));
        } else {
            Scanner scanner = new Scanner(ligne);
            return new LigneTerminalDescriptionFichier(scanner.nextInt());
        }
    }

    private static LigneTerminal convertirLigneCommandeEnLigneTerminal(String ligne) {
        if (ligne.contains("cd")) {
            return new LigneTerminalChangementRepertoire(ligne.substring(5));
        } else {
            return new LigneTerminalSansApplication();
        }
    }


    private static class Repertoire {
        List<Repertoire> enfants;

        Long tailleTotaleDesFichiers;

        String nom;

        public Repertoire(String nom) {
            this.nom = nom;
            this.enfants = new ArrayList<>();
            this.tailleTotaleDesFichiers = 0L;
        }

        public List<Long> recupererTaillesDeTousLesRepertoires() {
            Queue<Repertoire> reperoiresAParcourir = new LinkedList<>();
            reperoiresAParcourir.add(this);

            List<Long> taillesDeTousLesRepertoires = new ArrayList<>();
            while (!reperoiresAParcourir.isEmpty()) {
                Repertoire repertoireParcouru = reperoiresAParcourir.remove();
                reperoiresAParcourir.addAll(repertoireParcouru.enfants);
                taillesDeTousLesRepertoires.add(repertoireParcouru.tailleTotaleDesFichiers);
            }
            return taillesDeTousLesRepertoires;
        }

        public Repertoire getRepertoireEnfant(String repertoireEnfant) {
            return enfants.stream().filter(r -> repertoireEnfant.equals(r.nom)).findFirst().get();
        }

        public void ajouterRepertoireEnfant(String nomRepertoire) {
            enfants.add(new Repertoire(nomRepertoire));
        }

        public void ajouterTailleFichier(int tailleFichier) {
            tailleTotaleDesFichiers += tailleFichier;
        }
    }

    private static class ContexteParcoursArborescence {
        Repertoire racine;

        Stack<Repertoire> cheminEnCours;

        public ContexteParcoursArborescence(Repertoire racine) {
            this.racine = racine;
            cheminEnCours = new Stack<>();
            cheminEnCours.add(racine);
        }

        public void remonterVersRepertoireParent() {
            cheminEnCours.pop();
        }

        public void descendreVersRepertoireEnfant(String nomRepertoireEnfant) {
            Repertoire repertoireEnfant = cheminEnCours.peek().getRepertoireEnfant(nomRepertoireEnfant);
            cheminEnCours.add(repertoireEnfant);
        }

        public void ajouterRepertoireEnfant(String nomRepertoire) {
            cheminEnCours.peek().ajouterRepertoireEnfant(nomRepertoire);
        }

        public void ajouterTailleFichierARepertoireEnCours(int tailleFichier) {
            cheminEnCours.stream().forEach(r -> r.ajouterTailleFichier(tailleFichier));
        }
    }

    private static interface LigneTerminal {
        void appliquerLigne(ContexteParcoursArborescence contexteParcoursArborescence);

    }

    private static class LigneTerminalSansApplication implements LigneTerminal {
        @Override
        public void appliquerLigne(ContexteParcoursArborescence contexteParcoursArborescence) {
            //Ne fait rien.
        }
    }

    private static class LigneTerminalChangementRepertoire implements LigneTerminal {
        String repertoireCible;

        public LigneTerminalChangementRepertoire(String repertoireCibble) {
            this.repertoireCible = repertoireCibble;
        }

        @Override
        public void appliquerLigne(ContexteParcoursArborescence contexteParcoursArborescence) {
            if ("..".equals(repertoireCible)) {
                contexteParcoursArborescence.remonterVersRepertoireParent();
            } else {
                contexteParcoursArborescence.descendreVersRepertoireEnfant(repertoireCible);
            }
        }
    }

    private static class LigneTerminalDescriptionRepertoire implements LigneTerminal {
        String nomRepertoire;

        public LigneTerminalDescriptionRepertoire(String nomRepertoire) {
            this.nomRepertoire = nomRepertoire;
        }

        @Override
        public void appliquerLigne(ContexteParcoursArborescence contexteParcoursArborescence) {
            contexteParcoursArborescence.ajouterRepertoireEnfant(nomRepertoire);
        }
    }

    private static class LigneTerminalDescriptionFichier implements LigneTerminal {
        int tailleFichier;

        public LigneTerminalDescriptionFichier(int tailleFichier) {
            this.tailleFichier = tailleFichier;
        }

        @Override
        public void appliquerLigne(ContexteParcoursArborescence contexteParcoursArborescence) {
            contexteParcoursArborescence.ajouterTailleFichierARepertoireEnCours(tailleFichier);
        }
    }
}
