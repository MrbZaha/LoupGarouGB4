package Roles;

import joueurBot.Joueur;

public abstract class Personnages {
    private int idJoueur;
    private int idPerso;
    private String nom;

    // Constructeur
    protected Personnages(int idJoueurP, int idPersoP, String nom){
        this.idJoueur = idJoueurP;
        this.idPerso = idPersoP;
        this.nom = nom; }

    // Getter
    public int getIdJoueur() {
        return idJoueur; }

    public int getIdPerso() {
        return idPerso; }

    public String getNom() {
        return nom; }

    // Setters
    public void setNom(String newNom) {                      // Pour que la voyante puisse voir les rôles changés par le voleur
        this.nom = newNom;
    }

    // Fonctions
    public String IsKilled(String nomJoueur) {
        //Ajouté une variable pour que précise "au cours du vote" ou "au cours de la nuit" en fonction du moment
        return nomJoueur+"a été tué";}

    public void actionJour(Joueur cible1, Joueur cible2){
        // Seul le chasseur possède une action de jour
    }

    public abstract void actionNuit(Joueur cible1, Joueur cible2);

    public abstract String revelationJ(Joueur victime);

}
