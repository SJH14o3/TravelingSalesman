import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Intro {
    public Timer timer=new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    public Intro(JFrame f) {
        JLabel x = new JLabel(new ImageIcon());
        x.setBounds(0, 0, 1300, 800);
        x.setBackground(Color.BLACK);
        f.add(x);
    }
}
