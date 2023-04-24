import javax.swing.*;
import java.awt.*;

public class ScoreBoard {
    public JFrame scoreboard;
    public JLabel[] Money=new JLabel[4],Power=new JLabel[4];
    private Font font=new Font("Comic Sans MS", Font.BOLD,11);
    ScoreBoard(){
        scoreboard=new JFrame("Score Board");
        scoreboard.setBounds(0,0,250,300);
        scoreboard.setEnabled(false);
        scoreboard.setLayout(null);
        Stopwatch stopwatch=new Stopwatch(scoreboard);
        Money[0]=new JLabel();
        Money[0].setBounds(0,75,120,40);
        Money[0].setHorizontalAlignment(SwingConstants.CENTER);
        Money[0].setBackground(Color.red);
        Money[0].setFont(font);
        Money[0].setVisible(true);
        Money[0].setOpaque(true);

        Money[1]=new JLabel();
        Money[1].setBounds(120,75,120,40);
        Money[1].setHorizontalAlignment(SwingConstants.CENTER);
        Money[1].setBackground(Color.cyan);
        Money[1].setFont(font);
        Money[1].setVisible(true);
        Money[1].setOpaque(true);

        Power[0]=new JLabel();
        Power[0].setBounds(0,115,120,40);
        Power[0].setHorizontalAlignment(SwingConstants.CENTER);
        Power[0].setBackground(Color.red);
        Power[0].setFont(font);
        Power[0].setVisible(true);
        Power[0].setOpaque(true);

        Power[1]=new JLabel();
        Power[1].setBounds(120,115,120,40);
        Power[1].setHorizontalAlignment(SwingConstants.CENTER);
        Power[1].setBackground(Color.cyan);
        Power[1].setFont(font);
        Power[1].setVisible(true);
        Power[1].setOpaque(true);

        scoreboard.add(Money[0]);
        scoreboard.add(Money[1]);
        scoreboard.add(Power[0]);
        scoreboard.add(Power[1]);
        scoreboard.setVisible(true);
    }

}