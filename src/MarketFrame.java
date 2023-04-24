import javax.swing.*;
import java.awt.*;

public class MarketFrame {
    public JFrame marketframe=new JFrame("Market");
    public JButton QuestButton = new JButton();
    public JButton PowerButton = new JButton();
    public JButton Close = new JButton("Exit Market");
    public JLabel errorInfo=new JLabel(":-)");
    private JLabel QuestText=new JLabel("Buy the quest place") , info=new JLabel("you can buy power for 50$ and the quest location for 250$");
    private ImageIcon QuestIMG=new ImageIcon("images\\questbutton1.png") , PowerIMG=new ImageIcon("images\\sword.png");
    MarketFrame(){
        marketframe.setBounds(730,330,400,250);
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

        QuestText.setBounds(0,150,400,30);
        QuestText.setEnabled(true);
        QuestText.setVisible(true);
        QuestText.setOpaque(true);
        QuestText.setHorizontalAlignment(SwingConstants.CENTER);

        PowerButton.setBounds(20,100,150,70);
        PowerButton.setVisible(true);
        PowerButton.setOpaque(true);
        PowerButton.setIcon(PowerIMG);
        PowerButton.setText("Buy power");

        QuestButton.setBounds(214,100,150,70);
        QuestButton.setVisible(true);
        QuestButton.setOpaque(true);
        QuestButton.setIcon(QuestIMG);
        QuestButton.add(QuestText);

        Close.setBounds(117, 180, 150, 20);
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