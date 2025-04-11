package Main;

import DerouleJeu.*;
import Exceptions.Exceptions;
import joueurBot.*;
import Roles.*;

public class Main {

    private static int tour=1;
    private static int tourSurvecu=1;

    // Lancement du jeu
    public void main(String[] args){
        // Combien de joueurs pour la partie?

        Plateau.initialisationJeu();

        System.out.println("        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- .......    Crépuscule   ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");
        Jeu.tourPreliminaire();

        // Boucle principale du jeu
        while (!Jeu.getVictoireVillageois() && !Jeu.getVictoireLoup()){
            System.out.println("        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                    STR."        // --- --- --- --- --- --------------- .......      Nuit \{tour}     ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

                // Faire le tour de nuit
            Jeu.tourDeJeuNuit();

            System.out.println("        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                    STR."        // --- --- --- --- --- --------------- .......      Jour \{tour}     ....... --------------- --- --- --- --- --- //\n" +
                    "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

            if(Plateau.getGrosBG().getIsAlive()) {
                tourSurvecu++;                                  // Le nombre de tours qu'iel a survécu incrémente
            }

            // Faire le tour de jour
            Jeu.tourDeJeuJour();

            tour++;
        }

        System.out.println("\n        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //\n" +
                STR."        // --- --- --- --- --- --------------- .......  Fin du Jeu.  ....... --------------- --- --- --- --- --- //\n" +
                "        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //");

        Exceptions.sleepJeu(3000);

        // Si les villageois gagnent
        if (Jeu.getVictoireVillageois()){
            System.out.println("\nLes villageois ont gagné!");
            if(Plateau.getGrosBG().getPerso().getNom().equals("Loup Garou")){
                System.out.println("Dommage pour vous. :)");
            } else {
                System.out.println("Bravo, vous avez réussi à éliminer tous les Loups Garous!");
                Exceptions.sleepJeu(3000);
            }
        }

        // Si les LG gagnent
        if (Jeu.getVictoireLoup()){
            System.out.println("\nLes Loups Garous ont gagné!");
            if(Plateau.getGrosBG().getPerso().getNom().equals("Loup Garou")){
                System.out.println("Bravo à vous. :)");
            } else {
                System.out.println("Dommage pour le village, ils n'ont pas su éliminer tous les Loups Garous...");
            }
        }

        System.out.println(STR."\n Cette partie a duré \{tour} jour(s).");
        System.out.println(STR."\n Vous avez survécu \{tourSurvecu} jour(s).");
    }
}
