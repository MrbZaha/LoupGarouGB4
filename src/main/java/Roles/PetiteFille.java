package Roles;

import joueurBot.*;
import java.util.Random;
import DerouleJeu.Jeu;                                     // Pour avoir la liste des LG

import java.util.ArrayList;

public class PetiteFille extends Personnages{
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=4;

    // Variables propres à la Petite Fille
    private static Joueur joueurObserve;                   // Stocke le joueur qui a été vu cette nuit là pour la confiance
    Random random = new Random();                          // Le fait que la petite fille puisse voir un LG est basé sur de la chance


    // Constructeur prend en argument l'id du joueur
    public PetiteFille(int identifiant){
        super(identifiant, id,"Petite Fille");
    }


    // Getters
    public static Joueur getJoueurObserve(){
        return joueurObserve; }

    // Setters
    public void setJoueurObserve(Joueur loup){
        this.joueurObserve=loup; }


    // Fonctions
    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        int chance = random.nextInt(4);               // Il y a une chance sur 4 que l'évènement se déroule
        if(chance==3){
            setJoueurObserve((Joueur) Jeu.getListeLG().get(random.nextInt(Jeu.getListeLG().size())));        // Choisit un loup aléatoire
        }
        else{
            setJoueurObserve(null);
        }
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était la Petite-fille!";
    }
}

