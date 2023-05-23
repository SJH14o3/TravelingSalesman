import javax.swing.*;
import java.awt.*;

public class GameWindow{
    private static GameWindow f = new GameWindow();
    public JFrame gameWindow;
    public JLabel error,playerTurn, showName, coins, coinsCount, strength;
    public JLabel strengthCount;
    public JLayeredPane jl = new JLayeredPane();
    Map map = new Map();
    MovementButtons m;
    JLabel[] startImg = {new JLabel(new ImageIcon("images\\startLeft.png")), new JLabel(new ImageIcon("images\\startRight.png"))};
    JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
    QuestPanel questPanel = new QuestPanel(jl);
    JButton toggleScoreboard = new JButton(new ImageIcon("images\\scoreboard.png"));
    public static GameWindow getF(){
        return f;
    }

    private void setupFrame() {
        gameWindow.setBounds(240,5,1300,800);
        gameWindow.setLayout(null);
        gameWindow.setTitle("Traveling Salesman");
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon I = new ImageIcon("images\\icon.png");
        gameWindow.setIconImage(I.getImage());
        gameWindow.setVisible(true);
    }
    public void questFound(int in) {
        String Target = "Quest Item \"" + QuestPanel.getQuestItemName(in+1) + "\"has been found!";
        JOptionPane.showMessageDialog(null, Target, "Founded Quest Item!", JOptionPane.INFORMATION_MESSAGE);
    }
    public void updateCoinCount(int in) {
        coinsCount.setText(String.valueOf(in));
    }
    public void updatePowerCount(int in) {
        strengthCount.setText(String.valueOf(in));
    }
    public void lootDialog() {
        JOptionPane.showMessageDialog(null, "You have found loot! claim it!",
                "Found Loot!", JOptionPane.INFORMATION_MESSAGE);
    }
    public void TrapDialog(int a, String str) {
        String target = "Hit a trap! You lost " + a + " " + str;
        JOptionPane.showMessageDialog(null, target,
                "Hit a Trap!", JOptionPane.ERROR_MESSAGE);
    }
    private GameWindow() {
        gameWindow=new JFrame();
        error=new JLabel("Roll the dice");
        playerTurn=new JLabel();

        toggleScoreboard.setBounds(50,27, 160, 110);
        jl.add(toggleScoreboard, JLayeredPane.MODAL_LAYER);

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

        jl.setBounds(0,0, 1300, 800);
        gameWindow.add(jl);
        setupFrame();
        //new Dice(gameWindow);
        m=new MovementButtons(jl);
        map.setBounds(291, 40, 704, 704);
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
        coinsCount.setBounds(350, 30, 70, 30);
        coinsCount.setHorizontalAlignment(SwingConstants.LEFT);
        coinsCount.setForeground(new Color(0xFFFFFF));
        coinsCount.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 25));
        jl.add(coinsCount, JLayeredPane.MODAL_LAYER);
        strength = new JLabel(new ImageIcon("images\\sword.png"));
        strength.setBounds(938, 28, 36, 36);
        jl.add(strength, JLayeredPane.MODAL_LAYER);
        strengthCount = new JLabel("0");
        strengthCount.setBounds(881, 29, 60, 30);
        strengthCount.setHorizontalAlignment(SwingConstants.RIGHT);
        strengthCount.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 25));
        strengthCount.setForeground(new Color(0xFFFFFF));
        jl.add(strengthCount, JLayeredPane.MODAL_LAYER);
    }
}