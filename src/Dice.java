import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dice {
    private int DiceNumber,max=6,min=1;
    private int rang=max-min+1;
    private JButton dice;
    ImageIcon dice1=new ImageIcon("images\\dice1.png");
    ImageIcon dice2=new ImageIcon("images\\dice2.png");
    ImageIcon dice3=new ImageIcon("images\\dice3.png");
    ImageIcon dice4=new ImageIcon("images\\dice4.png");
    ImageIcon dice5=new ImageIcon("images\\dice5.png");
    ImageIcon dice6=new ImageIcon("images\\dice6.png");
    ImageIcon FirstImg=new ImageIcon("images\\FirstDice.png");
    Dice(JFrame gamewindow){
        dice=new JButton();
        dice.setBounds(75,330,100,100);
        dice.setIcon(FirstImg);
        dice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                DiceNumber=(int)(Math.random()*rang)+min;
                switch (DiceNumber){
                    case 1:
                        dice.setIcon(dice1);
                        break;
                    case 2:
                        dice.setIcon(dice2);
                        break;
                    case 3:
                        dice.setIcon(dice3);
                        break;
                    case 4:
                        dice.setIcon(dice4);
                        break;
                    case 5:
                        dice.setIcon(dice5);
                        break;
                    case 6:
                        dice.setIcon(dice6);
                        break;
                }
            }
        });
        gamewindow.add(dice);
    }
}
