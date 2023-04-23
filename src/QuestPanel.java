import javax.swing.*;
import java.awt.*;

public class QuestPanel {
    private JLabel icon = new JLabel();
    private JLabel text = new JLabel();
    private JLabel bg = new JLabel(new ImageIcon("images\\questPanel.png"));
    void changeQuestIcon(int num) {
        String target = "images\\Treasures\\" + num + ".png";
        text.setText(getQuestItemName(num));
        icon.setIcon(new ImageIcon(target));
    }
    static public String getQuestItemName(int in) {
        switch(in) {
            case 1:
                return "Glass Chalice";
            case 2:
                return "Wooden Bow";
            case 3:
                return "Steel Shield";
            case 4:
                return "Dragon's Scroll";
            case 5:
                return "Golden Goblet";
            case 6:
                return "Jeweled Sword";
            case 7:
                return "Golden Key";
            case 8:
                return "Diamond Ring";
            default:
                break;
        }
        return "";
    }
    QuestPanel(JLayeredPane jl) {
        changeQuestIcon(1);
        icon.setBounds(1055, 8, 220, 220);
        jl.add(icon, JLayeredPane.POPUP_LAYER);
        text.setBounds(1045, 228, 239, 35);
        text.setForeground(Color.WHITE);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font("Tahoma", Font.PLAIN,25));
        jl.add(text, JLayeredPane.POPUP_LAYER);
        bg.setBounds(1045, -5, 239, 270);
        jl.add(bg, JLayeredPane.PALETTE_LAYER);
    }
}