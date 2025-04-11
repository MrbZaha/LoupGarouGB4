

public abstract class Cartes {
    enum Role{
        Loup_Garou, Petite_Fille, Voyante, Chasseur, Cupidon, Voleur, Sorciere, Villageois;

        //On contient l'ensemble de nos rôles dans une liste
        private static final Role[] roles = Role.values();

        //On place un getter pour role
        private static Role getRole(int i){
            return Role.roles[i];
        }
    }

    // Définir nos variables d'instance en fonction de Role
    private Role role;

    // Créer un constructeur par défaut
//    public Cartes(){
//        this.role;
//    }

    // Créer un constructeur de cartes qui utilise Role
    public Cartes(Role role_prop){
        this.role=role_prop;
    }

    // Placer un getter pour le role de cartes
    public Role getCarte(){
        return this.role;
    }

    // Mettre une fonction ToString qui retourne le role du joueur
    public String toString(){
        return ""+this.role;
    }

    // Création d'une fonction abstraite
    public abstract void action(Role nom);
}

// La structure de ce code est largement inspirée d'un tuto youtube
// pour créer un jeu Uno.