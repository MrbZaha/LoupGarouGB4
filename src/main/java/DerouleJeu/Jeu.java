package DerouleJeu;

import Exceptions.Exceptions;
import Main.Main;
import Phrases.ConsoleText;
import joueurBot.*;
import Roles.*;

import java.util.*;

public class Jeu {
    // Contient les grandes étapes de déroulement du jeu
    public static final int idCUPIDON=0;
    public static final int idVOLEUR=1;
    public static final int idVOYANTE=2;
    public static final int idLG=3;
    public static final int idPETITEFILLE=4;
    public static final int idSORCIERE=5;
    public static final int idCHASSEUR=6;
    public static final int idVILLAGEOIS=7;

    private static Joueur maire=null;                                     // Répertorie le joueur présentant le titre de maire
    private static Joueur[] amoureux;                                     // Répertorie les joueurs touchés par Cupidon

    static Scanner input= new Scanner(System.in);

    private static ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private static ArrayList<Joueur> listeBots=new ArrayList<>();         // Liste des robots qui vont jouer

    private static ArrayList<Joueur> listeLG=new ArrayList<>();           // Stocke les joueurs Loups Garous
    private static ArrayList<Joueur> listeVillageois=new ArrayList<>();   // Stocke les joueurs non Loup Garous

    private static ArrayList<Joueur> mortsDuSoir=new ArrayList<>();       // On stocke le/les individus qui meurent dans la soirée
    private static ArrayList<Joueur> mortSauve=new ArrayList<>();         // Si la sorcière sauve quelqu'un

    private static Set<Integer> rolesDejaJoues = new HashSet<>();         // Permet d'éviter qu'un même rôle ne soit joué plusieurs fois dans la même nuit (comme le Voleur...)

    private static Map<Joueur, Integer> vote = new HashMap<>();       // Pour stocker les votes de chaque joueur

    private static boolean victoireLoup=false;
    private static boolean victoireVillageois=false;

    private static final Joueur grosBG =Plateau.getGrosBG();

    private static Runnable actionDiffVol = null;                    // Stock d'une action différée pour que le voleur ne dérègle pas le jeu

    private static ArrayList<String> listeMessagesVillages = new ArrayList<String>();

    private static int checkMortUneFois=0;



    // Setters
    public static void setAmoureux(Joueur[] amoureux) {
        Jeu.amoureux = amoureux;}

    public static void setListeLG(int addRem, Joueur loup){
        if (addRem<0){
            Jeu.listeLG.remove(loup);
        }
        else{
            Jeu.listeLG.add(loup); }}

    public static void setListeVillageois(int addRem, Joueur villageois){
        if (addRem<0){
            Jeu.listeVillageois.remove(villageois);
        }
        else{
            Jeu.listeVillageois.add(villageois); }}

    public static void setMortsDuSoir(Joueur mortDuSoir) {
        Jeu.mortsDuSoir.add(mortDuSoir); }

    public static void setMortSauve(Joueur mortSauve) {
        Jeu.mortSauve.add(mortSauve);
    }

    public static void setActionDiffVol(Runnable actionDiffVol) {
        Jeu.actionDiffVol = actionDiffVol;
    }



    // Getters
    public static Joueur getMaire() {
        return maire; }

    public static ArrayList<Joueur> getListeLG() {
        return Jeu.listeLG; }

    public static ArrayList<Joueur> getListeVillageois() {
        return Jeu.listeVillageois; }

    public static ArrayList<Joueur> getMortsDuSoir() {
        return Jeu.mortsDuSoir; }

    public static boolean getVictoireLoup() {
        return victoireLoup; }

    public static boolean getVictoireVillageois() {
        return victoireVillageois; }

