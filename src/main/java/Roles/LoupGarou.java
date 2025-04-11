package Roles;

import joueurBot.Joueur;
import DerouleJeu.Jeu;

import java.util.Random;

public class LoupGarou extends Personnages {
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=3;


    // Constructeur prend en argument l'id du joueur
    public LoupGarou(int identifiant){
        super(identifiant, id,"Loup Garou");
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    @Override
    public void actionNuit(Joueur joueurActuel, Joueur cible) {
        // Durant la nuit, les loups-garous peuvent se réveiller
        // Choisir un des joueurs/bot et le tuer
        // Il y a une faible possibilité que cela rate pour le bot
        cible.setIsAlive(false);
        Jeu.setMortsDuSoir(cible);
        // System.out.println(joueurActuel.getNom()+" a tué "+cible.getNom());                  // À retirer après, pour debug
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était un Loup Garou! Il en reste "+(Jeu.getListeLG().size()-1)+".";
    }
}