import javax.swing.*;

public class Game {
    Player[] players;
    GameWindow f = new GameWindow();

    private void setupPlayers(byte count) {
        players = new Player[count];
        for (byte i = 0; i < players.length; i++) {
            players[i] = new Player();
            System.out.println(players[i].getName());
        }
    }
    Game(byte playersCount) {

        setupPlayers(playersCount);
    }
}
