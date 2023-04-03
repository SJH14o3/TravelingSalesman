import javax.swing.*;

public class Game {
    Player[] players;
    GameWindow f = new GameWindow();
    private byte questNum = 0;
    private void setupPlayers(byte count) {
        players = new Player[count];
        for (byte i = 0; i < players.length; i++) {
            players[i] = new Player();
            System.out.println(players[i].getName());
        }
    }
    void questDone() {
        questNum++;
    }
    public byte getQuestNum() {
        return questNum;
    }
    Game(byte playersCount) {

        setupPlayers(playersCount);
    }
}
