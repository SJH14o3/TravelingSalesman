import javax.swing.*;

public class Map extends JPanel {
    int[][] map = {{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}};
    JLabel border = new JLabel(new ImageIcon("images\\border.png"));
    Map() {
        border.setBounds(0, 0, 704, 704);
        border.setVisible(true);
        add(border);
        setOpaque(false);
    }
}
