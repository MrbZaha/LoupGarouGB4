# Jeu du Loup Garou de Thiercelieux
## GB4 - Programmation Orientée Objet
***
## Description
Dans le cadre de mes études, j’ai eu l’occasion de travailler la Programmation Orientée Objet au travers de la création d’un jeu de cartes… Pour ne pas être contraire à moi-même et ma capacité à toujours jouer (plus ou moins volontairement) avec les règles, j’ai donc choisis de réaliser, en Java, un Jeu du Loup Garou de Thiercelieux.

Ah oui, au début il y a un peu de texte. Se ce dernier fini par vous ennuyer après plusieurs parties relancées, vous pouvez juste commenter les lignes ... à ... dans la classe Plateau et recompiler le tout.
***
## Pré-requis

Vous devez avoir Maven installé sur votre ordinateur.\
Vous aurez besoin d'au moins Java 17.

### Vérifier que Maven soit installé sous linux

Veuillez entrez cela dans le terminal : 
```
mvn -v
```
Si rien ne ressort, veuillez entrer une commande du type : 
```
apt install maven
```
***
## Lancement
**
### Sous linux (testé sous Linux Mint)
Ouvrez votre terminal depuis la source du projet

#### Etape 1 : Compiler (Non nécessaire pour cette version du projet car déjà réalisé)
Entrez la ligne suivante dans votre terminal : 
```
javac -d bin src/main/java/**/*.java
```

#### Etape 2 : Lancement
Entrez la ligne suivante dans votre terminal : 
```
java -cp bin Main.Main
```

### Sous Windows

Double-cliquez sur le document "LoupGarou.bat". Un terminal cmd se lancera avec le jeu.
***
## DISCLAIMER
Le jeu du Loup-Garou de Thiercelieux, créé par Philippe des Pallières et Hervé Marly, est la propriété des éditeurs **“Lui-Même”**. Ce projet sert uniquement de rendu pour un projet de cours et est témoin de mes compétences actuelles en informatique.
***
## Règles du jeu
### But du jeu
Pour les Villageois : éliminer les Loups-Garous.\
Pour les Loups-Garous : éliminer les Villageois.

### Les LOUPS-GAROUS :
_Chaque nuit, ils dévorent un Villageois. Le jour, ils essaient de masquer leur identité nocturne pour échapper à la vindicte populaire. Ils sont 1, 2, 3 ou 4 suivant le nombre de joueurs et les variantes appliquées (à l’heure actuelle ils ne sont que 2 pour des parties à 9 joueurs). En aucun cas un Loup-Garou ne peut dévorer un Loup-Garou._

### Les VILLAGEOIS (tous ceux qui ne sont pas Loups-Garous) :
_Chaque nuit, l’un d’entre eux est dévoré par les Loups-Garous. Ce joueur est éliminé du jeu. Les Villageois survivants se réunissent le lendemain matin et essaient de remarquer, chez les autres joueurs, les signes qui trahiraient leur identité nocturne. Après discussions, ils votent l’exécution d’un suspect qui sera éliminé du jeu. Dans cette version, on retrouve 8 types de villageois :_ 

#### Le Simple Villageois
_Il n’a aucune compétence particulière. Ses seules armes sont la capacité d’analyse des comportements pour identifier les Loups-Garous, et la force de conviction pour empêcher l’exécution de l’innocent qu’il est._

#### La Voyante
_Chaque nuit, elle découvre la vraie personnalité d’un joueur de son choix. Elle doit aider les autres Villageois, mais rester discrète pour ne pas être démasquée par les Loups-Garous._

#### Le Chasseur
_S’il se fait dévorer par les Loups-Garous ou exécuter malencontreusement par les joueurs, le Chasseur doit répliquer avant de rendre l’âme, en éliminant immédiatement n’importe quel autre joueur de son choix._

