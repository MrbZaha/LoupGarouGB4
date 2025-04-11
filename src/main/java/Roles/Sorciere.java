package Roles;

import DerouleJeu.Jeu;
import joueurBot.Joueur;
import java.util.Scanner;

public class Sorciere extends Personnages{
    private static final int id=5;                 // Indice du personnage (suivant l'ordre de jeu)
    private static int choixVieMort=0;             // Ajout de cette variable statique pour ne pas modifier l'override action
    private static int potionVie=1;
    private static int potionMort=1;

    private Scanner input = new Scanner(System.in);


    // Constructeur prend en argument l'id du joueur
    public Sorciere(int identifiant){
        super(identifiant, id,"Sorcière");
    }


    //Setter
    public static void setChoixVieMort(int choixVieMort) {
        Sorciere.choixVieMort = choixVieMort;
    }


    // Getters
    public static int getPotionVie(){
        return potionVie;
    }

    public static int getPotionMort() {
        return potionMort;
    }

    public static int getChoixVieMort(){ return choixVieMort; }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    // Fonctions
    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        if (choixVieMort==1){                              // Si on veut sauver
            cible1.setIsAlive(true);                            // Revenir
            Jeu.setMortSauve(cible1);
            potionVie=0;
        }
        else if(choixVieMort==2){                               // Si on veut tuer
            cible1.setIsAlive(false);
            Jeu.setMortsDuSoir(cible1);
            potionMort=0;
        }
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était la Sorcière!";
    }
}