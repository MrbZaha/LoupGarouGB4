package Roles;

import DerouleJeu.Jeu;
import joueurBot.Joueur;

public class Chasseur extends Personnages {
    // Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=6;

    private static Joueur cibleChasseur = null;

    // Constructeur prend en argument l'id du joueur
    public Chasseur(int identifiant){
        super(identifiant, id,"Chasseur");
    }

    // Getter
    public static Joueur getCibleChasseur() {
        return cibleChasseur;
    }

    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    @Override
    public void actionJour(Joueur joueurActuel , Joueur cible) {
        // Lorsqu'il est tué, il peut emporter un autre joueur avec lui
        cible.setIsAlive(false);
        Jeu.retirerDuJeu(cible);
        cibleChasseur = cible;
    }

    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        //Pas d'action pour le Chasseur durant la nuit
    }

    // Renvoie une phrase quand le joueur a été tué
    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était le Chasseur!";
    }

}

//    // Getters
//    public int getId(){
//        return this.id;}