    public static ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs; }

    public static ArrayList<Joueur> getListeBots(){
        return listeBots; }

    public static Map<Joueur, Integer> getVote(){
        return vote;
    }



    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ... Manipulations de listes ... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    // Ajouter un joueur et son rôle
    public static void ajouterJoueur(Joueur joueur) {
        listeJoueurs.add(joueur);
    }

    public static void afficherJoueur(boolean removeMe){
        System.out.print("|| ");
        if (removeMe){
            for (int i=0; i<listeJoueurs.size(); i++){
                if (listeJoueurs.get(i) == grosBG){
                    // On ne fait rien
                    }
                else{
                System.out.print(ConsoleText.CYAN_BOLD+listeJoueurs.get(i).getNom()+ConsoleText.RESET+" || ");}
            }}
        else {
            for (int i=0; i<listeJoueurs.size(); i++){
                System.out.print(ConsoleText.CYAN_BOLD+listeJoueurs.get(i).getNom()+ConsoleText.RESET+" || ");}
            }
        }

    public static void initialiserVotes(ArrayList<Joueur> listeJoueurs) {
        vote.clear();                                                     // Au cas où la map contenait déjà des valeurs
        for (Joueur joueur : listeJoueurs) {
            if (joueur.getIsAlive()) {
                vote.put(joueur, 0);
            }
        }
    }

    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......      Tours      ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //



    public static void tourPreliminaire(){
        // Ce tour n'arrive qu'une unique fois, lors de la première nuit
        // Cupidon entre en jeu, les amoureux se réveillent et se découvrent
        System.out.println("\nLa première nuit est tombée, l'amour flotte dans l'air... Cupidon se réveille.");

        if (grosBG.getPerso().getIdPerso()==Plateau.idCUPIDON){           // Si on est le Cupidon
            grosBG.actionDuJoueur(grosBG.getPerso().getIdPerso());
        } else {
            for (Joueur B:listeBots){                                     // On cherche parmi notre liste de Bots
                if (B.getPerso().getIdPerso()==Plateau.idCUPIDON){
                    B.getBotRattache().cupiBot(listeJoueurs);
                    break;
                }
            }
        }

        for (Joueur B : listeBots){
            B.getBotRattache().initialiserConfiance();                   // J'initialise la confiance des bots entres eux + joueur
        }
    }

    public static void tourDeJeuJour(){
        // Préciser au joueur son rôle
        Exceptions.sleepJeu(2000);
        if (grosBG.getIsAlive()){
            System.out.println(ConsoleText.YELLOW_BOLD+"\nLors de ce tour, votre rôle sera le suivant : "+grosBG.getPerso().getNom()+ConsoleText.RESET);
            Exceptions.sleepJeu(2000);
        }

        // Vérifier si une des équipes a gagné
        boolean finDuJeu = verifFinDuJeu(); if (finDuJeu) {return;}

        // Annoncer les morts
        if(mortsDuSoir.isEmpty()){
            System.out.println("\nFort heureusement, aucun-e mort-e n'est à déplorer en ce jour.");
            Exceptions.sleepJeu(2000);
            System.out.println("Mais il ne faut pas nous relâcher, continuez à enquêter.");
            Exceptions.sleepJeu(2000);
        } else {
            System.out.print("\nNous comptons "+mortsDuSoir.size()+" mort-e-s pour cette nuit : ");
            for (Joueur i : mortsDuSoir){
                System.out.print(i.getNom()+"    "); }
            System.out.println(); Exceptions.sleepJeu(4000);
        }

        // Annoncer les revenants s'il y en a
        if (mortSauve.isEmpty()){
            // On ne dit rien
        } else {
            System.out.println("\nCependant, "+mortSauve.get(0).getNom()+" a pu être sauvé ce soir!\n");
            Exceptions.sleepJeu(2000);
            mortsDuSoir.remove(mortSauve.get(0));
        }

        // Annoncer si un d'entres eux était amoureux
        checkAmour();

        // Annoncer le rôle des vrais morts + mettre à jour les liste LG, villageois, joueurs, le maire, liste vote et confiance
        for (Joueur mort : mortsDuSoir){
            System.out.println();

            // Faire entrer en jeu le chasseur
            checkChasseur(mort);

            retirerDuJeu(mort);

            System.out.println(mort.getPerso().revelationJ(mort));
            Exceptions.sleepJeu(1000);
        }

        // Mettre à jour la confiance des bots pour retirer la confiance qu'ils ont envers les morts
        for (Joueur bot : listeBots){
            for (Joueur mort : mortsDuSoir){
                bot.getBotRattache().getConfiance().remove(mort);
            }
        }

        // Vérifier si une des équipes a gagné
        finDuJeu = verifFinDuJeu(); if (finDuJeu) {return;}

        checkJoueurMort();

        if(maire!=null){
            if(!maire.getIsAlive()){
                maire=null;
            }
        }

        // Réinitialiser mortDuSoir, mortSauvé
        mortsDuSoir.clear();
        mortSauve.clear();

        // Petit instant discussion
        System.out.println("""
                        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
                        // --- --- --- --- --- --------------- ......  Instant parlotte ...... --------------- --- --- --- --- --- //
                        // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
                """);

        // D'abord tous les bots y passent
        for (Joueur bot : listeBots) {
            String message = bot.getBotRattache().messBot();
            if (message != null) {
                if (!message.contains("%r%")) {
                    listeMessagesVillages.add(bot.getNom()+" : "+message);
                    System.out.println(ConsoleText.CYAN_BOLD+bot.getNom()+ConsoleText.RESET+" : "+message.replaceAll("%[a-z]%", ""));
                    Exceptions.sleepJeu(2000);
                } else {
                    System.out.println(ConsoleText.CYAN_BOLD+bot.getNom()+ConsoleText.RESET+" : "+message.replaceAll("%[a-z]%", ""));
                    Exceptions.sleepJeu(2000);
                }
            }
        }

        // puis le joueur
        if (grosBG.getIsAlive()) {
            String messageJoueur = grosBG.messJoueur();
            if (messageJoueur!=null) {
                listeMessagesVillages.add(messageJoueur);                                           // On l'ajoute à la liste des phrases à vérifier
                System.out.println(messageJoueur.replaceAll("%[a-z]%", ""));      // Aidé par ChatGPT
            }
        }

        // pour chaque phrase du type, les autres bots doivent avoir leur confiance modifiée
        for ( Joueur bot : listeBots) {
            bot.getBotRattache().analyseMessBot(listeMessagesVillages);
        }

        // La liste contenant toutes les phrases doit être réinitialisée
        listeMessagesVillages.clear();

        // élire un maire s'il n'y a plus
        if (Jeu.maire==null){
            System.out.println("\nIl semble que nous manquions d'un maire pour nous aider dans nos décisions.");
            System.out.println("Qui souhaitez vous élire?");
            // Élire un maire
            Joueur votee=voteGeneral();
            maire=votee;                                 // On élit le maire
            System.out.println();
            System.out.println("Vous avez donc élu "+votee.getNom()+" en tant que maire. Les péripéties peuvent reprendre.");
            Exceptions.sleepJeu(2000);

            // Le maire élu gagne en confiance chez le reste des gens
            for (Joueur bot : listeBots){
                bot.getBotRattache().setConfiance(votee, +2);   // Les joueurs gagnent en confiance pour le maire
            }
        }

        // effectuer Vote du sacrifice;
        System.out.println("\nNous pouvons désormais passer au vote du village.");
        System.out.println("Afin d'éliminer la vermine qui se cache parmi nous, nous vous demanderons de voter pour la personne\n"
        +"  que vous souhaitez éliminer aujourd'hui.");
        Joueur votee=voteGeneral();

        System.out.println();
        System.out.println("Le village a voté... Et le village a choisi "+votee.getNom()+".\n");
        Exceptions.sleepJeu(2000);

        // Set isAlive en false et le retirer de la liste des joueurs, préciser son rôle pour vote de mort
        votee.setIsAlive(false);

        // Vérifier si joueur était un chasseur
        checkChasseur(votee);

        retirerDuJeu(votee);

        // Après l'avoir retiré du jeu, on révèle son rôle (pour pas avoir de pb avec l'affichage du nombre de lg restants)
        System.out.println(votee.getPerso().revelationJ(votee));

        // Checker si les 2 amoureux sont toujours vivants
        checkAmour();

        // Si le chasseur a joué durant ce tour
        if (Chasseur.getCibleChasseur()!=null) {                                    // Si le chasseur a joué ce soir
            retirerDuJeu(Chasseur.getCibleChasseur());
        }

        // Revérifier si une des équipes a gagné
        finDuJeu = verifFinDuJeu(); if (finDuJeu) {return;}

        checkJoueurMort();

        // La confiance de tous les bots les uns pour les autres descend de un
        for (Joueur bot : listeBots){
            for (Map.Entry<Joueur, Integer> lesAutres : bot.getBotRattache().getConfiance().entrySet()){
                bot.getBotRattache().setConfiance(lesAutres.getKey(), -1);
            }
        }
    }

    // Lance toutes les actions de nuit à effectuer
    public static void tourDeJeuNuit(){
        // Pour l'ensemble des joueurs en vie, lancera leur actionNuit
        for (int indexPerso = idVOLEUR ; indexPerso < idCHASSEUR ; indexPerso++) { // On fait jouer tous les joueurs entre le voleur et chasseur non compris
            if (indexPerso==idLG) {                      // Les LG sont plusieurs, donc il faut préciser certaines conditions
                // On regarde s’il y a un joueur humain parmi eux
                boolean joueurHumain = listeLG.stream().anyMatch(j -> j == grosBG);

                if (joueurHumain) {
                    grosBG.actionDuJoueur(idLG);    // C'est lui qui s'occupe du choix de la nuit
                } else {
                    // Tous les LG sont des bots, on n'en fait jouer qu'un
                    Joueur botQuiJoue = listeLG.get(0);
                    botQuiJoue.getBotRattache().actionDuBot(idLG);
                }
            } else if (!rolesDejaJoues.contains(indexPerso)) { // Pour les autres rôles
                for (Joueur joueur : listeJoueurs) {
                    if (joueur.getPerso().getIdPerso() == indexPerso) {
                        if (joueur == grosBG) {
                            joueur.actionDuJoueur(indexPerso);
                        } else {
                            joueur.getBotRattache().actionDuBot(indexPerso);
                        }

                        rolesDejaJoues.add(indexPerso);       // On stocke les rôles déjà joués
                        break; // On sort de la boucle interne, car on ne veut qu’UN joueur de ce rôle
                    }
                }
            }
        }

        // L'idée d'utiliser une liste de Runnable a été offerte par ChatGPT
        if (actionDiffVol!=null) {                            // On effectue l'action du Voleur
            actionDiffVol.run();
            actionDiffVol=null;                               // On reset pour la prochaine nuit
        }
        rolesDejaJoues.clear();                               // On vide la liste pour la nuit suivante
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......     Méthodes    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //


    public static boolean verifFinDuJeu(){
        if (listeLG.size()>listeVillageois.size()){           // S'il y a plus de LG que de villageois
            victoireLoup=true;
            return true;
        } else if(listeLG.isEmpty()){                         // S'il n'y a plus de LG
            victoireVillageois=true;
            return true;
        } else if (Plateau.getSecretEnding()){                // Une fin secrète???
            return true;
        } else {
            return false;
        }
    }

    public static Joueur voteGeneral(){
        // (Ré)initialisation de la liste de votes
        initialiserVotes(listeJoueurs);

        for (Joueur joueur : listeJoueurs) {
            if (joueur == grosBG) {
                joueur.voteJoueur();                                     // Le joueur vote
            } else {
                if (maire==null){
                    joueur.getBotRattache().voteBot(true);       // Le bot vote pour la personne en laquelle il a confiance
                } else {
                    joueur.getBotRattache().voteBot(false);      // Le bot vote pour la personne en laquelle il n'a pas confiance
                }
            }
        }
        // Observation de la liste des votes, on récupère ceux au plus grand vote
        System.out.println("\nVoici le résultat des votes : ");
        System.out.print("|| ");
        for (Map.Entry<Joueur, Integer> entree : vote.entrySet()){
            System.out.print(entree.getKey().getNom()+" : "+entree.getValue()+" || ");
        } Exceptions.sleepJeu(3000);

        // Récupérer ceux/celui au plus grand vote
        int maxVotes = 0;
        List<Joueur> joueursMaxVotes = new ArrayList<>();
        for (Map.Entry<Joueur, Integer> entry:vote.entrySet()) {
            int nbVotes=entry.getValue();
            Joueur joueur=entry.getKey();

            if (nbVotes > maxVotes) {
                maxVotes = nbVotes;
                joueursMaxVotes.clear();                            // On redémarre la liste
                joueursMaxVotes.add(joueur);                        // On récupère le joueur au plus haut score de vote
            } else if (nbVotes == maxVotes) {
                joueursMaxVotes.add(joueur);                        // On ajoute un nouveau joueur au plus haut score de vote
            }
        }

        // Retourner le joueur pour lequel les joueurs on voté
        if (joueursMaxVotes.size() == 1) {
            return joueursMaxVotes.get(0);
        } else {
            Random rand = new Random();
            Joueur votee = joueursMaxVotes.get(rand.nextInt(joueursMaxVotes.size()));
            System.out.println();
            System.out.print("Un tirage au sort est lancé pour connaître le candidat du vote");
            System.out.print("."); Exceptions.sleepJeu(800);
            System.out.print("."); Exceptions.sleepJeu(800);
            System.out.print("."); Exceptions.sleepJeu(1200);
            System.out.println(" Le sort a voté pour "+votee.getNom()+" !"); Exceptions.sleepJeu(1200);
            return votee;
        }

        // Faire un petit jeu pour choisir, au hasard ou bien refaire un vote,

    }

    public static void checkAmour(){
        if (amoureux!=null){                                                           // Si on compte encore des amoureux
            for (Joueur amour : amoureux){
                if (!amour.getIsAlive()){
                    Joueur partenaire=(amoureux[0]==amour) ? amoureux[1]:amoureux[0];  // On cherche son-sa partenaire

                    if (partenaire.getIsAlive()) {                                     // Si le-la partenaire est encore vivant-e
                        partenaire.setIsAlive(false);
                        // Jeu.setMortsDuSoir(partenaire);                                // On l'ajoute à la liste des mort-e-s dans la soirée
                        System.out.println(partenaire.getNom()+", partenaire de "+amour.getNom()+" a décidé de le-la rejoindre dans la mort");

                        checkChasseur(partenaire);
                        retirerDuJeu(partenaire);                                      // On retire partenaire du jeu

                        System.out.println(partenaire.getPerso().revelationJ(partenaire));



                    } else {
                        System.out.println(amour.getNom() + " et " + partenaire.getNom() + " étaient amoureux-euse l'un-e de l'autre.");
                    }

                    amoureux = null;                                                   // On les retire de la liste des amoureux
                    break;
                }
            }
        }
    }

    public static void checkChasseur(Joueur votee) {                                   // Vérifier si joueur était un chasseur
        if (votee.getPerso().getNom().equals("Chasseur")){
            if(votee==grosBG){                                                         // Si le joueur était chasseur
                votee.actionDuJoueur(votee.getPerso().getIdPerso());
            } else {
                votee.getBotRattache().actionDuBot(votee.getPerso().getIdPerso());
            }
        }
    }

    //Regarde si le joueur est mort et lui propose de recommencer
    // Cette fonction a été écrite avec l'aide de ChatGPT
    public static void checkJoueurMort() {
        if (!grosBG.getIsAlive() && checkMortUneFois==0) {
            System.out.println(ConsoleText.YELLOW_BOLD+"\nIl semble que soyiez définitivement éliminé du jeu. Souhaitez vous "+ConsoleText.RED_BOLD+"continuer"+ConsoleText.YELLOW_BOLD+" ou "+ConsoleText.RED_BOLD+"recommencer"+ConsoleText.YELLOW_BOLD+" une autre partie?"+ConsoleText.RESET);
            String rep = input.nextLine();
            while (!rep.equalsIgnoreCase("continuer") && !rep.equalsIgnoreCase("recommencer")) {
                System.out.println(ConsoleText.YELLOW_BOLD+"Veuillez rentrer une des valeurs possibles.");
                rep = input.nextLine();
            }

            if (rep.equalsIgnoreCase("recommencer")) {
                System.out.println("Très bien, le jeu va recommencer."+ConsoleText.RESET);
                Exceptions.sleepJeu(1500);

                // Clear le terminal, et les éléments du jeu à réinitialiser.
                listeJoueurs.clear();
                listeBots.clear();
                listeLG.clear();
                listeVillageois.clear();
                mortsDuSoir.clear();
                mortSauve.clear();
                rolesDejaJoues.clear();
                vote.clear();

                // reset de tout ce qu’il faut ici si besoin
                Main.lancerJeu();                                                         // On appelle une nouvelle partie
                return;
            }

            checkMortUneFois++;                                                           // Comme ça on ne demande qu'une fois
        }
    }

    public static void retirerDuJeu(Joueur mort){
        listeLG.remove(mort);
        listeVillageois.remove(mort);
        listeJoueurs.remove(mort);
        listeBots.remove(mort);
        vote.remove(mort);
    }
}