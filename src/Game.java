import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game {
    //map[x][y]==1==loot
    //map[x][y]==2==market
    private final int LootCash=70;
    int i=0;
    private Random random;
    private JLabel[] icons = {new JLabel(new ImageIcon("images\\icon1.png")), new JLabel(new ImageIcon("images\\icon2.png"))};
    Player[] players;
    ScoreBoard scoreboard;
    ImageIcon cross=new ImageIcon("images\\cross.png");
    JLabel MovesLeft;
    JButton changeTurn;
    int[][] NotePlaces={{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2}};
    GameWindow f=new GameWindow();
    //MarketFrame marketFrame=new MarketFrame();
    Dice d=new Dice(f.jl);
    Walls walls = new Walls(f.jl);
    private byte questNum = 8;
    private void MarketWork(){

    }
    boolean CheckNotePlaces(String direction){
        if (direction=="Left") {
            for (int x = 0; x<NotePlaces.length ; x++) {
                if (NotePlaces[x][0] == (players[d.turn-1].x) && NotePlaces[x][1] == (players[d.turn-1].y - 1)){
                    f.error.setBackground(Color.yellow);
                    f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,17));
                    f.error.setText("Crossed place!");
                    return true;
                }
            }
            return false;
        } else if (direction=="Right") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x) && NotePlaces[x][1] == (players[d.turn-1].y + 1)) {
                    f.error.setBackground(Color.yellow);
                    f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,17));
                    f.error.setText("Crossed place!");
                    return true;
                }
            }
            return false;
        } else if (direction=="Up") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x - 1) && NotePlaces[x][1] == (players[d.turn-1].y)) {
                    f.error.setBackground(Color.yellow);
                    f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,17));
                    f.error.setText("Crossed place!");
                    return true;
                }
            }
            return false;
        } else if (direction=="Down") {
            for (int x = 0 ; x<NotePlaces.length ; x++){
                if (NotePlaces[x][0] == (players[d.turn-1].x + 1) && NotePlaces[x][1] == (players[d.turn-1].y)) {
                    f.error.setBackground(Color.yellow);
                    f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,17));
                    f.error.setText("Crossed place!");
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    private void FightTester(int playerNum){
        for (int i=0 ; i<playerNum-1 ; i++){
            for (int j=1 ; j<playerNum ; j++){
                if (players[i].x==players[j].x && players[i].y==players[j].y){
                    Fight(i,j);
                    return;
                }
            }
        }
    }
    private void lootFound() {
        f.map.LootDraw(d.turn-1, players);
        f.lootDialog();
        players[d.turn-1].money+=LootCash;
        f.updateCoinCount(players[d.turn-1].money);
        System.out.println("loot found!");
        scoreboard.Money[d.turn-1].setText(players[d.turn-1].getName()+" money: "+players[d.turn-1].money);
        f.map.map[players[d.turn-1].x][players[d.turn-1].y]=0; //null
    }
    private void trapMoney() {
        f.TrapDialog(players[d.turn-1].money/10, "Money!");
        players[d.turn-1].money -= players[d.turn-1].money/10;
        f.updateCoinCount(players[d.turn-1].money);
        scoreboard.Money[d.turn-1].setText(players[d.turn-1].getName()+" money: "+players[d.turn-1].money);
    }
    private void trapPower() {
        f.TrapDialog(players[d.turn-1].power/10, "Power!");
        players[d.turn-1].power -= players[d.turn-1].power/10;
        f.updatePowerCount(players[d.turn-1].power);
        scoreboard.Power[d.turn-1].setText(players[d.turn-1].getName()+" power: "+players[d.turn-1].power);
    }
    private void hitTrap() {
        if (players[d.turn-1].money < 100) {
            trapPower();
        }
        else if (players[d.turn-1].power < 50) {
            trapMoney();
        }
        else {
            int r = random.nextInt(2);
            if (r == 0) {
                trapMoney();
            }
            else {
                trapPower();
            }
        }
        f.map.markTrap(players[d.turn-1].x, players[d.turn-1].y, d.turn-1);
    }
    private void move() {
        f.error.setBackground(Color.green);
        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
        f.error.setText("Go!");
        d.DiceNumber--;
        MovesLeft.setText("Moves Left: "+d.DiceNumber);
        updateIcon(d.turn-1);
        if (NotePlaces[i][0] > -1 && NotePlaces[i][0] < 10 && NotePlaces[i][1] > -1 && NotePlaces[i][1] < 10) {
            f.map.crossedPlace[NotePlaces[i][0]][NotePlaces[i][1]].setVisible(true);
        }
        i++;
        if (f.map.map[players[d.turn-1].x][players[d.turn-1].y]==1){ //loot
            lootFound();
        }
        else if (f.map.map[players[d.turn-1].x][players[d.turn-1].y]==3) {
            hitTrap();
        }
        players[d.turn-1].places[players[d.turn-1].y][players[d.turn-1].x] = true;
        if (d.DiceNumber==0){
            f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
            f.error.setBackground(Color.red);
            f.error.setText("Change the turn!");
            changeTurn.setEnabled(true);
            changeTurn.setVisible(true);
        }
    }
    private void updateIcon(int i) {
        icons[i].setBounds(319 + players[i].y*(65), 67 + players[i].x*(65) , 65, 65);
        icons[i].repaint();
    }
    private void correspondStats() {
        f.showName.setText(players[d.turn-1].getName() + "'s turn");
        f.coinsCount.setText(String.valueOf(players[d.turn-1].money));
        f.strengthCount.setText(String.valueOf(players[d.turn-1].power));
        f.playerTurn.setText("Turn: "+ (d.turn));
    }
    private void turnFinished() {
        icons[d.turn-1].setVisible(false);
        if (d.turn == 1) {
            d.turn++;
        }
        else {
            d.turn--;
        }
        icons[d.turn-1].setVisible(true);
        f.map.showTraps(d.turn-1);
        correspondStats();
        i = 0;
    }
    private void outOfMapError() {
        f.error.setBackground(Color.yellow);
        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        f.error.setText("Out of map!");
    }
    private void hitWallError() {
        f.error.setBackground(Color.yellow);
        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        f.error.setText("Hit a wall!");
    }
    public void MovementActions(byte playersCount){
        i=0;
            f.m.Left.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].y == 0 && d.DiceNumber>0) {
                            outOfMapError();
                    } else if(!walls.checkWallLeft(players, d.turn) && d.DiceNumber>0) {
                        hitWallError();
                    }
                    if (players[d.turn-1].y > 0 && d.DiceNumber>0 && CheckNotePlaces("Left")==false && walls.checkWallLeft(players, d.turn)) {
                        players[d.turn-1].y = (byte) (players[d.turn-1].y - 1);
                        NotePlaces[i][0]=players[d.turn-1].x;
                        NotePlaces[i][1]=players[d.turn-1].y+1;
                        move();

                    }
                }
            });
            f.m.Right.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].y == 9 && d.DiceNumber>0) {
                        outOfMapError();
                    } else if (!walls.checkWallRight(players, d.turn) && d.DiceNumber>0) {
                        hitWallError();
                    }
                    if (players[d.turn-1].y < 9 && d.DiceNumber>0 && CheckNotePlaces("Right")==false && walls.checkWallRight(players, d.turn)) {
                        players[d.turn-1].y = (byte) (players[d.turn-1].y + 1);
                        NotePlaces[i][0]=players[d.turn-1].x;
                        NotePlaces[i][1]=players[d.turn-1].y-1;
                        move();
                    }
                }
            });
            f.m.Up.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].x == 0 && d.DiceNumber>0) {
                        outOfMapError();
                    } else if(!walls.checkWallUp(players, d.turn) && d.DiceNumber>0) {
                        hitWallError();
                    }
                    if (players[d.turn-1].x > 0 && players[d.turn-1].y>-1 && d.DiceNumber>0 && CheckNotePlaces("Up")==false && walls.checkWallUp(players, d.turn)) {
                        players[d.turn-1].x = (byte) (players[d.turn-1].x - 1);
                        NotePlaces[i][0]=players[d.turn-1].x+1;
                        NotePlaces[i][1]=players[d.turn-1].y;
                        move();
                    }
                }
            });
            f.m.Down.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (players[d.turn-1].x == 9 && d.DiceNumber>0) {
                        outOfMapError();
                    } else if (!walls.checkWallDown(players, d.turn) && d.DiceNumber>0) {
                        hitWallError();
                    }
                    if (players[d.turn-1].x < 9 && d.DiceNumber>0 && players[d.turn-1].y != 10 && CheckNotePlaces("Down")==false && walls.checkWallDown(players, d.turn)) {
                        players[d.turn-1].x = (byte) (players[d.turn-1].x + 1);
                        NotePlaces[i][0]=players[d.turn-1].x-1;
                        NotePlaces[i][1]=players[d.turn-1].y;
                        move();
                    }
                }
            });
    }

    private void setupPlayers(byte count) {
        players = new Player[count]; //count
        for (byte i = 0; i < players.length; i++) {
            players[i] = new Player();
            players[i].power = 1000;
            if (i==0 || i==2){
                players[i].x=0;
                players[i].y=10;
                scoreboard.Money[i].setText(players[i].getName()+" money: "+players[i].money);
                scoreboard.Power[i].setText(players[i].getName()+" power: "+players[i].power);
            } else if (i==1 || i==3) {
                players[i].x=9;
                players[i].y=-1;
                scoreboard.Money[i].setText(players[i].getName()+" money: "+players[i].money);
                scoreboard.Power[i].setText(players[i].getName()+" power: "+players[i].power);
            }
        }
    }
    private void Fight(int p1,int p2){
        short x;
        if (players[p1].power>players[p2].power){
            x=(short) (((players[p1].power-players[p2].power)*players[p2].money)/(players[p1].power+players[p2].power));
            players[p1].money+=x;
            players[p2].money-=x;
            players[p1].power= (short) (players[p1].power-players[p2].power);
            scoreboard.Money[p1].setText(players[p1].getName()+" money: "+players[p1].money);
            scoreboard.Power[p1].setText(players[p1].getName()+" power: "+players[p1].power);
            scoreboard.Money[p2].setText(players[p2].getName()+" money: "+players[p2].money);
        } else if (players[p1].power<players[p2].power) {
            x=(short) (((players[p2].power-players[p1].power)*players[p1].money)/(players[p1].power+players[p2].power));
            players[p2].money+=x;
            players[p1].money-=x;
            players[p2].power= (short) (players[p2].power-players[p1].power);
            scoreboard.Money[p1].setText(players[p1].getName()+" money: "+players[p1].money);
            scoreboard.Money[p2].setText(players[p2].getName()+" money: "+players[p2].money);
            scoreboard.Power[p2].setText(players[p2].getName()+" power: "+players[p2].power);
        }
    }
    private void GameLoop(byte playersCount) {
        d.turn = 1;
        f.playerTurn.setText("Turn: 1");
        d.dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
                f.error.setBackground(Color.GREEN);
                f.error.setText("Go!");
                d.DiceNumber = (byte) ((byte) (Math.random() * d.rang) + d.min);
                d.setTarget(d.DiceNumber);
                d.dice.setIcon(new ImageIcon(d.target));
                d.dice.setEnabled(false);
                MovesLeft.setText("Moves Left: " + d.DiceNumber);
                for (int k = 0 ; k < NotePlaces.length ; k++) {
                    for (int z = 0; z < NotePlaces[k].length; z++) {
                        NotePlaces[k][z] = -2;
                    }
                }
                i=0;
            }
        });
        MovementActions(playersCount);
    }
    void questDone() {
        questNum--;
    }
    public byte getQuestNum() {
        return questNum;
    }
    Game(byte playersCount) {
        random=new Random();
        scoreboard=new ScoreBoard();
        setupPlayers(playersCount);

        for (int i = 0 ; i<f.map.crossedPlace.length ; i++){
            for (int j = 0 ; j<f.map.crossedPlace[i].length ; j++){
                f.map.crossedPlace[i][j]=new JLabel(cross);
                f.map.crossedPlace[i][j].setBounds(318 + j*(65), 67 + i*(65) , 65, 65);
                f.map.crossedPlace[i][j].setOpaque(false);
                f.map.crossedPlace[i][j].setVisible(false);
                f.gameWindow.add(f.map.crossedPlace[i][j]);
                f.jl.add(f.map.crossedPlace[i][j],JLayeredPane.MODAL_LAYER);
            }
        }

        changeTurn=new JButton("Tap to change turn");
        changeTurn.setBounds(55,480,150,100);
        changeTurn.setVisible(true);
        changeTurn.setHorizontalAlignment(SwingConstants.CENTER);
        changeTurn.setVisible(false);
        changeTurn.setEnabled(false);
        changeTurn.setOpaque(true);
        changeTurn.setFont(new Font("Combria", Font.BOLD,13));
        changeTurn.setBackground(Color.blue);
        changeTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FightTester(playersCount);
                d.dice.setEnabled(true);
                d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
                f.error.setBackground(Color.orange);
                f.error.setText("Roll the dice!");
                MovesLeft.setText("Roll the dice");
                f.map.LootHider(d.turn-1);
                turnFinished();
                f.map.LootShower(d.turn-1);
                //f.showCheckMarks();
                changeTurn.setEnabled(false);
                changeTurn.setVisible(false);
                for (int k = 0 ; k < 6 && NotePlaces[k][0]!=-2; k++) {
                    if (NotePlaces[k][0] > -1 && NotePlaces[k][0] < 10 && NotePlaces[k][1] < 10 && NotePlaces[k][1] > -1) {
                        f.map.crossedPlace[NotePlaces[k][0]][NotePlaces[k][1]].setVisible(false);
                    }
                }
            }
        });
//        marketFrame.PowerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                marketFrame.marketframe.setVisible(false);
//                f.gameWindow.setEnabled(true);
//                players[d.turn - 1].power += 40;
//                players[d.turn-1].money-=50;
//                scoreboard.Power[d.turn-1].setText(players[d.turn-1].getName()+" power: "+players[d.turn-1].power);
//                scoreboard.Money[d.turn-1].setText(players[d.turn-1].getName()+" money: "+players[d.turn-1].money);
//            }
//        });
//        marketFrame.QuestButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });


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
        icons[1].setVisible(false);
        f.jl.add(icons[0], JLayeredPane.POPUP_LAYER);
        f.jl.add(icons[1], JLayeredPane.POPUP_LAYER);
        f.jl.add(changeTurn,JLayeredPane.MODAL_LAYER);
        correspondStats();
        GameLoop(playersCount);
    }
}
