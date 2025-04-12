package Main;

import DerouleJeu.*;
import Exceptions.Exceptions;
import joueurBot.*;
import Roles.*;

public class Main {

    private static int tour;
    private static int tourSurvecu;

    public static void main(String[] args) {
        lancerJeu();
    }

    public static void lancerJeu() {
        tour = 1;
        tourSurvecu = 1;

        Plateau.initialisationJeu();

        System.out.println("        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- .......    Crépuscule   ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");
        Jeu.tourPreliminaire();

        // Boucle principale du jeu
        while (!Jeu.getVictoireVillageois() && !Jeu.getVictoireLoup()) {
            System.out.println("\n        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- .......      Nuit "+tour+"     ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

            Jeu.tourDeJeuNuit();

            System.out.println("        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- .......      Jour "+tour+"     ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

            if (Plateau.getGrosBG().getIsAlive()) {
                tourSurvecu++;
            }

            Jeu.tourDeJeuJour();
            tour++;
        }

        afficherFin();
    }

    public static void afficherFin() {
        System.out.println("\n        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- .......  Fin du Jeu.  ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

        Exceptions.sleepJeu(3000);

        if (Jeu.getVictoireVillageois()) {
            System.out.println("\nLes villageois ont gagné!");
            if (Plateau.getGrosBG().getPerso().getNom().equals("Loup Garou")) {
                System.out.println("Dommage pour vous. :)");
            } else {
                System.out.println("Bravo, vous avez réussi à éliminer tous les Loups Garous!");
                Exceptions.sleepJeu(3000);
            }
        }

        if (Jeu.getVictoireLoup()) {
            System.out.println("\nLes Loups Garous ont gagné!");
            if (Plateau.getGrosBG().getPerso().getNom().equals("Loup Garou")) {
                System.out.println("Bravo à vous. :)");
            } else {
                System.out.println("Dommage pour le village, ils n'ont pas su éliminer tous les Loups Garous...");
            }
        }

        System.out.println("\n Cette partie a duré "+tour+" jour(s).");
        System.out.println("\n Vous avez survécu "+tourSurvecu+" jour(s).");
    }
}