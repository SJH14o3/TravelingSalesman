import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frame{
    public JFrame f;
    private JLabel l;
    private JButton newgame , loadgame;
    private JTextField title;
    frame(){ //menu
        f=new JFrame("Traveling Salesman");//creating instance of JFrame
        l=new JLabel(new ImageIcon("images\\TravelingSalesman2.png"));
        l.setBounds(0,0,1300,800);
        newgame=new JButton("New game");
        newgame.setBounds(1130,420,130,75);
        newgame.setBackground(Color.blue);

        loadgame=new JButton("Load game");
        loadgame.setBounds(1130,500,130,75);
        loadgame.setBackground(Color.cyan);

        title=new JTextField("Traveling Salesman");
        title.setBounds(0,0,500,100);
        title.setEditable(false);
        title.setFont(new Font("Segoe Script",Font.BOLD,45));
        title.setBackground(Color.yellow);

        f.add(title);
        f.add(newgame);
        f.add(loadgame);
        f.add(l);
        ImageIcon I = new ImageIcon("images\\icon.png");
        f.setIconImage(I.getImage());
        f.setBounds(350,5,1300,800);
        f.setLayout(null);
        f.setVisible(true);
        newgame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                new Game((byte) 2);
            }
        });
    }


}
