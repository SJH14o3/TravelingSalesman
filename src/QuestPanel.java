import javax.swing.*;
import java.awt.*;

public class QuestPanel {
    private JLabel icon = new JLabel();
    private JLabel text = new JLabel();
    private JLabel bg = new JLabel(new ImageIcon("images\\questPanel.png"));
    void changeQuestIcon(byte num) {
        String target = "images\\Treasures\\" + num + ".png";
        System.out.println(target);
        icon.setIcon(new ImageIcon(target));
    }
    void setText(byte num) {
        switch(num) {
            case 1:
                text.setText("Glass Chalice");
                break;
            case 2:
                text.setText("Wooden Bow");
                break;
            case 3:
                text.setText("Steel Shield");
                break;
            case 4:
                text.setText("Dragon's Scroll");
                break;
            case 5:
                text.setText("Golden Goblet");
                break;
            case 6:
                text.setText("Jeweled Sword");
                break;
            case 7:
                text.setText("Golden Key");
                break;
            case 8:
                text.setText("Diamond Ring");
                break;
            default:
                break;
        }
    }
    QuestPanel(JLayeredPane jl) {
        changeQuestIcon((byte) 1);
        icon.setBounds(1055, 8, 220, 220);
        jl.add(icon, JLayeredPane.POPUP_LAYER);
        setText((byte) 1);
        text.setBounds(1045, 228, 239, 35);
        text.setForeground(Color.WHITE);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font("Tahoma", Font.PLAIN,25));
        jl.add(text, JLayeredPane.POPUP_LAYER);
        bg.setBounds(1045, -5, 239, 270);
        jl.add(bg, JLayeredPane.PALETTE_LAYER);
    }
}
