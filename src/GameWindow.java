import javax.swing.*;

public class GameWindow{
    public JFrame gamewindow;
    Map map = new Map();
    JLabel[] startImg = {new JLabel(new ImageIcon("images\\startLeft.png")), new JLabel(new ImageIcon("images\\startRight.png"))};
    JLabel background = new JLabel(new ImageIcon("images\\background.png"));
    private void setupFrame() {
        gamewindow.setBounds(150,5,1300,800);
        gamewindow.setLayout(null);
        gamewindow.setTitle("Traveling Salesman");
        gamewindow.setResizable(false);
        //TODO create a program icon.
        gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamewindow.setVisible(true);
    }
    GameWindow() {
        gamewindow=new JFrame();
        setupFrame();
        Dice d=new Dice(gamewindow);
        MovementButtons m=new MovementButtons(gamewindow);
        map.setBounds(316, 60, 654, 660);
        gamewindow.add(map);
        map.setVisible(true);
        startImg[0].setBounds(966, 64, 61, 70);
        startImg[0].setVisible(true);
        startImg[1].setBounds(255, 649, 61, 70);
        startImg[1].setVisible(true);
        gamewindow.add(startImg[0]);
        gamewindow.add(startImg[1]);
        background.setBounds(0,0, 1300, 810);
        gamewindow.add(background);
        //while(true)System.out.println(gamewindow.getMousePosition());
    }
}
