import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    Clip music, menu;
    public static void coins() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\coins.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void error() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\error.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void castle() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\castle.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void fightSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\fight.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void completeQuest() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\done.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void trapSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\trap.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void questFoundSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\quest.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void marketSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\market.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public static void diceSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\roll.wav");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
    public void menuMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\menu.au");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        menu = AudioSystem.getClip();
        menu.open(i);
        menu.loop(Clip.LOOP_CONTINUOUSLY);
        menu.start();
    }
    public void gameMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File e = new File("sounds\\b.au");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        music = AudioSystem.getClip();
        music.open(i);
        music.loop(Clip.LOOP_CONTINUOUSLY);
        music.start();
    }
    public void gameFinished() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        music.stop();
        File e = new File("sounds\\goodgame.au");
        AudioInputStream i = AudioSystem.getAudioInputStream(e);
        Clip clip = AudioSystem.getClip();
        clip.open(i);
        clip.start();
    }
}
