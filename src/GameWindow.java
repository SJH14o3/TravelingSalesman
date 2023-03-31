import javax.swing.*;

public class GameWindow extends JFrame {
    Map map = new Map();
    JLabel[] startImg = {new JLabel(new ImageIcon("images\\startLeft.png")), new JLabel(new ImageIcon("images\\startRight.png"))};
    JLabel background = new JLabel(new ImageIcon("images\\background.png"));
    private void setupFrame() {
        setBounds(150,5,1300,800);
        setLayout(null);
        setTitle("Traveling Salesman");
        setResizable(false);
        //TODO create a program icon.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    GameWindow() {
        setupFrame();
        map.setBounds(316, 60, 654, 660);
        add(map);
        map.setVisible(true);
        startImg[0].setBounds(966, 64, 61, 70);
        startImg[0].setVisible(true);
        startImg[1].setBounds(255, 649, 61, 70);
        startImg[1].setVisible(true);
        add(startImg[0]);
        add(startImg[1]);
        background.setBounds(0,0, 1300, 810);
        add(background);
    }
}
