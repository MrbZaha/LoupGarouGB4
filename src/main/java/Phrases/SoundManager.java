package Phrases;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Cette classe a été créée en grande partie par ChatGPT

public class SoundManager {
    private static Clip clip;

    public static void init(String soundFilePath) {               // Permet de définir le son à utiliser
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(soundFilePath));
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            audioInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();                   // Stoppe le son s’il est encore en cours (évite empilement)
            }
            clip.setFramePosition(0);          // Revenir au début
            clip.start();
        }
    }
}
