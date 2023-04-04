import javax.swing.*;

public class ScoreBoard {
    public JFrame scoreboard;
    ScoreBoard(){
        scoreboard=new JFrame("Score Board");
        scoreboard.setBounds(0,0,250,200);
        scoreboard.setEnabled(false);
        scoreboard.setLayout(null);
        Stopwatch stopwatch=new Stopwatch(scoreboard);
        scoreboard.setVisible(true);
    }

}
