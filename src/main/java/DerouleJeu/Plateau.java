package DerouleJeu;

import Exceptions.Exceptions;
import Phrases.ConsoleText;
import Phrases.Affichage;
import joueurBot.*;

import java.util.*;

public class Plateau {
    // Initialise tous les joueurs et lance les différents moments de jeu

    public static final int idCUPIDON=0;
    public static final int idVOLEUR=1;
    public static final int idVOYANTE=2;
    public static final int idLG=3;
    public static final int idPETITEFILLE=4;
    public static final int idSORCIERE=5;
    public static final int idCHASSEUR=6;
    public static final int idVILLAGEOIS=7;

    private static int numBot = 0;

    private static int nbJoueur=9;                     // Contient le nombre de joueurs total
    private static ArrayList<Integer> listePersoPossible=new ArrayList<>();     // Contient la liste des personnages possibles

    private static Joueur grosBG;

    private static final ArrayList<String> listeNomsJoueurs = new ArrayList<>(
            Arrays.asList("Ewan", "Axel", "Alexis", "Zahir", "Dina", "Achille", "Nina", "Izel", "Lélia", "Nhu Vy", "Lucille", "Kevin", "Solène",
                    "Habiba", "Driss", "Adam", "Nour", "Julie", "Salomé", "Juliette", "Anaïs", "Maxence", "Mathis", "Dorian", "Emma")
    );

    private static Scanner input = new Scanner(System.in);
    private static String nom;

    private static boolean secretEnding = false;                                         // Une fin secrète???


    // Constructeur initalisant les différents joueurs
    public Plateau() {
        setListePersoPossible(Plateau.nbJoueur);                                  // On crée l'ensemble de nos rôles pour le tour

        Random random = new Random();
        int assignement=random.nextInt(listePersoPossible.size());
        int assignementNom;

        System.out.println();
        System.out.print("Veuillez rentrer votre nom : ");
        nom = input.nextLine();
        while (nom.equalsIgnoreCase("") || nom.equalsIgnoreCase("Personne")) {
            System.out.println(ConsoleText.YELLOW_BOLD+"Ce nom n'est pas possible, veuillez réessayer."+ConsoleText.RESET);
            nom = input.nextLine();
        }

        grosBG = new Joueur(nom, listePersoPossible.get(assignement), null);  // Initialisation du rôle du joueur // débug

        listePersoPossible.remove(assignement); //debug

        if(grosBG.getPerso().getIdPerso()==idLG){Jeu.setListeLG(1,grosBG); //On ajoute aux listes de villageois et loups
        }else{Jeu.setListeVillageois(1,grosBG);}

        Jeu.ajouterJoueur(grosBG);                                                 // On ajoute à la liste des joueurs

        Affichage.aff(ConsoleText.YELLOW_BOLD+"Note du narrateur : Bonjour "+Plateau.nom+", vous entamerez ce rôle en début de partie : "+grosBG.getPerso().getNom()+ConsoleText.RESET);

        // On crée notre liste de robots
        while (!listePersoPossible.isEmpty()) {                                    // Tant que listePersoPossible n'est pas vide, on crée de nouveaux robots
            assignement = random.nextInt(listePersoPossible.size());               // Pour choisir le rôle du bot
            assignementNom = random.nextInt(listeNomsJoueurs.size());

            while (listeNomsJoueurs.get(assignementNom).equals(nom)) {             // Si joueur et bot ont le même nom
                assignementNom = random.nextInt(listeNomsJoueurs.size());
            }

            Jeu.getListeBots().add(new Joueur(listeNomsJoueurs.get(assignementNom), listePersoPossible.get(assignement), new Bot()));  //On ajoute nos bots au tableau

            Joueur botDuTour = Jeu.getListeBots().get(numBot);                    // Ça faisait long à écrire chaque fois
            botDuTour.getBotRattache().setJoueur(botDuTour);                      // On rattache au bot le joueur auquel il est lié pour avoir accès aux actions du personnage

            listePersoPossible.remove(assignement);                               // On retire pour éviter les redondances
            listeNomsJoueurs.remove(assignementNom);

            Jeu.ajouterJoueur(botDuTour);

            if(botDuTour.getPerso().getIdPerso()==idLG) {Jeu.setListeLG(1,botDuTour);
            }else{Jeu.setListeVillageois(1,botDuTour);}

            numBot++;
        }
    }


