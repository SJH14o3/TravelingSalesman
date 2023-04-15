import javax.swing.*;
import java.awt.*;

public class GameWindow{
    public JFrame gameWindow;
    public JLabel error;
    public JLayeredPane jl;
    Map map = new Map();
    MovementButtons m;
    JLabel[] startImg = {new JLabel(new ImageIcon("images\\startLeft.png")), new JLabel(new ImageIcon("images\\startRight.png"))};
    JLabel background = new JLabel(new ImageIcon("images\\background.png"));
    private void setupFrame() {
        gameWindow.setBounds(230,5,1300,800);
        gameWindow.setLayout(null);
        gameWindow.setTitle("Traveling Salesman");
        gameWindow.setResizable(false);
        //TODO create a program icon.
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
    }
    GameWindow() {
        //ScoreBoard scorboard=new ScoreBoard();
        gameWindow=new JFrame();
        error=new JLabel("Go");
        error.setBounds(50,600,160,110);
        error.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setBackground(Color.green);
        error.setVisible(true);
        error.setOpaque(true);
        jl = new JLayeredPane();
        jl.setBounds(0,0, 1300, 800);
        gameWindow.add(jl);
        setupFrame();
        //new Dice(gameWindow);
        new QuestPanel(jl);
        m=new MovementButtons(jl);
        map.setBounds(316, 60, 654, 660);
        jl.add(map, JLayeredPane.PALETTE_LAYER);
        map.setVisible(true);
        startImg[0].setBounds(966, 64, 61, 70);
        startImg[0].setVisible(true);
        startImg[1].setBounds(255, 649, 61, 70);
        startImg[1].setVisible(true);
        jl.add(startImg[0], JLayeredPane.PALETTE_LAYER);
        jl.add(startImg[1], JLayeredPane.PALETTE_LAYER);
        background.setBounds(0,0, 1300, 810);
        jl.add(background, JLayeredPane.DEFAULT_LAYER);
        jl.add(error,JLayeredPane.MODAL_LAYER);
        //while(true)System.out.println(gameWindow.getMousePosition());
    }
}
