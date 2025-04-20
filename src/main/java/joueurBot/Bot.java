package joueurBot;

import DerouleJeu.Jeu;
import DerouleJeu.Plateau;
import Exceptions.Exceptions;
import Phrases.UsineAPhrases;
import Roles.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bot {
    // Variables
    private Joueur joueur;          // Le bot doit pouvoir créer un objet joueur
    private Map<Joueur, Integer> confiance = new HashMap<>(); // Associe chaque joueur (via index) à une confiance

    private Random random = new Random();

    private final boolean ontConfiance = true;
    private final boolean pasConfiance = false;

    // Constructeur, sert surtout à créer l'objet Joueur
    public Bot() {
        }


    //Setters
    public void setConfiance(Joueur joueur, int changement) {
        if (!confiance.containsKey(joueur)) return;   //Si le joueur n'existe pas dans la liste

        int nouvelleValeur = Math.max(0, Math.min(10, confiance.get(joueur) + changement)); //Crée la nouvelle valeur en restant entre 0 et 10
        confiance.put(joueur, nouvelleValeur); }

    public void setJoueur(Joueur joueurRattache){
        this.joueur=joueurRattache; }


    //Getters
    public Map<Joueur, Integer> getConfiance() {
        return confiance;
    }

    public Joueur getJoueur() {
        return joueur; }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... Fonction du Bot ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    // On initialise les niveaux de confiance de l'entité
    // Si la confiance est à 0, l'entité tentera par tous les moyens de vous tuer
    // Si la confiance est à 10, l'entité vous laissera tranquille et votera pour vous en tant que maire
    // À la fin de chaque tour, la confiance pour chaque personnage diminue de 1 (si possible)

    public void initialiserConfiance() {
        for (Joueur parcoursJ : Jeu.getListeJoueurs()) {
            if (parcoursJ.getBotRattache() != this) {
                int conf = random.nextInt(4)+5;                   // La confiance est aléatoirement comprise entre 3 et 7 entres eux
                confiance.put(parcoursJ, conf);    // Valeur neutre
            }
        }
    }

    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... Fonctions Rôles ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    public void actionDuBot(int idRole){
        switch(idRole){
            case 0 : cupiBot(Jeu.getListeJoueurs());break;
            case 1 : volBot(Jeu.getListeJoueurs()); break;
            case 2 : voitBot(Jeu.getListeJoueurs()); break;
            case 3 : LGBot(Jeu.getListeVillageois()); break;
            case 4 : PFBot(); break;
            case 5 : sortBot(Jeu.getListeJoueurs()); break;
            case 6 : chasseBot(); break;
            default: break;
        }
    }

    public void cupiBot(ArrayList<Joueur> listeJoueurs) {        // Action de Cupidon en tant que Bot
        int index1 = random.nextInt(listeJoueurs.size());        // Tirer le premier joueur

        // Tirer le deuxième joueur (différent du premier)
        int index2;
        do {
            index2 = random.nextInt(listeJoueurs.size());
        } while (index2 == index1);

        Joueur amourUn = listeJoueurs.get(index1);
        Joueur amourDeux = listeJoueurs.get(index2);

        // Lancer l'action de Cupidon
        this.joueur.getPerso().actionNuit(amourUn,amourDeux);

        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(1200);

        // Si le joueur fait partie des amoureux
        if (amourUn.getIdJoueur()==0 || amourDeux.getIdJoueur()==0){
            System.out.println(" "+amourUn.getNom() + " et " + amourDeux.getNom() + " sont désormais amoureux !");
        } Exceptions.sleepJeu(1000);


        System.out.println(" Cupidon se rendort...\n");
    }

    public void volBot(ArrayList<Joueur> listeJoueurs){
        if (!this.joueur.getIsAlive()) {
            return;
        }

        // Tirer le joueur dont on échange la carte
        int index = random.nextInt(listeJoueurs.size());

        while (listeJoueurs.get(index)==this.joueur){          // Tant que le joueur choisi est soi-même
            index = random.nextInt(listeJoueurs.size()); }

        Joueur cible = listeJoueurs.get(index);

        // Lancer l'action du Voleur
        this.joueur.getPerso().actionNuit(this.joueur,cible);

        System.out.println("\nLe Voleur se réveille");
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(1200);

        // Si le joueur a eu sa carte changé
//        if (cible.getIdJoueur()==0){
//            System.out.println("Votre rôle a changé, vous êtes désormais Voleur.");
//        }
        // Peut-être à rédiger au démarrage du jour
        System.out.println(" Le Voleur se rendort...\n");

        setConfiance(cible,+10);                   // L'exVoleur a totalement confiance en la personne dont il a changé la carte
    }

    public void voitBot(ArrayList<Joueur> listeJoueurs){
        if (!this.joueur.getIsAlive()) {
            return;
        }

        // Tirer le joueur dont on observe la carte
        int index = random.nextInt(listeJoueurs.size());

        while (listeJoueurs.get(index)==this.joueur) {       // Tant que le joueur choisi est soi-même
            index = random.nextInt(listeJoueurs.size()); }

        Joueur cible = listeJoueurs.get(index);


        // Lancer l'action de la Voyante
        this.joueur.getPerso().actionNuit(cible,null);

        if(Voyante.getRevelation().getPerso().getNom().equalsIgnoreCase("Loup Garou")){
            setConfiance(cible,-10);                 // La Voyante perd toute confiance en les loup-garous
        } else {
            setConfiance(cible,+10);                 // La Voyante gagne confiance en les joueurs villageois
        }

        System.out.println("\nLa Voyante se réveille");
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(1200);
        System.out.println(" La Voyante se rendort...\n");
    }

    public void LGBot(ArrayList<Joueur> listeJoueurs){
        if (!this.joueur.getIsAlive()) {
            return;
        }

        // Lui il va être chiant, un seul doit jouer
        // Tirer le joueur qu'on va tuer
        int chance = random.nextInt(5);
        if (chance!=0){                                        // Il y a une chance sur 5 que les loups décident de ne pas tuer
            int index = random.nextInt(listeJoueurs.size());

            while (listeJoueurs.get(index)==this.joueur) {     // Tant que le joueur choisi est soi-même
                index = random.nextInt(listeJoueurs.size()); }

            Joueur cible = listeJoueurs.get(index);
            this.joueur.getPerso().actionNuit(this.joueur, cible);}

        System.out.println("\nLes Loups Garous se réveillent");
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(1200);
        System.out.println(" Les Loups Garous se rendorment...\n");
    }

    public void PFBot(){
        if (!this.joueur.getIsAlive()) {                      // Peut-être pas utile, à retirer
            return;
        }

        this.joueur.getPerso().actionNuit(null, null);
        if(PetiteFille.getJoueurObserve()==null){             // Si elle n'a rien vu
        } else {
            setConfiance(PetiteFille.getJoueurObserve(),-10); // Elle perd toute confiance en la personne qu'elle a vu
            if (Plateau.getGrosBG().getPerso().getNom().equals("Loup Garou")){  // Si le joueur est un loup garou
                System.out.println("Il semble que des yeux enfantins vous ont remarqué-e.");
                Exceptions.sleepJeu(2000);
            }
        }
    }

    // Cette fonction a été créée avec l'aide de ChatGPT
    public void sortBot(ArrayList<Joueur> listeJoueurs){
        // On vérifie les potions disponibles
        ArrayList<String> actionsPossibles = new ArrayList<>();

        if (Sorciere.getPotionVie() > 0 && !Jeu.getMortsDuSoir().isEmpty()) {
            actionsPossibles.add("sauver"); }
        if (Sorciere.getPotionMort() > 0) {
            actionsPossibles.add("tuer"); }
        actionsPossibles.add("rien");

        String choix = actionsPossibles.get(random.nextInt(actionsPossibles.size()));

        switch (choix) {
            case "sauver":
                Sorciere.setChoixVieMort(1);
                this.joueur.getPerso().actionNuit(Jeu.getMortsDuSoir().get(0), null);  // Ne prend pas en compte qu'il y ait personne
                this.setConfiance(Jeu.getMortsDuSoir().get(0), +10);        // Elle gagne confiance en la personne choisie par les LG
                break;
            case "tuer":
                Joueur cible = choisirCible(pasConfiance);
                while (!Jeu.getMortsDuSoir().isEmpty() && cible == Jeu.getMortsDuSoir().get(0)) { // Si sorcière va choisir même personnage 2 fois
                    cible=Jeu.getListeJoueurs().get(random.nextInt(Jeu.getListeJoueurs().size()));    // On en choisit un au hasard
                }

                if (cible != null) {
                    Sorciere.setChoixVieMort(2);
                    this.joueur.getPerso().actionNuit(cible, null);
                } else {
                    Sorciere.setChoixVieMort(0);
                }
                break;
            case "rien":
            default:
                Sorciere.setChoixVieMort(0);
                break;
        }

        System.out.println("\nLa Sorcière se réveille");
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(1200);
        System.out.println(" La Sorcière se rendort...\n");
    }

    public void chasseBot(){
        if (this.joueur.getIsAlive()) {
            return;
        }

        Joueur cible = choisirCible(pasConfiance);
        this.joueur.getPerso().actionJour(this.joueur, cible);        // Le chasseur entre en jeu
        System.out.println(this.joueur.getNom()+" a emporté "+cible.getNom()+" avec ellui dans la tombe!");
        System.out.println(cible.getPerso().revelationJ(cible));
    }

    public void voteBot(boolean confiant){
        Joueur cible=choisirCible(confiant);

        // Initialise à 0 si la cible n'existe pas encore
        // Jeu.getVote().putIfAbsent(cible, 0);

        // Si le joueur est maire, son vote compte double
        if (Jeu.getMaire()==this.joueur){
            Jeu.getVote().put(cible,Jeu.getVote().get(cible)+2);
        } else {
            Jeu.getVote().put(cible,Jeu.getVote().get(cible)+1);
        }
    }





    private Joueur choisirCible(boolean confiant) {              // Choisissent une cible en laquelle ils ont confiance(true) ou non(false)
        Joueur plusMoinsFiable = null;
        int confianceMin = Integer.MAX_VALUE;
        int confianceMax = -1;

        if (confiant) {                                          // Si on choisit un joueur pour lequel on a confiance (maire)
            for (Map.Entry<Joueur, Integer> entry : confiance.entrySet()) {
                Joueur j = entry.getKey();
                int confianceJ = entry.getValue();

                if (j.getIsAlive() && confianceJ > confianceMax) {  // Choisi le joueur à la plus grande confiance
                    confianceMax = confianceJ;
                    plusMoinsFiable = j;
                }
            }
        } else {                                                 // Si on choisit un sacrifice
            for (Map.Entry<Joueur, Integer> entry : confiance.entrySet()) {
                Joueur j = entry.getKey();
                int confianceJ = entry.getValue();

                if (this.joueur.getIsLove()) {
                    if (j.getIsAlive() && confianceJ < confianceMin && !j.getIsLove()) {  // Choisi le joueur à la plus petite confiance qui n'est pas l'amoureux
                        confianceMin = confianceJ;
                        plusMoinsFiable = j;
                    }
                } else if (j.getIsAlive() && confianceJ < confianceMin) {  // Choisi le joueur à la plus petite confiance
                    confianceMin = confianceJ;
                    plusMoinsFiable = j;
                }
            }
        }
        return plusMoinsFiable;                                     // Retourner le joueur à la plus petite confiance
    }

    public String messBot() {              // En fonction de son rôle, le bot renvoit un message particulier
        // La petite fille, la voyante, le loup et quelques autres personnages pourront parler durant un tour de parole
        String phrase;
        double probaTypePhrase;

        switch(this.joueur.getPerso().getIdPerso()){
            case Jeu.idLG :
                // Choisit un joueur aléatoire dans la liste des villageois
                int cible = random.nextInt(Jeu.getListeVillageois().size());
                this.joueur.setJoueurDiscussion(Jeu.getListeVillageois().get(cible));

                probaTypePhrase = random.nextDouble();
                int typePhrase;

                // Parle en mal 70% du temps, dit qu'on peut lui faire confiance 20% du temps et n'importe quoi 10% du temps
                if (probaTypePhrase < 0.7) typePhrase=UsineAPhrases.phraseDeMefiance;
                else if (probaTypePhrase < 0.9) typePhrase=UsineAPhrases.phraseDeConfiance;
                else typePhrase=UsineAPhrases.phraseAleatoire;

                // On return la phrase pour le LG
                phrase = UsineAPhrases.getPhrase(typePhrase);
                return phrase.replace("%cible%", this.joueur.getJoueurDiscussion().getNom());

            case Jeu.idPETITEFILLE :
                // Choisit le joueur qu'elle a vu... Si elle l'a vu et qu'il est vivant
                if (PetiteFille.getJoueurObserve()==null) {
                    return null;
                } else {
                    if (PetiteFille.getJoueurObserve().getIsAlive()) {    // S'il est encore vivant
                        phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseDeMefiance);     // Elle l'accuse

                        this.joueur.setJoueurDiscussion(PetiteFille.getJoueurObserve());

                        return phrase.replace("%cible%", PetiteFille.getJoueurObserve().getNom());
                    } else {                                              // Mais s'il est mort
                        phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseAleatoire);     // Elle ne dit rien d'utile
                        return phrase;
                    }
                }

            case Jeu.idVOYANTE :
                // Choisit le joueur qu'elle a vu... Si elle l'a vu (changement de rôle) et qu'il est vivant
                if (Voyante.getRevelation()==null) {
                    return null;
                } else {
                    if (Voyante.getRevelation().getIsAlive()) {    // S'il est encore vivant
                        this.joueur.setJoueurDiscussion(Voyante.getRevelation());
                        if (Voyante.getRevelation().getPerso().getNom().equalsIgnoreCase("Loup Garou")) {
                            phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseDeMefiance);     // Elle l'accuse
                            return phrase.replace("%cible%", Voyante.getRevelation().getNom());
                        } else {
                            phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseDeConfiance);     // Elle en dit du bien
                            return phrase.replace("%cible%", Voyante.getRevelation().getNom());
                        }
                    } else {                                                  // Mais s'il est mort
                        phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseAleatoire);         // Elle ne dit rien d'utile
                        return phrase;
                    }
                }

            default :                                                         // Si c'est un joueur random
                double probaParle = random.nextDouble();
                if (probaParle < 0.5) return null;                           // 50% chance de se taire

                boolean confPasConf = random.nextBoolean();

                Joueur cibleVillageois = choisirCible(confPasConf);
                if (cibleVillageois == null) return null;

                this.joueur.setJoueurDiscussion(cibleVillageois);

                probaTypePhrase = random.nextDouble();
                if (probaTypePhrase < 0.7) {                                  // 70% de chance de dire n'importe quoi
                    phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseAleatoire);
                } else if (confPasConf) {                                     // S'il avait confiance en le villageois
                    phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseDeConfiance);
                } else {
                    phrase = UsineAPhrases.getPhrase(UsineAPhrases.phraseDeMefiance);
                }

                return phrase.replace("%cible%", cibleVillageois.getNom());
        }
    }

    // Cette fonction a été écrite en partie par ChatGPT
    public void analyseMessBot(ArrayList<String> messages) {
        // Pour chacun des messages de confiance ou méfiance, on vérifie quel joueur a été pointé du doigt
        for (String msg : messages) {
            String[] parts = msg.split(":", 2);
            if (parts.length < 2) continue;                               // Si on a bien 2 parties de message

            String nomParlant = parts[0];                                 // On récupère le joueur qui a parlé
            String contenu = parts[1];                                    // Ainsi que sa phrase

            Joueur parlant = getJoueurDepuisNom(nomParlant);
            if (parlant == null || parlant == this.joueur)
                continue;      // Si parlant est le bot actuel ou n'existe pas

            Joueur accuse = parlant.getJoueurDiscussion();

            // Si le bot fait confiance au parlant, il ajuste sa confiance envers la cible du parlant
            if (getConfiance().get(parlant)<4){                   // Si on ne fait vraiment pas confiance au parlant
                // On n'interprète pas ses paroles car elles n'en valent pas la peine
            } else {                                              // Si on fait confiance, la confiance change
                if (contenu.contains("%m%")) {
                    // méfiance
                    this.setConfiance(accuse, -1);
                } else if (contenu.contains("%c%")) {
                    // confiance
                    this.setConfiance(accuse, +2);
                }
            }
        }
    }

    public Joueur getJoueurDepuisNom(String nom){                        // On récupère le joueur depuis son nom
        for (Joueur joueur : Jeu.getListeJoueurs()){
            if (joueur.getNom().equalsIgnoreCase(nom.strip())) {
                return joueur;
            }
        }
        return null;                                                    // par mesure de sécurité
    }
}