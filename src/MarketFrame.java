import javax.swing.*;
import java.awt.*;

public class MarketFrame {
    private JFrame marketframe=new JFrame("Market");
    public JButton QuestButton = new JButton("Buy the quest place");
    public JButton PowerButton = new JButton("Buy power");
    public JLabel info=new JLabel();
    JLabel test=new JLabel("salam");
//    private ImageIcon QuestIMG=new ImageIcon("images\\")
    MarketFrame(){
        marketframe.setBounds(730,330,400,250);

        marketframe.setEnabled(true);
        test.setBounds(0,0,400,250);
        test.setVisible(true);
        test.setOpaque(true);
        test.setBackground(Color.yellow);
        test.setEnabled(true);
        PowerButton.setBounds(20,100,150,70);
        PowerButton.setVisible(true);
        PowerButton.setOpaque(true);
        //PowerButton.setIcon();
        QuestButton.setBounds(210,100,150,70);
        QuestButton.setVisible(true);
        QuestButton.setOpaque(true);
        marketframe.add(test);
        marketframe.add(PowerButton);
        marketframe.add(QuestButton);
        marketframe.setVisible(true);
    }
}
