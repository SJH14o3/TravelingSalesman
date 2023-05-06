import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    public class FightAnimation extends JPanel implements ActionListener {
        GameWindow f=GameWindow.getF();
        final int PANEL_WIDTH = f.gameWindow.getWidth();
        final int PANEL_HEIGHT = f.gameWindow.getHeight();
        Image swordRight , swordLeft;
        Timer timer;
        int xVelocity = 1 , yVelocity = 1 , xLeft = 250 , xRight = 350 , y = 50;
        FightAnimation(){
            this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
            this.setBounds(0,0,1300,800);
            this.setBackground(Color.BLACK);
            swordLeft = new ImageIcon("images\\swordL.png").getImage();
            swordRight = new ImageIcon("images\\swordR.png").getImage();
            timer = new Timer(1,  this);
            timer.start();
            f.jl.add(this,JLayeredPane.DRAG_LAYER);


        }
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.drawImage(swordLeft,xLeft,y,null);
            g2D.drawImage(swordRight,xRight,y,null);

        }
        public void actionPerformed(ActionEvent e){
            if (xLeft>=300){
                this.setEnabled(false);
                this.setVisible(false);
            }
            xLeft+=xVelocity;
            xRight-=xVelocity;
            repaint();
        }

    }


