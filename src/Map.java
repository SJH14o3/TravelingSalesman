import javax.swing.*;
import java.util.Random;

public class Map extends JPanel {
    //1==loot
    int[][] map = new int[10][10];
    Random random;
    JLabel[] loots;
    JLabel border = new JLabel(new ImageIcon("images\\border.png"));
    Map() {
        random=new Random();
        border.setBounds(0, 0, 704, 704);
        border.setVisible(true);
        add(border);
        setOpaque(false);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j]=0;
            }
        }
    }
}
