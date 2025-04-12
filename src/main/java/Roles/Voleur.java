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
        if (cible==null) {return;}                                // Si personne n'est choisi, par sécurité
        Personnages tempPerso = joueurActuel.getPerso();      // Capture temporaire du rôle du joueur actuel

        joueurActuel.getPerso().setNom(cible.getPerso().getNom()); // Permet de ne pas tromper la voyante
        cible.getPerso().setNom(tempPerso.getNom());

        Runnable action = () -> {                                 // On stocke l'action dans un Runnable sans l'exécuter de suite

            joueurActuel.setPerso(cible.getPerso().getIdPerso()); // Changement des rôles et initiation d'un nouveau
            cible.setPerso(tempPerso.getIdPerso());

            if (joueurActuel.getPerso().getIdPerso()==Plateau.idLG) { // Si le voleur s'est changé en loup garou
                Jeu.setListeVillageois(-1, joueurActuel); // On le retire de la liste des villageois...
                Jeu.setListeVillageois(1, cible);         // Et on y ajoute sa cible

                Jeu.setListeLG(1, joueurActuel);          // On l'ajoute à la liste de LG...
                Jeu.setListeLG(-1, cible);                // Et on en retire sa cible
            }
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