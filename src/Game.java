import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    int i=0;
    Player[] players;
    JLabel MovesLeft;
    JLabel[] icons = {new JLabel(new ImageIcon("images\\icon1.png")), new JLabel(new ImageIcon("images\\icon2.png"))};
    int[][] NotePlaces={{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2}};
    GameWindow f=new GameWindow();
    Dice d=new Dice(f.jl);
    Walls walls = new Walls(f.jl);
    private byte questNum = 8;
    boolean CheckNotePlaces(String direction){
        if (direction=="Left") {
            for (int x = 0; x<NotePlaces.length ; x++) {
                if (NotePlaces[x][0] == (players[d.turn-1].x) && NotePlaces[x][1] == (players[d.turn-1].y - 1) ) return true;
            }
            return false;
        } else if (direction=="Right") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x) && NotePlaces[x][1] == (players[d.turn-1].y + 1)) return true;
            }
            return false;
        } else if (direction=="Up") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x - 1) && NotePlaces[x][1] == (players[d.turn-1].y)) return true;
            }
            return false;
        } else if (direction=="Down") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x + 1) && NotePlaces[x][1] == (players[d.turn-1].y)) return true;
            }
            return false;
        }
        return false;
    }
    private void turnFinished() {
        if (d.turn == 1) {
            d.turn++;
        }
        else {
            d.turn--;
        }
        System.out.println("turn for " + d.turn);
        System.out.println("i = " + i + "\n");
        i = 0;
    }
    private void updateIcon(int i) {
        icons[i].setBounds(319 + players[i].y*(65), 67 + players[i].x*(65) , 65, 65);
        icons[i].repaint();
    }
    public void MovementActions(){
        i=0;
            f.m.Left.addActionListener(new ActionListener() {
                //TODO make error messages
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].y > 0 && d.DiceNumber>0 && CheckNotePlaces("Left")==false) {
                        players[d.turn-1].y = (byte) (players[d.turn-1].y - 1);
                        NotePlaces[i][0]=players[d.turn-1].x;
                        NotePlaces[i][1]=players[d.turn-1].y;
                        i++;
                        d.DiceNumber--;
                        System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                        System.out.println("DiceNumber: "+d.DiceNumber);
                        MovesLeft.setText("Moves Left: "+d.DiceNumber);
                        updateIcon(d.turn-1);
                        if (d.DiceNumber==0){
                            d.dice.setEnabled(true);
                            d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                            System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                            MovesLeft.setText("Roll the dice");
                            turnFinished();
                        }
                    }
                    else {
                        System.out.println("failed");
                    }
                }
            });
            f.m.Right.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].y < 9 && d.DiceNumber>0 && CheckNotePlaces("Right")==false) {
                        players[d.turn-1].y = (byte) (players[d.turn-1].y + 1);
                        NotePlaces[i][0]=players[0].x;
                        NotePlaces[i][1]=players[0].y;
                        i++;
                        d.DiceNumber--;
                        System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                        System.out.println("DiceNumber: "+d.DiceNumber);
                        MovesLeft.setText("Moves Left: "+d.DiceNumber);
                        updateIcon(d.turn-1);
                        if (d.DiceNumber==0){
                            d.dice.setEnabled(true);
                            d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                            System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                            MovesLeft.setText("Roll the dice");
                            turnFinished();
                        }
                    }
                    else {
                        System.out.println("failed");
                    }
                }
            });
            f.m.Up.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].x > 0 && players[d.turn-1].y>-1 && d.DiceNumber>0 && CheckNotePlaces("Up")==false) {
                        players[d.turn-1].x = (byte) (players[d.turn-1].x - 1);
                        NotePlaces[i][0]=players[d.turn-1].x;
                        NotePlaces[i][1]=players[d.turn-1].y;
                        i++;
                        d.DiceNumber--;
                        System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                        System.out.println("DiceNumber: "+d.DiceNumber);
                        MovesLeft.setText("Moves Left: "+d.DiceNumber);
                        updateIcon(d.turn-1);
                        if (d.DiceNumber==0){
                            d.dice.setEnabled(true);
                            d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                            System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                            MovesLeft.setText("Roll the dice");
                            turnFinished();
                        }
                    }
                    else {
                        System.out.println("failed");
                    }
                }
            });
            f.m.Down.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].x < 9 && d.DiceNumber>0 && CheckNotePlaces("Down")==false) {
                        players[d.turn-1].x = (byte) (players[d.turn-1].x + 1);
                        NotePlaces[i][0]=players[d.turn-1].x;
                        NotePlaces[i][1]=players[d.turn-1].y;
                        i++;
                        d.DiceNumber--;
                        System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                        System.out.println("DiceNumber: "+d.DiceNumber);
                        MovesLeft.setText("Moves Left: "+d.DiceNumber);
                        updateIcon(d.turn-1);
                        if (d.DiceNumber==0){
                            d.dice.setEnabled(true);
                            d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                            System.out.println("player(x,y): "+players[d.turn-1].x+" "+players[d.turn-1].y);
                            MovesLeft.setText("Roll the dice");
                            turnFinished();
                        }
                    }
                    else {
                        System.out.println("failed");
                    }
                }
            });
    }

    private void setupPlayers(byte count) {
        players = new Player[count];
        for (byte i = 0; i < players.length; i++) {
            players[i] = new Player();
            if (i==0 || i==2){
                players[i].x=9;
                players[i].y=-1;
            } else if (i==1 || i==3) {
                players[i].x=0;
                players[i].y=10;
            }
            System.out.println(players[i].getName());
        }
    }
    private void GameLoop() {
        d.turn = 1;
        System.out.println("player1(x,y): " + players[d.turn-1].x + "  " + players[d.turn-1].y);
        d.dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.DiceNumber = (byte) ((byte) (Math.random() * d.rang) + d.min);
                d.setTarget(d.DiceNumber);
                d.dice.setIcon(new ImageIcon(d.target));
                d.dice.setEnabled(false);
                MovesLeft.setText("Moves Left: " + d.DiceNumber);
                for (int k = 0; k < NotePlaces.length; k++) {
                    for (int z = 0; z < NotePlaces[k].length; z++) {
                        NotePlaces[k][z] = -2;
                    }
                }
            }
        });
        MovementActions();
    }
    void questDone() {
        questNum--;
    }
    public byte getQuestNum() {
        return questNum;
    }
    Game(byte playersCount) {
        setupPlayers(playersCount);
        MovesLeft=new JLabel();
        MovesLeft.setBounds(75,435,100,30);
        MovesLeft.setText("Roll the dice");
        MovesLeft.setFont(new Font("Comic Sans MS", Font.PLAIN,13));
        MovesLeft.setHorizontalAlignment(SwingConstants.CENTER);
        MovesLeft.setVisible(true);
        MovesLeft.setOpaque(true);
        f.jl.add(MovesLeft, JLayeredPane.MODAL_LAYER);
        updateIcon(0);
        updateIcon(1);
        f.jl.add(icons[0], JLayeredPane.POPUP_LAYER);
        f.jl.add(icons[1], JLayeredPane.POPUP_LAYER);
        GameLoop();
    }
}
