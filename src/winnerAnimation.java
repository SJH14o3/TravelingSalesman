import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class winnerAnimation extends JPanel implements ActionListener {
    GameWindow f=GameWindow.getF();
    final int PANEL_WIDTH = f.gameWindow.getWidth();
    final int PANEL_HEIGHT = f.gameWindow.getHeight();
    Image win , draw;
    Timer timer;
    int xVelocity = 1 , yVelocity = 3 , xPerson2 = 800 , xPerson1 = 50 , yPerson2 = 50 , yPerson1 = 250;
    winnerAnimation(int winner){ //0==draw//1==P1//2==P2
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBounds(0,0,1300,800);
        this.setBackground(Color.BLACK);
        if (winner == 0){
            win = new ImageIcon("images\\P1win.png").getImage();
            draw = new ImageIcon("images\\P2win.png").getImage();
        }else {
            win = new ImageIcon("images\\P"+winner+"win.png").getImage();
            draw = null;
        }
        timer = new Timer(1,  this);
        timer.start();
        f.jl.add(this,JLayeredPane.DRAG_LAYER);


    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(win,xPerson1,yPerson1,null);
        g2D.drawImage(draw,xPerson2,yPerson2,null);

    }
    public void actionPerformed(ActionEvent e){
        if (yPerson1<50 || yPerson1>250){
            yVelocity *= -1;
        }
        yPerson1 -= yVelocity;
        yPerson2 += yVelocity;
        repaint();
    }

}
