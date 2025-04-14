package Roles;

import joueurBot.Joueur;
import DerouleJeu.Jeu;

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
        // Durant la nuit, les loups-garous peuvent se réveille choisir un des joueurs/bot et le tuer
        // Il y a une faible possibilité que cela rate pour le bot
        cible.setIsAlive(false);
        Jeu.setMortsDuSoir(cible);
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était un Loup Garou! Il en reste "+(Jeu.getListeLG().size())+".";
    }
}