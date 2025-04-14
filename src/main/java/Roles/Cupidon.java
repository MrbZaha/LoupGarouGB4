package Roles;

import joueurBot.Joueur;
import DerouleJeu.Jeu;

public class Cupidon extends Personnages{
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=0;

    // Constructeur prend en argument l'id du joueur
    public Cupidon(int identifiant){
        super(identifiant, id,"Cupidon");
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    // Fonctions
    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        // Décide des amoureux pour la partie, ne peut le faire qu'au premier tour, est forcément en vie
        Jeu.setAmoureux(new Joueur[]{cible1, cible2});
        cible1.setIsLove(true);
        cible2.setIsLove(true);
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était le Cupidon!";
    }
}