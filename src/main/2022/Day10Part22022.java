import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day10Part22022 {

    public static void main(String[] args) {
        List<Instruction> instructions = recupererInstructions();
        Cpu cpu = new Cpu();
        cpu.appliquerInstructions(instructions);
        cpu.afficherEcran();
    }

    private static List<Instruction> recupererInstructions() {
        List<String> lignes = Util.lireFichier("entree.txt");

        return lignes.stream().map(Day10Part22022::convertirLigneEnInstruction).collect(Collectors.toList());
    }

    private static Instruction convertirLigneEnInstruction(String ligne) {
        Scanner scanner = new Scanner(ligne);
        String instructionString = scanner.next();
        return switch (instructionString) {
            case "noop" -> new NoopInstruction();
            case "addx" -> new AddXInstruction(scanner.nextInt());
            default -> throw new IllegalArgumentException("Instruction '" + instructionString + "' non gérée");
        };
    }

    private static class Cpu {
        public static final int LONGUEUR_LIGNE = 40;
        int registre;
        int cyclesTermines;

        String affichage;

        public Cpu() {
            registre = 1;
            cyclesTermines = 0;
            affichage = "";
        }

        public void appliquerInstructions(List<Instruction> instructions) {
            instructions.forEach(this::appliquerInstruction);
        }

        private void appliquerInstruction(Instruction instruction) {
            instruction.appliquer(this);
        }

        public void consommerCycle() {
            afficherPixel();
            cyclesTermines++;
        }

        private void afficherPixel() {
            if (spriteConincideAvecCycle()) {
                affichage += "#";
            } else {
                affichage += ".";
            }
        }

        private boolean spriteConincideAvecCycle() {
            return Math.abs(cyclesTermines % LONGUEUR_LIGNE - registre) <= 1;
        }

        public void ajouterAuRegistre(int valeur) {
            registre += valeur;
        }

        public void afficherEcran() {
            String[] lignesAffichage = affichage.split("(?<=\\G.{" + LONGUEUR_LIGNE + "})");
            Arrays.stream(lignesAffichage).forEach(System.out::println);
        }
    }

    interface Instruction {

        void appliquer(Cpu cpu);
    }

    private static class NoopInstruction implements Instruction {

        @Override
        public void appliquer(Cpu cpu) {
            cpu.consommerCycle();
        }
    }

    private static class AddXInstruction implements Instruction {
        int valeur;

        public AddXInstruction(int valeur) {
            this.valeur = valeur;
        }

        @Override
        public void appliquer(Cpu cpu) {
            cpu.consommerCycle();
            cpu.consommerCycle();
            cpu.ajouterAuRegistre(valeur);
        }
    }

}
