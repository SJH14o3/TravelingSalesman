import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class frame{
    public JFrame f;
    private final Sound S = new Sound();
    frame(){ //menu
        try {
            S.menuMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        JLabel l = new JLabel(new ImageIcon("images\\menu.jpg"));
        l.setBounds(0,0,1300,800);
        JButton newGame = new JButton(new ImageIcon("images\\newGame.png"));
        newGame.setBounds(577,450,130,75);

        JButton guide = new JButton(new ImageIcon("images\\guide.png"));
        guide.setBounds(577,530,130,75);

        f =new JFrame("Traveling Salesman");
        f.setBounds(240,5,1300,800);
        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel x = new JLabel(new ImageIcon("images\\black.jpg"));
        f.add(x);
        x.setBounds(0, 0, 1300, 800);



        f.add(newGame);
        f.add(guide);
        f.add(l);
        ImageIcon I = new ImageIcon("images\\icon.png");
        f.setIconImage(I.getImage());


        guide.addActionListener(e-> {
            JLabel label = new JLabel("<html><p align=center>" + "Welcome to Traveling Salesman<br>" +
                    "in this game you will seek quest items announced by the castle and submit their location to the castle and win price!<br>" +
                    "both players start at their individual start point and start moving toward were they want!<br>" +
                    "player must explore the map to find quest items and submit their location to the castle and win a price!<br>" +
                    "every round total moves is determined by a dice.<br>" +
                    "player can move using the movement keys in bottom right corner. player cannot move more than the dice number they have rolled.<br>" +
                    "when player is out of moves a button will appear that will change the turn. by changing turn map will get changed too and player will see<br>" +
                    "their own map. they cannot see other players.<br>" +
                    "if you got stuck and cannot move anywhere simply click every four movement button and the change turn button will appear.<br>" +
                    "winner player is who has found the most quest items and they will win the castle!<br>" +
                    "when a player starts they only can see castle, market and walls. market is where you can buy weapon, improve power and buy quest location!<br>" +
                    "a weapon will give you a base power but you can add more power by buying additional power. there are 4 weapons. player starts with no weapon and they have 0 power.<br>" +
                    "buying a new weapon will remove every additional power. player cannot buy the weapon they currently have and the weaker weapons. buying additional power is always available.<br>" +
                    "power has two uses. in fight between players and losing them when hitting a trap.<br>" +
                    "when a player in their last move reach another player, they will fight! player with more power will win. if both player have same power winner will<br>" +
                    "be the player who has initiated the fight.<br>" +
                    "the winner player will lose some of their power both steal some of other player money.<br>" +
                    "loser will lose all of their power and a portion of their money. they will go back to their starting point.<br>" +
                    "money can be obtained in two ways. either finding loots which are randomized around the map or successfully submitting the quest item.<br>" +
                    "loots can only picked up one time. they grant a small amount of money.<br>" +
                    "there are 8 quest item which will be announce by the castle in a sequence. when a quest is found next quest will be announced.<br>" +
                    "when all quests are done, game will be finished and winner will be announced!<br>" +
                    "player can buy quest location. a random quest location will be revealed in their map. they must go and check it out to know what quest item is it.<br>" +
                    "random means that it could be a quest item which is not wanted by the castle for now.<br>" +
                    "submitting a quest is done by reaching the castle, pressing its button and entering its location with this method:<br>" +
                    "(row) (column) example: 5 2. careful that you can only enter a quest item that is wanted by castle and you have discover its location.<br>" +
                    "entering invalid input will give player another chance to enter location but entering a wrong location will end a player turn.<br>" +
                    "also entering the quest location correctly will finish the player round and reward them with a lot of money!<br>" +
                    "there are walls in the map. black horizontal or vertical lines between two houses. you cannot pass through them and you have to choose another path.<br>" +
                    "there are traps in the map. they are hidden but when you hit them they will be visible to you in the next rounds.<br>" +
                    "when player hit a trap they will lose 10% of either their money or power. if a player have low amount of money and power they will die when they hit a trap<br>" +
                    "and respawn at their starting point.<br>" +
                    "remember you cannot move back to the places that you have been in your round. they are marked with a red cross.<br>" +
                    "scoreboard will give you information about players and the elapsed time. it will toggle a window when you click on it.");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            JOptionPane.showMessageDialog(null, label, "Guide", JOptionPane.INFORMATION_MESSAGE);
        });
        newGame.addActionListener(e -> {
            S.menu.stop();
            f.setVisible(false);
            Game.getGame();
        });
    }

}
