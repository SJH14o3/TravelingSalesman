import javax.swing.*;

public class GameWindow{
    public JFrame gameWindow;
    Map map = new Map();
    JLabel[] startImg = {new JLabel(new ImageIcon("images\\startLeft.png")), new JLabel(new ImageIcon("images\\startRight.png"))};
    JLabel background = new JLabel(new ImageIcon("images\\background.png"));
    private void setupFrame() {
        gameWindow.setBounds(150,5,1300,800);
        gameWindow.setLayout(null);
        gameWindow.setTitle("Traveling Salesman");
        gameWindow.setResizable(false);
        //TODO create a program icon.
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
    }
    GameWindow() {
        gameWindow=new JFrame();
        setupFrame();
        ScoreBoard scorboard=new ScoreBoard();
        Dice d=new Dice(gameWindow);
        new QuestPanel(gameWindow);
        MovementButtons m=new MovementButtons(gameWindow);
        map.setBounds(316, 60, 654, 660);
        gameWindow.add(map);
        map.setVisible(true);
        startImg[0].setBounds(966, 64, 61, 70);
        startImg[0].setVisible(true);
        startImg[1].setBounds(255, 649, 61, 70);
        startImg[1].setVisible(true);
        gameWindow.add(startImg[0]);
        gameWindow.add(startImg[1]);
        background.setBounds(0,0, 1300, 810);
        gameWindow.add(background);
        //while(true)System.out.println(gameWindow.getMousePosition());
    }
}