    // Getters
    public static String getNom() {
        return nom; }

    public static Joueur getGrosBG() {
        return Plateau.grosBG;
    }

    public static boolean getSecretEnding() {
        return secretEnding; }

    public static int getNbJoueur() {
        return nbJoueur; }

    // Setters
    public static void setListePersoPossible(int nombreJoueur) {
        if (nombreJoueur==9){                           // Si on a 9 joueurs
            listePersoPossible.add(idVOLEUR);
            listePersoPossible.add(idCUPIDON);
            listePersoPossible.add(idVOYANTE);        // Debug
            listePersoPossible.add(idLG); listePersoPossible.add(idLG);     // On a 2 loups
            listePersoPossible.add(idPETITEFILLE);
            listePersoPossible.add(idSORCIERE);
            listePersoPossible.add(idCHASSEUR);
            listePersoPossible.add(idVILLAGEOIS);
        }
        else{                                          // Si le programme a été modifié...
            System.out.println("""
                    On dirait que des paramètres du programme ont été modifiés.
                    Veuillez assigner à la variable 'nbJoueur', présente dans la classe Plateau,
                    la valeur '9' afin de pouvoir jouer.
                    """);
            System.exit(0);
        }
    }


    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- .......    Fonctions    ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //


    public static void initialisationJeu(){
        Affichage.aff("Bien le bonjour, nouvel habitant de Thiercelieux. Nous ne pensions pas vous voir de sitôt.");

        Affichage.aff("Avant tout, Pouvez vous me rappeler votre nom?");
        Plateau jeu = new Plateau();
        Affichage.aff("Très bien... \""+Plateau.nom+"\"... J'imagine que vous n'êtes pas sans savoir des derniers évènements du village.");
        Affichage.aff("Nous avons comme qui dirait, un petit problème de... 'Nuisibles'... ");
        Affichage.aff("Il semblerait que quelques Loups-Garous se soient infiltrés dans le village, et... Bon je ne vous ferai pas un dessin.");
        Affichage.aff("... Comment? Vous ne pensiez pas que le loyer seraient si bas sans raison, non?");

        // Laisser la personne poser une réponse, un oui ou un non et répondre en conséquence
        System.out.print("                    "); Exceptions.sleepJeu(100);
        Affichage.affSansAttente("\"bah si.\"");                                  // On laisse un petit temps avant d'avoir les lettres affichées avec son
        Exceptions.sleepJeu(1000); System.out.print("                    ");      // C'est pour l'effet comique.
        Affichage.affSansAttente("\"non tkt\"");
        System.out.println();

        ArrayList<String> phrasesSecretEnding = new ArrayList<>();
        phrasesSecretEnding.add("bah si.");
        phrasesSecretEnding.add("bah si");
        phrasesSecretEnding.add("oui");
        phrasesSecretEnding.add("OUI");
        phrasesSecretEnding.add("si");

        String reponse = input.nextLine();
        if (phrasesSecretEnding.contains(reponse)) {
            secretEnding = true;
            boolean finDuJeu = Jeu.verifFinDuJeu(); if (finDuJeu) return;
        }

        Affichage.aff("Bon. Il n'empêche que nous allons devoir nous serrer les coudes sur les prochaines semaines!");
        Affichage.aff("Après tout, nous n'avons tous qu'un but : survivre.");
        Affichage.aff("Moi? Oh mais je ne suis qu'une simple villageoise.");
        Affichage.aff("Il commence à se faire tard, vous devriez rentrer pour votre propre sécurité. Bonne nuit, et bonne chance!\n");
    }



}
