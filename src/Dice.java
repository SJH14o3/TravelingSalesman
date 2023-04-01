import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dice {
    private int DiceNumber,max=6,min=1;
    private int rang=max-min+1;
    private JButton dice;
    private String target;
    Dice(JFrame gameWindow){
        dice=new JButton();
        dice.setBounds(75,330,100,100);
        dice.setIcon(new ImageIcon("images\\FirstDice.png"));
        gameWindow.add(dice);
        dice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                DiceNumber=(int)(Math.random()*rang)+min;
                setTarget(DiceNumber);
                dice.setIcon(new ImageIcon(target));
            }
        });
    }
    private void setTarget(int num) {
        this.target = "images\\dice" + num + ".png";
    }
}
