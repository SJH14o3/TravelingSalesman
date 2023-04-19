import javax.swing.*;
import java.awt.*;

public class GameWindow{
    public JFrame gameWindow;
    public JLabel error,playerTurn, showName, coins, coinsCount, strength, strengthCount;
    public JLayeredPane jl;

    public JLabel[][] checkMark = new JLabel[10][10];
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
    private void setupCheckMarks() {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                checkMark[i][j] = new JLabel(new ImageIcon("images\\cross.png"));
                checkMark[i][j].setBounds(318 + i*65, 67 + j*65, 65, 65);
                checkMark[i][j].setVisible(false);
                jl.add(checkMark[i][j], JLayeredPane.MODAL_LAYER);
            }
        }
    }
    public void hideCheckMarks() {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                checkMark[i][j].setVisible(false);
            }
        }
    }
    GameWindow() {
        //ScoreBoard scorboard=new ScoreBoard();
        gameWindow=new JFrame();
        error=new JLabel("Roll the dice");
        playerTurn=new JLabel();

        error.setBounds(50,655,160,55);
        error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setBackground(Color.orange);
        error.setVisible(true);
        error.setOpaque(true);

        playerTurn.setBounds(50,600,160,55);
        playerTurn.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
        playerTurn.setHorizontalAlignment(SwingConstants.CENTER);
        playerTurn.setBackground(Color.black);
        playerTurn.setForeground(new Color(0xAAAAAA));
        playerTurn.setVisible(true);
        playerTurn.setOpaque(true);


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
        jl.add(playerTurn,JLayeredPane.MODAL_LAYER);
        showName = new JLabel();
        showName.setBounds(400, 25, 486, 50);
        showName.setFont(new Font("Eras Bold ITC", Font.PLAIN, 24)); //27
        showName.setForeground(new Color(0xFFFFFF));
        showName.setHorizontalAlignment(SwingConstants.CENTER);
        showName.setOpaque(false);
        showName.setBackground(Color.CYAN);
        jl.add(showName, JLayeredPane.MODAL_LAYER);
        coins = new JLabel(new ImageIcon("images\\coins.png"));
        coins.setBounds(315, 28, 36, 36);
        jl.add(coins, JLayeredPane.MODAL_LAYER);
        coinsCount = new JLabel();
        coinsCount.setBounds(350, 30, 50, 30);
        coinsCount.setHorizontalAlignment(SwingConstants.LEFT);
        coinsCount.setForeground(new Color(0xFFFFFF));
        coinsCount.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 25));
        jl.add(coinsCount, JLayeredPane.MODAL_LAYER);
        strength = new JLabel(new ImageIcon("images\\sword.png"));
        strength.setBounds(938, 28, 36, 36);
        jl.add(strength, JLayeredPane.MODAL_LAYER);
        strengthCount = new JLabel("1234");
        strengthCount.setBounds(901, 29, 40, 30);
        strengthCount.setHorizontalAlignment(SwingConstants.RIGHT);
        strengthCount.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 25));
        strengthCount.setForeground(new Color(0xFFFFFF));
        jl.add(strengthCount, JLayeredPane.MODAL_LAYER);
        setupCheckMarks();
        //while(true)System.out.println(gameWindow.getMousePosition());
    }
}