#### Le Cupidon
_Cupidon a le pouvoir de rendre 2 personnes amoureuses à jamais. La première nuit (tour préliminaire), il désigne les 2 joueurs (ou joueuses ou 1 joueur et 1 joueuse) amoureux. Cupidon peut, s’il le veut, se désigner comme l’un des deux Amoureux. Si l’un des Amoureux est éliminé, l’autre meurt de chagrin immédiatement. Un Amoureux ne doit jamais voter contre son aimé, ni lui porter aucun préjudice (même pour faire semblant !)._

#### La Sorcière
_Elle sait concocter 2 potions extrêmement puissantes : une *potion de guérison*, pour ressusciter le joueur tué par les Loups-Garous, une potion d’empoisonnement, utilisée la nuit pour éliminer un joueur._
_La Sorcière ne peut utiliser qu’une seule potion par nuit. Chaque potion ne peut être utilisée qu’une fois au cours de la partie. Si un joueur devient sorcière en cours de partie, son compteur de potion revient au départ. Le matin suivant l’usage de ce pouvoir, il pourra donc y avoir soit 0 mort, 1 mort ou 2 morts. La Sorcière peut utiliser les potions à son profit, et donc se guérir elle-même si elle vient d’être attaquée par les Loups-Garous._

#### La Petite Fille
_La Petite Fille peut, en entrouvrant les yeux, espionner les Loups-Garous pendant leur réveil. La Petite Fille ne peut espionner que la nuit, durant le tour d’éveil des Loups-Garous._

#### Le Maire
_Cette carte est confiée à un des joueurs, en plus de sa carte personnage. Le Capitaine est élu par vote, à la majorité relative. On ne peut refuser l’honneur d’être Capitaine. Dorénavant, les votes de ce joueur comptent pour 2 voix._

#### Voleur
_Le Voleur peut, au cours de la nuit, échanger sa carte avec celle d’un autre joueur. Il agit en tout début de nuit, ce qui permet à la Voyante de se mettre à jour sur les nouveaux rôles. Au levé de soleil, tous les joueurs sont invités à observer leur rôle pour voir si ce dernier a changé durant la nuit (ce dernier est automatiquement indiqué par le narrateur)._

### Rôles possibles
Parties à 9 joueurs : **2 Loups Garous, Villageois, Voleur, Chasseur, Voyante, Petite Fille, Sorcière, Cupidon.**
***
## Règles du jeu original qui ont été retirées
**Sorcière** : _Elle peut se servir de ses 2 potions la même nuit._

**Petite Fille** : _Si elle se fait surprendre par un des Loups-Garous, elle meurt immédiatement (en silence), à la place de la victime désignée. Elle n’a pas le droit de se faire passer pour un Loup-Garou et d’ouvrir grand les yeux._

**Maire** : _Si ce joueur se fait éliminer, dans son dernier souffle il désigne son successeur._

**Voleur** : Tout le personnage du voleur a été modifié pour correspondre à ma propre expérience du jeu du Loup Garou, à l’origine, voici ce que le personnage est censé faire : _Si on veut jouer le Voleur, il faut ajouter deux cartes Simples Villageois en plus de toutes celles déjà choisies. Après la distribution, les deux cartes non distribuées sont placées au centre de la table faces cachées. La première nuit, le Voleur pourra prendre connaissance de ces deux cartes, et échanger sa carte contre une des deux autres. Si ces cartes sont deux Loups-Garous, il est obligé d’échanger sa carte contre un des deux Loups-Garous. Il jouera désormais ce personnage jusqu’à la fin de la partie._

**Cupidon** :  _Si l’un des deux Amoureux est un Loup-Garou et l’autre un Villageois, le but de la partie change pour eux. Pour vivre en paix leur amour et gagner la partie, ils doivent éliminer tous les autres joueurs, Loups-Garous et Villageois, en respectant les règles de jeu._
***
## Type Java
Ce jeu a été testé sur Java 21 (sur IntelliJ et le terminal Windows) ainsi que sur Java 17.0.14 (sur le GNOME Terminal). Aucune librairie n’a été utilisée pour réaliser ce projet.

## OS de test
Ce programme a été testé sur les Systèmes d’Exploitation **Linux Mint** ainsi que **Windows 11**.
