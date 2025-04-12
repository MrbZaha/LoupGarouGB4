package joueurBot;

import DerouleJeu.Jeu;
import DerouleJeu.Plateau;
import Exceptions.Exceptions;
import Exceptions.MauvaisInput;
import Phrases.ConsoleText;
import Roles.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    private String nom;
    private boolean isAlive=true;

    private static Scanner input=new Scanner(System.in);

    private static int nbJoueur=0;     // Permet de définir l'identifiant de chaque joueur
    private int idJoueur;              // Le joueur aura un identifiant, aide pour l'établissement des confiances des bots
                                       // Le vrai joueur aura toujours l'indice 0

    private Personnages perso;

//    private boolean isBot=true;
    private Bot botRattache;

    private Joueur joueurDiscussion;

    //Constructeur
    public Joueur(String nomE, int personnage, Bot bot){
        this.nom=nomE;
        this.idJoueur=nbJoueur;       // Le bot prend un identifiant
        this.nbJoueur+=1;             // À chaque bot créé, on incrémente nbJoueur

        setPerso(personnage);

        this.botRattache=bot;
//        if (bot==null){
//            this.isBot=false;
//        }
    }

    //Setters
    public void setIsAlive(boolean isDead){
        this.isAlive=isDead; }

    public void setId(int id){
        this.idJoueur=id; }

    public void setPerso(int numero){ // On assigne le personnage du joueur
        switch (numero){
            case 0 : this.perso = new Cupidon(this.idJoueur);break;
            case 1 : this.perso = new Voleur(this.idJoueur);break;
            case 2 : this.perso = new Voyante(this.idJoueur);break;
            case 3 : this.perso = new LoupGarou(this.idJoueur);break;
            case 4 : this.perso = new PetiteFille(this.idJoueur);break;
            case 5 : this.perso = new Sorciere(this.idJoueur);break;
            case 6 : this.perso = new Chasseur(this.idJoueur);break;
            case 7 : this.perso = new Villageois(this.idJoueur);break;
            default: this.perso = null; } }

    public void setJoueurDiscussion(Joueur joueurDiscussion) {
        this.joueurDiscussion = joueurDiscussion; }



    // Getters
    public String getNom(){
        return this.nom; }

    public boolean getIsAlive(){
        return this.isAlive; }

    public Personnages getPerso(){
        return this.perso; }

    public int getIdJoueur() {
        return this.idJoueur; }

    public static int getNbJoueur() {
        return Joueur.nbJoueur; }

    public Bot getBotRattache() {
        return botRattache; }

    public Joueur getJoueurDiscussion() {
        return joueurDiscussion;
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    public void actionDuJoueur(int idRole){
        switch(idRole){
            case 0 : cupiJoueur(); break;     // Action du Cupidon
            case 1: volJoueur(); break;      // Action du Voleur
            case 2: voitJoueur(); break;     // Action de la Voyante
            case 3: LGJoueur(); break;       // Action du Loup Garou
            case 4: PFJoueur(); break;       // Action de la Petite Fille
            case 5: sortJoueur(); break;     // Action de la sorcière
            case 6: chasseJoueur(); break;   // Action du Chasseur
            default: break;
        }
    }

    // La création de demanderJoueur et verifierNomJoueur ont été réalisée en partie par ChatGPT, en particulier pour la gestion d'exceptions
    // Permet de choisir un joueur sur lequel effectuer une action
    public static Joueur demanderJoueur(boolean passerTour, boolean removeMe) {    // Méthode pour demander le nom d'un joueur
        System.out.println();              // On revient à la ligne
        while(true){
            try{
                String choixNom=input.nextLine().trim();

                if (passerTour && choixNom.equalsIgnoreCase("Personne")) {
                    System.out.println("Vous avez choisi de ne pas agir ce tour.");
                    return null;
                }

                Joueur joueurChoisi = Joueur.verifierNomJoueur(choixNom, removeMe);
                return joueurChoisi;                                               // Si trouvé dans la liste, on le retourne

            }catch (MauvaisInput e) {
                System.out.println("Erreur : "+e.getMessage()+" Veuillez réessayer.");
            }
        }
    }

    // Permet de choisir une action pour la sorcière
    public static String demanderChoixSorciere() throws MauvaisInput{
        while(true) {
            try{
                String choixOption=input.nextLine();

                while(!choixOption.equalsIgnoreCase("O") && !choixOption.equalsIgnoreCase("X") && !choixOption.equalsIgnoreCase("R")){
                    System.out.println("Veuillez rentrer une des valeurs possibles (o pour sauver | x pour tuer | r pour ne rien faire)");
                    choixOption = input.nextLine();}

                if (choixOption.equalsIgnoreCase("o")) {           // On ignore la casse
                    return "O";                         //On modifie choixVieMort
                } else if(choixOption.equalsIgnoreCase("x")){
                    return "X";
                } else if(choixOption.equalsIgnoreCase("r")){
                    return "R";
                }
            }catch (MauvaisInput e){
                System.out.println("Erreur : "+e.getMessage()+" Veuillez réessayer.");
            }
        }
    }

    // Vérifie si un nom est valide et retourne le joueur correspondant
    public static Joueur verifierNomJoueur(String nom, boolean removeMe) throws MauvaisInput {
        if (removeMe == false) {
            for (Joueur joueur : Jeu.getListeJoueurs()) {
                if (joueur.getNom().equalsIgnoreCase(nom)) {           // On ignore la casse
                    return joueur;
                }
            }
        } else {
            if (Plateau.getGrosBG().getNom().equalsIgnoreCase(nom)){  // Si j'ai rentré mon propre nom
                // Ne rien faire
            } else{
                for (Joueur joueur : Jeu.getListeJoueurs()) {
                    if (joueur.getNom().equalsIgnoreCase(nom)) {      // On ignore la casse
                        return joueur;
                    }
                }
            }
        }
        throw new MauvaisInput("Le nom \""+nom+"\" ne correspond à aucun joueur sur lequel vous pouvez agir!");
    }




    public void cupiJoueur(){
        System.out.println("Qui sera touché de votre flèche cette nuit? (Veuillez rentrez 2 noms parmi la liste, un par un)");
        Jeu.afficherJoueur(false);


        System.out.println();
        System.out.print("Premier-e amoureux-euse : ");
        Joueur amourUn = demanderJoueur(false, false);
        System.out.println();
        System.out.print("Second-e amoureux-euse : ");
        Joueur amourDeux;
        do{amourDeux = demanderJoueur(false, false);
            System.out.println("Veuillez choisir 2 joueurs différents");
        }while (amourUn==amourDeux);

        System.out.println("Vous avez donc uni-e-s "+amourUn.getNom()+" ainsi que "+amourDeux.getNom()+".\n");

        Plateau.getGrosBG().getPerso().actionNuit(amourUn,amourDeux);
    }

    public void volJoueur(){
        if (!this.isAlive) {
            return;
        }

        //Le voleur échange une carte avec la sienne
        System.out.println("\nLe Voleur se réveille.");
        System.out.println("Avec qui souhaitez-vous échanger votre rôle?");
        Jeu.afficherJoueur(true);
        System.out.print("Personne ||");

        Joueur cible = demanderJoueur(true, true);
        if (cible==null){
            System.out.println("Très bien, aucun vol ne sera réalisé ce soir.\n");
        } else {
            Plateau.getGrosBG().getPerso().actionNuit(Plateau.getGrosBG(),cible);
            System.out.println("Vous possédez désormais ce rôle : "+cible.getPerso().getNom());
        }
    }

    public void voitJoueur(){
        if (!this.isAlive) {
            return;
        }

        System.out.println("\nLa Voyante se réveille.");
        System.out.println("De quel joueur souhaitez vous observer le rôle ce soir?");
        Jeu.afficherJoueur(true);
        System.out.print("Personne ||");

        Joueur cible = demanderJoueur(true, true);
        if (cible==null){
            System.out.println("Très bien, vous n'avez découvert le rôle de personne ce soir.\n");
        } else {
            Plateau.getGrosBG().getPerso().actionNuit(cible, null);
            System.out.println(cible.getNom()+" présente ce rôle : "+cible.getPerso().getNom());
        }
    }

    public void LGJoueur(){                  // Action du joueur
        if (!this.isAlive) {
            return;
        }

        System.out.println("\nLes Loups-Garous se réveillent...");
        System.out.print("Les loups sont : ");
        for (Joueur i:Jeu.getListeLG()){
            System.out.print(i.getNom()+" ");
        }
        System.out.println();
        System.out.println("Quel joueur souhaitez-vous tuer ce soir?");
        Jeu.afficherJoueur(true);
        System.out.print("Personne ||");

        Joueur cible = demanderJoueur(true, true);
        while (cible != null && Jeu.getListeLG().contains(cible)) {                      // Tant que la cible est un autre lg
            System.out.println("Vous ne pouvez pas attaquer un autre loup-garou!");
            System.out.print("Veuillez choisir une nouvelle cible.");
            cible = demanderJoueur(true, true);
        }

        if (cible==null){
            System.out.println("Très bien, vous n'avez tué personne ce soir.\n");
        } else {
            Plateau.getGrosBG().getPerso().actionNuit(null, cible);
            System.out.println(cible.getNom()+" a bien été tué ce soir.");
        }
    }

    public void PFJoueur(){                  // Action de la petite fille
        if (!this.isAlive) {
            return;
        }

        System.out.println("\nLa petite fille se réveille...");

        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);
        System.out.print("."); Exceptions.sleepJeu(800);

        Plateau.getGrosBG().getPerso().actionNuit(null, null);
        if(PetiteFille.getJoueurObserve()==null){              // Si elle n'a rien vu
            System.out.println("Vous n'avez rien pu observer cette nuit...\n"); Exceptions.sleepJeu(2000);

        } else {
            System.out.println("Vous avez pu voir "+PetiteFille.getJoueurObserve().getNom()+" se transformer en loup durant la nuit!"); Exceptions.sleepJeu(2000);
        }
    }

    public void sortJoueur(){
        if (!this.isAlive) {
            return;
        }

        System.out.println("\nLa Sorcière se réveille...");
        if (!Jeu.getMortsDuSoir().isEmpty()){
            System.out.println(Jeu.getMortsDuSoir().get(0).getNom()+" risque de mourir ce soir.");
            System.out.println("Voulez vous tuer (x), sauver (O) ou ne rien faire (R) ce soir?");
        } else {
            System.out.println("Personne ne risque de mourir ce soir.");
            System.out.println("Voulez vous tuer (X) ou ne rien faire (R) ce soir?");
        }

        String reponse=demanderChoixSorciere();

        while(reponse.equals("O") && Sorciere.getPotionVie()==0){
            System.out.println("Vous avez déjà utilisée votre potion de vie.");
            System.out.println("Veuillez rentrer une nouvelle valeur.");
            reponse=demanderChoixSorciere();
        }

        while(reponse.equals("X") && Sorciere.getPotionMort()==0){
            System.out.println("Vous avez déjà utilisée votre potion de mort.");
            System.out.println("Veuillez rentrer une nouvelle valeur.");
            reponse=demanderChoixSorciere();
        }

        switch (reponse){
            case "O" :
                if (Sorciere.getPotionVie()==0 && !Jeu.getMortsDuSoir().isEmpty()){
                }
                Sorciere.setChoixVieMort(1);
                Plateau.getGrosBG().getPerso().actionNuit(Jeu.getMortsDuSoir().get(0), null);
                break;
            case "X" :
                Sorciere.setChoixVieMort(2);
                System.out.println("Qui désirez vous tuer?");
                Jeu.afficherJoueur(true);
                Joueur cible=demanderJoueur(false,true);
                while (!Jeu.getMortsDuSoir().isEmpty() && cible == Jeu.getMortsDuSoir().get(0)) {
                    System.out.println("Vous ne pouvez pas tuer un joueur déjà mort, veuillez réessayer :");
                    cible=demanderJoueur(false,true);
                }
                Plateau.getGrosBG().getPerso().actionNuit(cible, null);
                System.out.println("Très bien. Vous avez donc tué "+cible.getNom()+".");
                break;
            case "R" :
                Sorciere.setChoixVieMort(0);
                System.out.println("Très bien. Vous pouvez vous rendormir");
                break;
            default :
                break;
        }
        System.out.println("La Sorcière se rendort...\n");
    }

    public void chasseJoueur(){
        if (!this.isAlive){
            System.out.println("\nQui souhaitez vous emporter avec vous?");
            Jeu.afficherJoueur(true);
            System.out.print("Personne ||");

            Joueur cible=demanderJoueur(false,true);
            System.out.println("\n"+Plateau.getGrosBG().getNom()+" a décidé de tuer "+cible.getNom());
            System.out.println(cible.getPerso().revelationJ(cible));
            Plateau.getGrosBG().getPerso().actionJour(Plateau.getGrosBG(), cible);
        }
    }




    public void voteJoueur(){
        // On demande au joueur de choisir une personne à élire
        Joueur cible;
        if (Jeu.getMaire()==null) {
            Jeu.afficherJoueur(false);
            cible=demanderJoueur(false,false);
        } else {
            Jeu.afficherJoueur(true);
            cible=demanderJoueur(false,true);
        }


        // Si le joueur est maire, son vote compte double
        if (Jeu.getMaire()==this){
            Jeu.getVote().put(cible,Jeu.getVote().get(cible)+2);
        } else {
            Jeu.getVote().put(cible,Jeu.getVote().get(cible)+1);
        }

        // À la fin, si plusieurs joueurs sont égaux en nombre de vote, on lance l'évènement, un deux trois bras de fer chinois??
    }


    public String messJoueur(){
        System.out.println("\nEt vous? Avez vous quelque chose à ajouter, "+ConsoleText.BLUE_BOLD+"\"OUI\""+ConsoleText.RESET+" ou "+ConsoleText.BLUE_BOLD+"\"NON\""+ConsoleText.RESET+"?");

        // Proposition entre oui ou non, faire attention aux erreurs
        String ouiNon = input.nextLine();

        while (!ouiNon.equalsIgnoreCase("OUI") && !ouiNon.equalsIgnoreCase("NON")) {
            System.out.println("Erreur : Veuillez entrer le mot "+ConsoleText.BLUE_BOLD+"\"OUI\""+ConsoleText.RESET+" ou "+ConsoleText.BLUE_BOLD+"\"NON\"");
            ouiNon = input.nextLine();
        }

        if (ouiNon.equalsIgnoreCase("NON")) {                                 // Si on ne veut pas écrire de message
            return null;
        }

        System.out.println("De qui souhaitez vous faire remonter une information?");
        Jeu.afficherJoueur(true);
        joueurDiscussion = demanderJoueur(false, true);

        // Si la personne veut dire qu'elle lui fait confiance ou pas
        System.out.println("Voulez vous préciser votre "+ConsoleText.RED_BOLD+"\"Confiance\""+ConsoleText.RESET+" ou votre "+ConsoleText.RED_BOLD+"\"Méfiance\""+ConsoleText.RESET+" pour cette personne?");
        String mefConf = input.nextLine();

        while (!mefConf.equalsIgnoreCase("Confiance") && !mefConf.equalsIgnoreCase("Méfiance")) {
            System.out.println("Erreur : Veuillez entrer le mot "+ConsoleText.RED_BOLD+"\"Confiance\""+ConsoleText.RESET+" ou "+ConsoleText.RED_BOLD+"\"Méfiance\""+ConsoleText.RESET);
            mefConf = input.nextLine();
        }

        if (mefConf.equalsIgnoreCase("Confiance")){
            return "%c%"+this.getNom()+" : Je fais confiance à "+joueurDiscussion.getNom();
        } else if (mefConf.equalsIgnoreCase("Méfiance")) {
            return "%m%"+this.getNom()+" : Je ne fais pas confiance à "+joueurDiscussion.getNom();
        } else {
            return null;
        }

    }

}
