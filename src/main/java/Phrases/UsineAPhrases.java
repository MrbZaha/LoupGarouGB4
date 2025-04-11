package Phrases;

import DerouleJeu.Jeu;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UsineAPhrases {
    public static final int phraseDeConfiance = 1;
    public static final int phraseDeMefiance = 2;
    public static final int phraseAleatoire = 0;


    private static List<String> phrasesConfiance = Arrays.asList(                 // On y ajoute %c% pour déterminer les phrase de confiance
            "%c%Je pense que %cible% est digne de confiance.",
            "%c%Je ferais confiance à %cible% les yeux fermés.",
            "%c%%cible% est resté avec moi pour me protéger la nuit dernière! Vous pouvez lui faire confiance.",
            "%c%Je pense vraiment qu'on peut faire confiance à %cible%.",
            "%c%%cible% ne serait pas capable de faire du mal à une mouche, je doute fortement de sa culpabilité.",
            "%c%Je connais %cible% depuis près d'une décennie, il ne commettrait jamais ce genre d'atrocités."
    );

    private static List<String> phrasesMefiance = Arrays.asList(                 // On y ajoute %m% pour déterminer les phrase de méfiance
            "%m%%cible% cache quelque chose.",
            "%m%Je ne peux pas faire confiance à %cible%.",
            "%m%Au risque de me répéter, je vous avais dit qu'on aurait dû tuer %cible%!",
            "%m%Faites moi confiance quand je vous dis que %cible% devrait être éliminé.",
            "%m%J'ai vu %cible% se diriger vers la maison d'un de nos défunts cette nuit!",
            "%m%Je suis sûr d'avoir vu %cible% se transformer cette nuit..."
    );

    private static List<String> phrasesRandom = Arrays.asList(                   // On y ajoute de %r% qui permette de les éliminer de la liste des phrases à vérifier (perte de temps)
            "%r%J'en ai juste marre de toute cette situation...",
            "%r%Les Loups Garous n'existent pas de toute façon, vous êtes tous fous.",
            "%r%Je suis en train de craquer.",
            "%r%Je connais une bonne Shaman qui pourrait repousser les énergies négatives accumulées dans ce village!\nLaissez lui juste 1 semaine, elle est occupée...",
            "%r%Les gars je suis vraiment paumé, quelqu'un peut me faire un topo?",
            "%r%Vous pouvez répéter?",
            "%r%Les temps sont durs mais nous devons rester uni-e-s! J'ai fait un gâteau pour mettre un peu de baume à nos coeurs.",
            "%r%Je ne sais pas vraiment comment aider...",
            "%r%Ces discussions sont inutiles, on va tous mourir de toute façon.",
            "%r%La nuit prochaine si toute cette histoire n'est pas encore finie, je me tire une balle."
    );

    public static String getPhrase(int confiance) {
        Random rand = new Random();
        String source = new String();
        switch (confiance) {
            case phraseAleatoire : source = phrasesRandom.get(rand.nextInt(phrasesRandom.size()));        // On retourne une phrase sans trop de rapport
            case phraseDeConfiance : source = phrasesConfiance.get(rand.nextInt(phrasesConfiance.size()));  // On retourne une phrase pour augmenter la confiance
            case phraseDeMefiance : source = phrasesMefiance.get(rand.nextInt(phrasesMefiance.size()));    // On retourne une phrase pour diminuer la confiance
        }
//        List<String> source = confiance ? phrasesConfiance : phrasesMefiance;         // Debug
        return source;
    }
}



