import javax.swing.*;
import java.awt.*;

public class MarketFrame {
    public JFrame marketframe=new JFrame("Market");
    public JButton QuestButton = new JButton(new ImageIcon("images\\buyQuest.png"));
    public JButton PowerButton = new JButton();
    public JButton[] weapons = new JButton[4];
    public JButton Close = new JButton("Exit Market");
    public JLabel errorInfo=new JLabel("Waiting for Action");
    private JLabel info=new JLabel("Welcome to Market!");
    MarketFrame(){
        marketframe.setBounds(730,330,400,420);
        marketframe.setEnabled(true);
        marketframe.setLayout(null);
        marketframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE );
        marketframe.setResizable(false);

        info.setBounds(0,0,400,50);
        info.setVisible(true);
        info.setEnabled(true);
        info.setOpaque(true);
        info.setBackground(Color.yellow);
        info.setHorizontalAlignment(SwingConstants.CENTER);

        errorInfo.setBounds(0,50,400,50);
        errorInfo.setVisible(true);
        errorInfo.setEnabled(true);
        errorInfo.setOpaque(true);
        errorInfo.setBackground(Color.red);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < weapons.length; i++) {
            String target = "images\\Weapons\\" + (i+1) + ".png";
            weapons[i] = new JButton(new ImageIcon(target));
            if (i % 2 == 0) {
                weapons[i].setBounds(20, 110 + (i/2 * 80), 150, 70);
            }
            else {
                weapons[i].setBounds(214, 110 + (i/2 * 80), 150, 70);
            }
            marketframe.add(weapons[i]);
        }

        PowerButton.setBounds(20,270,150,70);
        PowerButton.setVisible(true);
        PowerButton.setOpaque(true);
        PowerButton.setIcon(new ImageIcon("images\\powerMarket.png"));

        QuestButton.setBounds(214,270,150,70);
        QuestButton.setVisible(true);
        QuestButton.setOpaque(true);

        Close.setBounds(117, 350, 150, 20);
        Close.setVisible(true);
        Close.setOpaque(true);

        marketframe.add(PowerButton);
        marketframe.add(QuestButton);
        marketframe.add(info);
        marketframe.add(errorInfo);
        marketframe.add(Close);
        marketframe.setVisible(false);

    }
}