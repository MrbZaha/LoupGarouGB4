package Roles;

import joueurBot.Joueur;

public class Villageois extends Personnages{
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=7;


    // Constructeur prend en argument l'id du joueur
    public Villageois(int identifiant){
        super(identifiant, id,"Villageois");}


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        // Les losers ne font rien bref.
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était Villageois!";
    }
}

//// Getters
//public int getId(){
//    return this.id;}