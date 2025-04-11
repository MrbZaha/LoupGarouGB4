package Roles;

import joueurBot.Joueur;

public class Voyante extends Personnages{
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=2;
    private static Joueur revelation=null;


    // Constructeur prend en argument l'id du joueur
    public Voyante(int identifiant){
        super(identifiant, id,"Voyante");
    }


    // Getters
    public static Joueur getRevelation() {
        return revelation;
    }


    // Setters
    public static void setRevelation(Joueur revelation) {
        Voyante.revelation = revelation;
    }

    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    @Override
    public void actionNuit(Joueur cible1, Joueur cible2) {
        setRevelation(cible1);
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était la Voyante!\n";
    }

}