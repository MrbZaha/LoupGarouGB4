package Phrases;

import Exceptions.Exceptions;

import java.io.*;
import javax.sound.sampled.*;

// Cette classe a été réalisée en bonne partie avec l'aide de ChatGPT

public class Affichage {

    private static volatile boolean skip = false;
    private static volatile boolean triangleActive = true;
    private static volatile boolean texteAffiche = true;

    public static void aff(String texte) {                                    // Permet un affichage saccade
        skip = false;
        texteAffiche = false;
        System.out.println();                                                 // Pour gagner une ligne d'affichage
        SoundManager.init("src/main/resources/Sons/sfx-blip.wav");

        Thread affichage = new Thread(() -> {                                // Thread pour afficher le texte progressivement
            int freqSon = 0;
            for (int i = 0; i < texte.length(); i++) {
                char c = texte.charAt(i);
                System.out.print(c);
                System.out.flush();

                // Si on a skip, affiche le texte restant
                if (skip && !texteAffiche) {
                    System.out.print(texte.substring(texte.indexOf(c) + 1));
                    break;
                };

                freqSon++;
                if (freqSon == 6) {                                           // Tous les 6 caractères, on lance un petit son
                    SoundManager.play();
                    freqSon = 0;
                }

                Exceptions.sleepJeu(15);                            // On attend 15 ms
            }
            texteAffiche = true;
        });

        // Thread pour la gestion d'une touche qui permettrait de skip le dialogue
        Thread inputHandler = new Thread(() -> {
            try {
                System.in.read();                                            // Une touche en attente
                if (!texteAffiche) {
                    skip = true;
                } else {
                    triangleActive = false;
                }

                // Vide le buffer proprement
                while (System.in.available() > 0) System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        affichage.start();                                                    // On démarre nos threads
        inputHandler.start();

        try {
            affichage.join();                                                // Attend que le texte soit fini
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("     ");                                           // Permet d'éviter que le triangle grignote les dernières lettres                                                // Pour éviter les retours à la ligne foireux

        // Affiche le triangle tant qu’on attend une touche
        triangleActive = true;
        Thread triangle = affTri();
        triangle.start();

        try {
            inputHandler.join();                                            // Attend que le triangle finisse de s'afficher
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SoundManager.init("src/main/resources/Sons/sfx-blink.wav");
        SoundManager.play();

//        playSound("src/main/resources/Sons/sfx-blink.wav");    // Un son pour indiquer au joueur qu'on peut passer à la suite

        // Nettoyage
        triangleActive = false;
        try {
            triangle.join();                                          // Attend que le triangle disparaisse proprement avant de l'envoyer dans les méandres
        } catch (InterruptedException e) {                            // Le join ne fonctionne pas toujours...
            e.printStackTrace();
        }
        System.out.print("\r   \r");                                  // Efface le triangle
    }

    public static Thread affTri() {
        Thread triangleThread = new Thread(() -> {                    // Crée un thread pour que les 2 actions se déroulent simultanément
            try {
                while (triangleActive) {                              // Tant que triangleActive n'est pas en false
                    System.out.print("\b\b\b\b\b    ▼");              // On print un triangle permettant au joueur de comprendre qu'il doit appuyer sur la touche d'après
                    System.out.flush();
                    Thread.sleep(200);                          // On attends 200 ms

                    System.out.print("\b\b\b\b\b     ");
                    System.out.flush();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); }
        });
        return triangleThread;
    }

    // Jusqu'ici, ça n'a pas beaucoup d'utilisations, mais c'est toujours utile
    public static void affSansAttente(String texte) {
        SoundManager.init("src/main/resources/Sons/sfx-blip.wav");
        int freqSon = 0;
        for (int i = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            System.out.print(c);
            System.out.flush();

            if (++freqSon % 3 == 0) {                                               // Lance un son tous les 3 charactères, évitons le spam
                SoundManager.play();
            }

            try {
                Thread.sleep(15);                                             // On patient 15 ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

//    public static void playSound(String soundFilePath) {
//        new Thread(() -> {
//            try {
//                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(soundFilePath));
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInput);
//                clip.start();
//
//                // Attendre un peu (sans bloquer le thread principal)
//                Thread.sleep(clip.getMicrosecondLength() / 1000);
//
//                clip.close();
//                audioInput.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
}