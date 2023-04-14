import javax.swing.*;

public class Dice {
    public byte max=6,min=1;
    public byte DiceNumber,turn=1;
    MovementButtons movementButtons=new MovementButtons();
    public byte rang= (byte) (max-min+1);
    public JButton dice;
    public String target;

    Dice(JLayeredPane jl){

        dice=new JButton();
        dice.setBounds(75,330,100,100);
        dice.setIcon(new ImageIcon("images\\FirstDice.png"));
        jl.add(dice, JLayeredPane.MODAL_LAYER);
    }


    public void setTarget(int num) {
        this.target = "images\\dice" + num + ".png";
    }


}
