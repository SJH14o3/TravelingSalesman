import javax.swing.*;

public class MovementButtons {
    public JButton Right,Down,Left,Up;
    private ImageIcon right=new ImageIcon("images\\RightButton.png");
    private ImageIcon left=new ImageIcon("images\\LeftButton.png");
    private ImageIcon up=new ImageIcon("images\\UpButton.png");
    private ImageIcon down=new ImageIcon("images\\DownButton.png");
    MovementButtons(JLayeredPane jl){
        Right=new JButton(right);
        Down=new JButton(down);
        Left=new JButton(left);
        Up=new JButton(up);

        Right.setBounds(1148,620,60,60);
        Up.setBounds(1088,559,60,60);
        Down.setBounds(1088,681,60,60);
        Left.setBounds(1027,620,60,60);

        jl.add(Right, JLayeredPane.POPUP_LAYER);
        jl.add(Left, JLayeredPane.POPUP_LAYER);
        jl.add(Up, JLayeredPane.POPUP_LAYER);
        jl.add(Down, JLayeredPane.POPUP_LAYER);
    }
    MovementButtons(){
    }
//    public void MovementActions(){
//        Left.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                if (turn==1 && players[0].x>0){
//                    players[0].x= (byte) (players[0].x-1);
//                    DiceNumber--;
//                }
//                else if (turn==2 && players[1].x>0){
//                    players[1].x=(byte) (players[1].x-1);
//                    DiceNumber--;
//                }
//            }
//        });
//        Right.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                if (turn==1 && players[0].x<9){
//                    players[0].x= (byte) (players[0].x+1);
//                    DiceNumber--;
//                }
//                else if (turn==2 && players[1].x<9){
//                    players[1].x=(byte) (players[1].x+1);
//                    DiceNumber--;
//                }
//            }
//        });
//        Up.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                if (turn==1 && players[0].y<9){
//                    players[0].y= (byte) (players[0].y+1);
//                    DiceNumber--;
//                }
//                else if (turn==2 && players[1].y<9){
//                    players[1].y=(byte) (players[1].y+1);
//                    DiceNumber--;
//                }
//            }
//        });
//        Down.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                if (turn==1 && players[0].y>0){
//                    players[0].y= (byte) (players[0].y-1);
//                    DiceNumber--;
//                }
//                else if (turn==2 && players[1].y>0){
//                    players[1].y=(byte) (players[1].y-1);
//                    DiceNumber--;
//                }
//            }
//        });
//    }

}
