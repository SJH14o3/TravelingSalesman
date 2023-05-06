import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrapAnimation extends JPanel implements ActionListener {
    GameWindow f=GameWindow.getF();
    final int PANEL_WIDTH = f.gameWindow.getWidth();
    final int PANEL_HEIGHT = f.gameWindow.getHeight();
    Image swordRight , swordLeft;
    Timer timer;
    int xVelocity = 1 , yVelocity = 3 , xTrap = 350 , xPerson = 350 , yTrap = 320 , yPerson = 280;
    TrapAnimation(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBounds(0,0,1300,800);
        this.setBackground(Color.BLACK);
        swordLeft = new ImageIcon("images\\trap2.png").getImage();
        swordRight = new ImageIcon("images\\ScaredPerson.png").getImage();
        timer = new Timer(1,  this);
        timer.start();
        f.jl.add(this,JLayeredPane.DRAG_LAYER);


    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(swordLeft,xTrap,yTrap,null);
        g2D.drawImage(swordRight,xPerson,yPerson,null);

    }
    public void actionPerformed(ActionEvent e){
        if (yPerson<=0){
            this.setEnabled(false);
            this.setVisible(false);
        }
        yPerson -= yVelocity;
        repaint();
    }

}




