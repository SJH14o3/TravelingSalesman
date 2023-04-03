import javax.swing.*;

public class ScoreBoard {
    public JFrame scoreboard;
    ScoreBoard(){

        scoreboard=new JFrame("Score Board");
        scoreboard.setBounds(1,1,350,250);
        scoreboard.setVisible(true);
        scoreboard.setEnabled(false);
    }

}
