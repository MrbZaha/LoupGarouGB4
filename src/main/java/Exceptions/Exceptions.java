package Exceptions;

public class Exceptions {
    public static void sleepJeu(int temps){
        try {
            Thread.sleep(temps); }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt(); } }
}
