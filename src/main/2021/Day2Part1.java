import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day2Part1 {

    public static void main(String[] args) {
        List<Instruction> instructions = recupererInstructions("entree.txt");
        int resultat = appliquerInstructions(instructions);
        System.out.println("Réponse=" + resultat);
    }

    private static int appliquerInstructions(List<Instruction> instructions) {
        Etat etat = new Etat();
        for (Instruction instruction : instructions) {
            appliquerInstructionSurEtat(instruction, etat);
        }
        return etat.position * etat.profondeur;
    }

    private static void appliquerInstructionSurEtat(Instruction instruction, Etat etat) {
        instruction.commande.appliquer(etat, instruction.valeur);
    }

    private static List<Instruction> recupererInstructions(String nomFichier) {
        List<String> lignes = Util.lireFichier(nomFichier);

        List<Instruction> instructions = new ArrayList<>();
        for (String ligne : lignes) {
            Instruction instruction = convertirChaineEnInstruction(ligne);
            instructions.add(instruction);
        }
        return instructions;
    }

    private static Instruction convertirChaineEnInstruction(String chaine) {
        int indexDelimiteur = chaine.indexOf(' ');
        String commande = chaine.substring(0, indexDelimiteur);
        String valeur = chaine.substring(indexDelimiteur + 1);
        return new Instruction(Commande.valueOf(commande.toUpperCase()), Integer.parseInt(valeur));
    }

    enum Commande {
        FORWARD {
            @Override
            void appliquer(Etat etat, int valeur) {
                etat.position += valeur;
            }
        },
        DOWN {
            @Override
            void appliquer(Etat etat, int valeur) {
                etat.profondeur += valeur;
            }
        },
        UP {
            @Override
            void appliquer(Etat etat, int valeur) {
                etat.profondeur -= valeur;
            }
        };

        abstract void appliquer(Etat etat, int valeur);
    }

    private static class Instruction {
        Commande commande;
        int valeur;

        public Instruction(Commande commande, int valeur) {
            this.commande = commande;
            this.valeur = valeur;
        }
    }

    private static class Etat {
        int position = 0;
        int profondeur = 0;
    }
}
