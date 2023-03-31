import javax.swing.*;

public class Game extends JFrame {
    Player[] players;
    private void setupFrame() {
        setBounds(150,5,1300,800);
        setLayout(null);
        setTitle("Traveling Salesman");
        setResizable(false);
        //TODO create a program icon.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void setupPlayers(byte count) {
        players = new Player[count];
        for (byte i = 0; i < players.length; i++) {
            players[i] = new Player();
            System.out.println(players[i].getName());
        }
    }
    Game(byte playersCount) {
        setupFrame();
        setupPlayers(playersCount);

    }
}
