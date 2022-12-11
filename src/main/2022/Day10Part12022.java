import util.Util;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day10Part12022 {

    public static void main(String[] args) {
        List<Instruction> instructions = recupererInstructions();
        Cpu cpu = new Cpu();
        cpu.appliquerInstructions(instructions);
        int resultat = cpu.calculerLaSommeDesForcesDeSignalSurLesCyclesRemarquables();
        System.out.println("Réponse=" + resultat);
    }


    private static List<Instruction> recupererInstructions() {
        List<String> lignes = Util.lireFichier("entree.txt");

        return lignes.stream().map(Day10Part12022::convertirLigneEnInstruction).collect(Collectors.toList());
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
        private static final List<Integer> NB_CYCLES_REMARQUABLES = List.of(20, 60, 100, 140, 180, 220);

        int registre;
        int cyclesTermines;

        int sommesDesForcesDeSignalRemarquables;

        public Cpu() {
            registre = 1;
            cyclesTermines = 0;
            sommesDesForcesDeSignalRemarquables = 0;
        }

        public void appliquerInstructions(List<Instruction> instructions) {
            instructions.forEach(this::appliquerInstruction);
        }

        private void appliquerInstruction(Instruction instruction) {
            instruction.appliquer(this);
        }

        public int calculerLaSommeDesForcesDeSignalSurLesCyclesRemarquables() {
            return sommesDesForcesDeSignalRemarquables;
        }

        public void consommerCycle() {
            cyclesTermines++;
            if (NB_CYCLES_REMARQUABLES.contains(cyclesTermines)) {
                sommesDesForcesDeSignalRemarquables += cyclesTermines * registre;
            }

        }

        public void ajouterAuRegistre(int valeur) {
            registre += valeur;
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
