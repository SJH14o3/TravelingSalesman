import javax.swing.*;
import java.awt.*;

public class ScoreBoard {
    public JFrame scoreboard;
    public JLabel[] name =new JLabel[2];
    public JLabel[] Money=new JLabel[2],Power=new JLabel[2], quest = new JLabel[2];
    private Font font=new Font("Comic Sans MS", Font.BOLD,11);
    ScoreBoard(){
        scoreboard=new JFrame("Score Board");
        scoreboard.setBounds(0,0,316,300);
        scoreboard.setEnabled(false);
        scoreboard.setLayout(null);
        Stopwatch stopwatch=new Stopwatch(scoreboard);

        name[0]=new JLabel();
        name[0].setBounds(0,75,150,40);
        name[0].setHorizontalAlignment(SwingConstants.CENTER);
        name[0].setBackground(Color.red);
        name[0].setFont(font);
        name[0].setVisible(true);
        name[0].setOpaque(true);

        name[1]=new JLabel();
        name[1].setBounds(150,75,150,40);
        name[1].setHorizontalAlignment(SwingConstants.CENTER);
        name[1].setBackground(Color.CYAN);
        name[1].setFont(font);
        name[1].setVisible(true);
        name[1].setOpaque(true);

        Money[0]=new JLabel();
        Money[0].setBounds(0,115,150,40);
        Money[0].setHorizontalAlignment(SwingConstants.CENTER);
        Money[0].setBackground(Color.red);
        Money[0].setFont(font);
        Money[0].setVisible(true);
        Money[0].setOpaque(true);

        Money[1]=new JLabel();
        Money[1].setBounds(150,115,150,40);
        Money[1].setHorizontalAlignment(SwingConstants.CENTER);
        Money[1].setBackground(Color.cyan);
        Money[1].setFont(font);
        Money[1].setVisible(true);
        Money[1].setOpaque(true);

        Power[0]=new JLabel();
        Power[0].setBounds(0,155,150,40);
        Power[0].setHorizontalAlignment(SwingConstants.CENTER);
        Power[0].setBackground(Color.red);
        Power[0].setFont(font);
        Power[0].setVisible(true);
        Power[0].setOpaque(true);

        Power[1]=new JLabel();
        Power[1].setBounds(150,155,150,40);
        Power[1].setHorizontalAlignment(SwingConstants.CENTER);
        Power[1].setBackground(Color.cyan);
        Power[1].setFont(font);
        Power[1].setVisible(true);
        Power[1].setOpaque(true);

        quest[0] = new JLabel();
        quest[0].setBounds(0,195,150,40);
        quest[0].setHorizontalAlignment(SwingConstants.CENTER);
        quest[0].setBackground(Color.RED);
        quest[0].setFont(font);
        quest[0].setVisible(true);
        quest[0].setOpaque(true);

        quest[1] = new JLabel();
        quest[1].setBounds(150,195,150,40);
        quest[1].setHorizontalAlignment(SwingConstants.CENTER);
        quest[1].setBackground(Color.cyan);
        quest[1].setFont(font);
        quest[1].setVisible(true);
        quest[1].setOpaque(true);

        scoreboard.add(name[0]);
        scoreboard.add(name[1]);
        scoreboard.add(Money[0]);
        scoreboard.add(Money[1]);
        scoreboard.add(Power[0]);
        scoreboard.add(Power[1]);
        scoreboard.add(quest[0]);
        scoreboard.add(quest[1]);
        scoreboard.setVisible(false);
    }

}