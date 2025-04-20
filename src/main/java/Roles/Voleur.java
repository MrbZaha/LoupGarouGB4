package Roles;

import DerouleJeu.Jeu;
import DerouleJeu.Plateau;
import joueurBot.Joueur;

public class Voleur extends Personnages{
    //Avoir une variable de classe qui donne un indice à chaque personnage
    private static final int id=1;


    // Constructeur prend en argument l'id du joueur
    public Voleur(int identifiant){
        super(identifiant, id,"Voleur");
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    @Override
    public void actionNuit(Joueur joueurActuel, Joueur cible) {
        if (cible==null) {return;}                                 // Si personne n'est choisi, par sécurité

        // J'ai utilisé la correction automatique d'intelliJ pour que ces 2 variables fonctionnent ici
        final Personnages[] roleJoueurActuel = {new Joueur(null, joueurActuel.getPerso().getIdPerso(), null).getPerso()};
        final Personnages[] roleCible = {new Joueur(null, cible.getPerso().getIdPerso(), null).getPerso()};

        joueurActuel.getPerso().setNom(roleCible[0].getNom()); // Permet de ne pas tromper la voyante
        cible.getPerso().setNom(roleJoueurActuel[0].getNom());

        Runnable action = () -> {                                  // On stocke l'action dans un Runnable sans l'exécuter de suite
            joueurActuel.setPerso(roleCible[0].getIdPerso());  // Changement des rôles et initiation d'un nouveau
            cible.setPerso(roleJoueurActuel[0].getIdPerso());

            if (joueurActuel.getPerso().getIdPerso()==Plateau.idLG) { // Si le voleur s'est changé en loup garou
                Jeu.setListeVillageois(-1, joueurActuel);  // On le retire de la liste des villageois...
                Jeu.setListeVillageois(1, cible);          // Et on y ajoute sa cible

                Jeu.setListeLG(1, joueurActuel);           // On l'ajoute à la liste de LG...
                Jeu.setListeLG(-1, cible);                 // Et on en retire sa cible
            }

            roleCible[0] =null;                                    // Afin de ne pas accumuler des joueurs zombies
            roleJoueurActuel[0] = null;
        };

        Jeu.setActionDiffVol(action);
    }

    @Override
    public String revelationJ(Joueur victime) {
        return victime.getNom()+" était le Voleur!";
    }
}

//// Getters
//public int getId(){
//    return this.id;}