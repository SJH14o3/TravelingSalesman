import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game {
    //map[x][y]==1==loot
    //map[x][y]==2==market
    private final int LootCash=70;
    byte countKnowQuestLoc[]=new byte[4];
    int i=0 , j=0;
    byte[] questLocCount={0,0,0,0};
    private Random random;
    private JLabel[] icons = {new JLabel(new ImageIcon("images\\icon1.png")), new JLabel(new ImageIcon("images\\icon2.png"))};
    Player[] players;
    ScoreBoard scoreboard;
    ImageIcon cross=new ImageIcon("images\\cross.png");
    JLabel MovesLeft;
    JButton changeTurn, Castle;
    int[] SaveRandomQuests=new int[8] ;
    int[][] NotePlaces={{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2}} ;
    GameWindow f=new GameWindow();
    MarketFrame marketFrame=new MarketFrame();
    Dice d=new Dice(f.jl);
    Walls walls = new Walls(f.jl);
    private int questNum = 1;
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
    private void FindQuest(int quest){
        boolean QuestFound=false;
        int x=0,y=0;
        while (QuestFound==false){
            if (f.map.map[x][y]==quest){
                QuestFound=true;
                players[d.turn-1].questLoc[questLocCount[d.turn-1]][0]= (byte) x;
                players[d.turn-1].questLoc[questLocCount[d.turn-1]][1]=(byte) y;
            }
            if (y<9){
                y++;
            }
            else {
                y=0;
                x++;
            }
        }
    }
    private void checkCastleEnabled() {
        if (Castle.isEnabled()) {
            Castle.setVisible(false);
            Castle.setEnabled(false);
        }
    }
    private void changeMoney(int in) {
        players[d.turn-1].money += in;
        f.updateCoinCount(players[d.turn-1].money);
        scoreboard.Money[d.turn-1].setText(players[d.turn-1].getName()+" money: "+players[d.turn-1].money);
    }
    private void changePower(int in) {
        players[d.turn-1].power += in;
        f.updatePowerCount(players[d.turn-1].power);
        scoreboard.Power[d.turn-1].setText(players[d.turn-1].getName()+" Power: "+players[d.turn-1].power);
    }
    private void lootFound() {
        f.map.LootDraw(d.turn-1, players);
        f.lootDialog();
        changeMoney(LootCash);
        f.map.map[players[d.turn-1].x][players[d.turn-1].y]=0; //null
    }
    private void trapMoney() {
        f.TrapDialog(players[d.turn-1].money/10, "Money!");
        changeMoney(-players[d.turn-1].money/10);
    }
    private void trapPower() {
        f.TrapDialog(players[d.turn-1].power/10, "Power!");
        changePower(-players[d.turn-1].power/10);
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
    private void checkLocation(boolean startRound) {
        if (players[d.turn-1].x > -1 && players[d.turn-1].y > -1 && players[d.turn-1].y < 10 && players[d.turn-1].x < 10) {
            switch(f.map.map[players[d.turn-1].x][players[d.turn-1].y]) {
                case 1:
                    lootFound();
                    break;
                case 2:
                    marketFrame.marketframe.setVisible(true);
                    f.gameWindow.setEnabled(false);
                    break;
                case 3:
                    if (!startRound) {
                        hitTrap();
                    }
                    break;
                case 4:
                    Castle.setVisible(true);
                    Castle.setEnabled(true);
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    if (!players[d.turn-1].knowQuestsLoc[f.map.map[players[d.turn-1].x][players[d.turn-1].y] - 11]) {
                        players[d.turn-1].knowQuestsLoc[f.map.map[players[d.turn-1].x][players[d.turn-1].y] - 11] = true;
                        f.questFound(f.map.map[players[d.turn-1].x][players[d.turn-1].y] - 11);
                    }
                    break;
            }
        }
    }
    private void move() {
        checkCastleEnabled();
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
        checkLocation(false);
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
        checkLocation(true);
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
    private void castleAction() {
        System.out.println("CASTLE!");
        String in = JOptionPane.showInputDialog("Enter Quest Location");
        if (in.charAt(0) >= 49 && in.charAt(0) <= 57 && in.charAt(1) == ' ' && in.charAt(2) >= 49 && in.charAt(2) <= 57 ) {
            int y = in.charAt(2) - 49;
            int x = in.charAt(0) - 49;
            if (f.map.map[y][x] > 10 && f.map.map[y][x] < 19 && players[d.turn-1].knowQuestsLoc[f.map.map[y][x] - 11] && questNum == f.map.map[y][x] - 10) {
                JOptionPane.showMessageDialog(null, "Successfully found quest!", "Quest is complete!", JOptionPane.INFORMATION_MESSAGE);
                changeMoney(100 + questNum * 100);
                players[d.turn-1].questsFound[questNum-1] = true;
                questNum++;
                f.questPanel.changeQuestIcon((byte) questNum);
                f.map.map[y][x] = 0;
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong!", "Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Try again!", "Wrong Input", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void setupCastleButton() {
        Castle = new JButton(new ImageIcon("images\\CastleB.png"));
        Castle.setBounds(1111, 330, 100, 100);
        Castle.setEnabled(false);
        Castle.setVisible(false);
        Castle.addActionListener(e -> castleAction());
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
    public int getQuestNum() {
        return questNum;
    }
    Game(byte playersCount) {
        random=new Random();
        scoreboard=new ScoreBoard();
        setupPlayers(playersCount);

        for (int k = 0; k < countKnowQuestLoc.length; k++) {
            countKnowQuestLoc[k]=0;
        }

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
                checkCastleEnabled();
                turnFinished();
                f.map.LootShower(d.turn-1);
///////////////////////////////////////////////////////////////////////////////////                for (int k = 0; ; k++) {
//                    setvisible label for treasure
//                }

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
        marketFrame.PowerButton.addActionListener(e -> {
            if (players[d.turn-1].money>=50) {
                players[d.turn - 1].power += 40;
                players[d.turn - 1].money -= 50;
                scoreboard.Power[d.turn - 1].setText(players[d.turn - 1].getName() + " power: " + players[d.turn - 1].power);
                scoreboard.Money[d.turn - 1].setText(players[d.turn - 1].getName() + " money: " + players[d.turn - 1].money);
            }
            else {
                marketFrame.errorInfo.setText("can't buy power!you need more money.");
            }
        });
        marketFrame.QuestButton.addActionListener(e -> {
            if (countKnowQuestLoc[d.turn-1]<8 && players[d.turn-1].money>=250) {
                int quest = random.nextInt(8) + 11;
                for (int k = 0; k < j; k++) {
                    if (quest == SaveRandomQuests[k]) {
                        quest = random.nextInt(8) + 11;
                        k = -1;
                    }
                }
                SaveRandomQuests[j] = quest;
                players[d.turn - 1].knowQuestsLoc[countKnowQuestLoc[d.turn - 1]] = true;
                //FindQuest(quest);
                countKnowQuestLoc[d.turn - 1]++;
                //TODO show quest label
                j++;
                players[d.turn - 1].money -= 250;
                scoreboard.Money[d.turn - 1].setText(players[d.turn - 1].getName() + " money: " + players[d.turn - 1].money);
            }
            else if(countKnowQuestLoc[d.turn-1]>=8){
                marketFrame.errorInfo.setText("you know all of quest locations");
            } else if (players[d.turn-1].money<250) {
                marketFrame.errorInfo.setText("can't buy!you need more money");
            }
        });
        marketFrame.Close.addActionListener(e -> {
            f.gameWindow.setEnabled(true);
            marketFrame.marketframe.setVisible(false);
            marketFrame.errorInfo.setText(":-)");
        });

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
        setupCastleButton();
        f.jl.add(Castle, JLayeredPane.MODAL_LAYER);
        GameLoop(playersCount);
    }
}