package DerouleJeu;

import Exceptions.Exceptions;
import Roles.LoupGarou;
import Roles.Personnages;
import joueurBot.*;

import javax.sound.midi.Soundbank;
import java.util.*;

public class Plateau {
    // Va lancer la boucle principale du jeu
    // Initialise tous les joueurs et lance les différents moments de jeu

    public static final int idCUPIDON=0;
    public static final int idVOLEUR=1;
    public static final int idVOYANTE=2;
    public static final int idLG=3;
    public static final int idPETITEFILLE=4;
    public static final int idSORCIERE=5;
    public static final int idCHASSEUR=6;
    public static final int idVILLAGEOIS=7;
    private int numBot=0;

    private static int nbJoueur=9;                     // Contient le nombre de joueurs total
    private static ArrayList<Integer> listePersoPossible=new ArrayList<>();     // Contient la liste des personnages possibles

    private static Joueur grosBG;

    private static final ArrayList<String> listeNomsJoueurs = new ArrayList<>(
            Arrays.asList("Ewan", "Axel", "Alexis", "Zahir", "Achille", "Nina", "Izel", "Lélia", "Nhu Vy", "Lucille", "Kevin", "Solène",
                    "Habiba", "Driss", "Adam", "Nour", "Julie", "Salome", "Juliette", "Anaïs", "Maxence", "Mathis", "Dorian", "Emma")
    );

    Scanner input = new Scanner(System.in);
    private static String nom;


    // Constructeur initalisant les différents joueurs
    public Plateau() {
        setListePersoPossible(Plateau.nbJoueur);                                  // On crée l'ensemble de nos rôles pour le tour

        Random random = new Random();
        int assignement=random.nextInt(listePersoPossible.size());
        int assignementNom;

        System.out.print("Veuillez rentrer votre nom : ");
        Plateau.setNom(input.nextLine());


        grosBG = new Joueur(nom, listePersoPossible.get(assignement), null);  // Initialisation du rôle du joueur
        this.listePersoPossible.remove(assignement);

        if(grosBG.getPerso().getIdPerso()==idLG){Jeu.setListeLG(1,grosBG); //On ajoute aux listes de villageois et loups
        }else{Jeu.setListeVillageois(1,grosBG);}

        Jeu.ajouterJoueur(grosBG);                                                 // On ajoute à la liste des joueurs

        System.out.println(STR."Note du narrateur : Bonjour \{Plateau.nom}, vous entamerez ce rôle en début de partie : \{grosBG.getPerso().getNom()}\n");

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

//        for (Joueur i: Jeu.getListeJoueurs()){                                            // Debug
//            System.out.println("Joueur : "+i.getNom()+" idRole : "+i.getPerso().getIdPerso() +" rôle : "+i.getPerso());
//        }
    }


    // Getters
    public static int getNbJoueur() {
        return nbJoueur; }

    public static String getNom() {
        return nom; }

    public static ArrayList<Integer> getListePersoPossible(){
        return Plateau.listePersoPossible; }

    public static Joueur getGrosBG(){
        return Plateau.grosBG;
    }



    // Setters
    public static void setNbJoueur(int nbJoueur) {
        Plateau.nbJoueur = nbJoueur;}

    public static void setNom(String nom) {
        Plateau.nom = nom; }

    public void setListePersoPossible(int nombreJoueur) {
        if (nombreJoueur==9){                           // Si on a 9 joueurs
            this.listePersoPossible.add(this.idVOLEUR);
            this.listePersoPossible.add(this.idCUPIDON);
            this.listePersoPossible.add(this.idVOYANTE);
            this.listePersoPossible.add(this.idLG); this.listePersoPossible.add(this.idLG);     // On a 2 loups
            this.listePersoPossible.add(this.idPETITEFILLE);
            this.listePersoPossible.add(this.idSORCIERE);
            this.listePersoPossible.add(this.idCHASSEUR);
            this.listePersoPossible.add(this.idVILLAGEOIS);
        }
        else{                                          // Si le programme a été modifié...
            System.out.println("On dirait que des paramètres du programme ont été modifiés.");
            System.out.println("Veuillez assigner à la variable 'nbJoueur', présente dans la classe Plateau,");
            System.out.println("la valeur '9' afin de pouvoir jouer.");
            System.exit(0);
        }
    }








    public static void initialisationJeu(){
        System.out.println("\nBien le bonjour, nouvel habitant de Thiercelieux. Nous ne pensions pas vous voir de sitôt.");
        Exceptions.sleepJeu(2000);
        System.out.println("Avant tout, Pouvez vous me rappeler votre nom?");
        Plateau jeu = new Plateau();
        Exceptions.sleepJeu(2500);
        System.out.println(STR."\nTrès bien... \"\{Plateau.nom}\"... J'imagine que vous n'êtes pas sans savoir des derniers évènements du village.");
        Exceptions.sleepJeu(3000);
        System.out.println("Nous avons comme qui dirait, un petit problème de... 'Nuisibles'... ");
        Exceptions.sleepJeu(3000);
        System.out.println("Il semblerait que quelques Loups-Garous se soient infiltrés dans le village, et... Bon je ne vous ferai pas un dessin.");
        Exceptions.sleepJeu(3000);
        System.out.println("... Comment? Vous ne pensiez pas que le loyer seraient si bas sans raison, non?");
        // Laisser la personne poser une réponse, un oui ou un non et répondre en conséquence

        System.out.println("Bon. Il n'empêche que nous allons devoir nous serrer les coudes sur les prochaines semaines!");
        Exceptions.sleepJeu(3000);
        System.out.println("Après tout, nous n'avons tous qu'un but : survivre.");
        Exceptions.sleepJeu(3000);
        System.out.println("Moi? Oh mais je ne suis qu'une simple villageoise.");
        Exceptions.sleepJeu(3000);
        System.out.println("Il commence à se faire tard, vous devriez rentrer pour votre propre sécurité. Bonne nuit, et bonne chance!\n");
        Exceptions.sleepJeu(3000);
    }



}
