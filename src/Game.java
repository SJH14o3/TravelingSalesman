import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game {
    //map[x][y]==1==loot
    //map[x][y]==2==market
    private final int LootCash=70;
    private boolean[] canMove = {true, true, true, true};
    byte countKnowQuestLoc[]=new byte[4];
    int i=0 , j=0;
    byte[] questLocCount={0,0,0,0};
    private Random random;
    private JLabel[] icons = {new JLabel(new ImageIcon("images\\icon1.png")), new JLabel(new ImageIcon("images\\icon2.png"))};
    Player[] players;
    ScoreBoard scoreboard;
    ImageIcon cross=new ImageIcon("images\\cross.png");
    JLabel MovesLeft;
    JOptionPane FightWinner;
    JButton changeTurn, Castle, OpenMarket;
    int[][] NotePlaces={{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2}} ;
    GameWindow f=new GameWindow();
    MarketFrame marketFrame=new MarketFrame();
    Dice d=new Dice(f.jl);
    Walls walls = new Walls(f.jl);
    private int questNum = 1;
    void makeFightFrame(int p){
        FightWinner=new JOptionPane();
        FightWinner.setBounds(730,330,400,250);
        JOptionPane.showMessageDialog(null,players[p].getName()+" won the fight!" , "Fight result", JOptionPane.INFORMATION_MESSAGE);
        FightWinner.setVisible(true);
        FightWinner.setEnabled(true);
        FightWinner.setOpaque(true);
        FightWinner.setLayout(null);

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
    private void checkMarketEnabled() {
        if (OpenMarket.isEnabled()) {
            OpenMarket.setVisible(false);
            OpenMarket.setEnabled(false);
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
        boolean mode = true;
        if (players[d.turn-1].money < 50 && players[d.turn-1].power > 10) {
            trapPower();
        }
        else if (players[d.turn-1].power < 10 && players[d.turn-1].money > 50) {
            trapMoney();
        }
        else if (players[d.turn-1].power < 10 && players[d.turn-1].money < 50) {
            mode = false;
            f.map.markTrap(players[d.turn-1].x, players[d.turn-1].y, d.turn-1);
            killPlayer(d.turn-1, (short) 0);
            JOptionPane.showMessageDialog(null, "You stepped on a trap, without having\nenough money and power. so you died!", "Trap killed You", JOptionPane.ERROR_MESSAGE);
            d.DiceNumber = 0;
            waitForChangeTurn();
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
        if (mode) {
            f.map.markTrap(players[d.turn-1].x, players[d.turn-1].y, d.turn-1);
        }
    }
    private void checkLocation(boolean startRound) {
        if (players[d.turn-1].x > -1 && players[d.turn-1].y > -1 && players[d.turn-1].y < 10 && players[d.turn-1].x < 10) {
            switch(f.map.map[players[d.turn-1].x][players[d.turn-1].y]) {
                case 1:
                    lootFound();
                    break;
                case 2:
                    OpenMarket.setVisible(true);
                    OpenMarket.setEnabled(true);
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
                        players[d.turn-1].boughtLocation[f.map.map[players[d.turn-1].x][players[d.turn-1].y] - 11] = false;
                        f.map.toggleQuestLoc(players[d.turn-1], false);
                        f.questFound(f.map.map[players[d.turn-1].x][players[d.turn-1].y] - 11);
                    }
                    break;
            }
        }
    }
    private void waitForChangeTurn() {
        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        f.error.setBackground(Color.red);
        f.error.setText("Change the turn!");
        changeTurn.setEnabled(true);
        changeTurn.setVisible(true);
    }
    private void move() {
        for (int k = 0; k < 4; k++) {
            canMove[k] = true;
        }
        checkCastleEnabled();
        checkMarketEnabled();
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
            waitForChangeTurn();
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
        f.map.toggleQuestLoc(players[d.turn-1], true);
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
    private boolean checkCanMove() {
        if (!canMove[0] && !canMove[1] && !canMove[2] && !canMove[3]) {
            return false;
        }
        return true;
    }
    private void stuck() {
        if (!checkCanMove()) {
            d.dice.setEnabled(false);
            d.DiceNumber = 0;
            waitForChangeTurn();
        }
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
                else {
                    canMove[1] = false;
                    stuck();
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
                else {
                    canMove[2] = false;
                    stuck();
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
                else {
                    canMove[0] = false;
                    stuck();
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
                else {
                    canMove[3] = false;
                    stuck();
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
            } else if (i==1 || i==3) {
                players[i].x=9;
                players[i].y=-1;
            }
            scoreboard.name[i].setText(players[i].getName());
            scoreboard.Money[i].setText("money: "+players[i].money);
            scoreboard.Power[i].setText("power: "+players[i].power);
            scoreboard.quest[i].setText("completed quest: "+0);
        }
    }
    private void finishGame() {
        String winner = "";
        boolean isDraw = false;
        if (players[0].completedQuest > players[1].completedQuest) winner = "player 1";
        else if (players[1].completedQuest > players[0].completedQuest) winner = "player 2";
        else isDraw = true;
        if (!scoreboard.scoreboard.isVisible()) {
            scoreboard.scoreboard.setVisible(true);
        }
        if (!isDraw) {
            JOptionPane.showMessageDialog(null, winner + "Won! Press \"ok\" to exit", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "DRAW!  Press \"ok\" to exit", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
        }
        System.exit(0);
    }
    private void castleAction() {
        String in = JOptionPane.showInputDialog("Enter Quest Location");
        if (in == null) {
            JOptionPane.showMessageDialog(null, "Please enter correct input", "Empty Input!", JOptionPane.WARNING_MESSAGE);
        }
        else if (in.length() < 3) {
            JOptionPane.showMessageDialog(null, "Input must be at least 3 characters", "Wrong Input!", JOptionPane.WARNING_MESSAGE);
        }
        else if (in.charAt(0) >= 49 && in.charAt(0) <= 57 && in.charAt(1) == ' ' && in.charAt(2) >= 49 && in.charAt(2) <= 57 ) {
            int x = in.charAt(2) - 49;
            int y = in.charAt(0) - 49;
            if (f.map.map[y][x] > 10 && f.map.map[y][x] < 19 && players[d.turn-1].knowQuestsLoc[f.map.map[y][x] - 11] && questNum == f.map.map[y][x] - 10) {
                JOptionPane.showMessageDialog(null, "Successfully found quest! your round is over", "Quest is complete!", JOptionPane.INFORMATION_MESSAGE);
                changeMoney(100 + questNum * 100);
                f.map.renderCompleteQuest(questNum-1);
                players[d.turn-1].questsFound[questNum-1] = true;
                questNum++;
                f.questPanel.changeQuestIcon((byte) questNum);
                f.map.map[y][x] = 0;
                players[d.turn-1].completedQuest++;
                scoreboard.quest[d.turn-1].setText("completed quest: "+ players[d.turn-1].completedQuest);
                if (questNum == 9) {
                    finishGame();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong! Your round is over.", "Wrong!", JOptionPane.ERROR_MESSAGE);
            }
            Castle.setEnabled(false);
            d.dice.setEnabled(false);
            d.DiceNumber = 0;
            waitForChangeTurn();
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
    private void killPlayer(int p, short money) {
        players[p].money-=money;
        players[p].power=0;
        players[p].weapon = 0;
        if (p == 0) {
            players[p].x = 0;
            players[p].y = 10;
        }
        else {
            players[p].x = 9;
            players[p].y = -1;
        }
        updateIcon(p);
        scoreboard.Money[p].setText(players[p].getName()+" money: "+players[p].money);
        scoreboard.Power[p].setText(players[p].getName()+" power: "+players[p].power);
    }
    private void FightStats(int winner, int loser) {
        short x;
        if (players[winner].power+players[loser].power == 0) {
            x = (short) 0;
        }
        else {
            x = (short) (((players[winner].power-players[loser].power)*players[loser].money)/(players[winner].power+players[loser].power));
        }
        killPlayer(loser, x);
        players[winner].money+=x;
        players[winner].power= (short) (players[winner].power-players[loser].power);
        makeFightFrame(winner);
        scoreboard.Money[winner].setText(players[winner].getName()+" money: "+players[winner].money);
        scoreboard.Power[winner].setText(players[winner].getName()+" power: "+players[winner].power);
    }
    private void Fight(int p1,int p2){
        short x;
        if (players[p1].power>players[p2].power){
            FightStats(p1, p2);
        } else if (players[p1].power<players[p2].power) {
            FightStats(p2, p1);
        }
        else {
            if (d.turn-1 == p1) {
                FightStats(p1, p2);
            }
            else FightStats(p2, p1);
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
    private void updateStats() {
        scoreboard.Power[d.turn - 1].setText(players[d.turn - 1].getName() + " power: " + players[d.turn - 1].power);
        scoreboard.Money[d.turn - 1].setText(players[d.turn - 1].getName() + " money: " + players[d.turn - 1].money);
        correspondStats();
    }
    private void weaponError(int in) {
        if (players[d.turn-1].weapon == in) {
            marketFrame.errorInfo.setText("Already bought this item");
        }
        else if (players[d.turn-1].weapon > in) {
            marketFrame.errorInfo.setText("Better weapon has been bought");
        }
        else {
            marketFrame.errorInfo.setText("Insufficient fund");
        }
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
        OpenMarket=new JButton(new ImageIcon("images\\marketB.png"));
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

        OpenMarket.setBounds(1111,330,100,100);
        OpenMarket.setVisible(false);
        OpenMarket.setEnabled(false);
        OpenMarket.setOpaque(true);
        OpenMarket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marketFrame.marketframe.setVisible(true);
                marketFrame.marketframe.setEnabled(true);
                f.gameWindow.setEnabled(false);
            }
        });

        changeTurn=new JButton(new ImageIcon("images\\turn.png"));
        changeTurn.setBounds(55,480,150,100);
        changeTurn.setVisible(true);
        changeTurn.setVisible(false);
        changeTurn.setEnabled(false);
        changeTurn.setOpaque(true);
        changeTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Castle.isEnabled()) {
                    Castle.setEnabled(true);
                }
                FightTester(playersCount);
                d.dice.setEnabled(true);
                d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
                f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
                f.error.setBackground(Color.orange);
                f.error.setText("Roll the dice!");
                MovesLeft.setText("Roll the dice");
                f.map.LootHider(d.turn-1);
                checkCastleEnabled();
                checkMarketEnabled();
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
                players[d.turn - 1].power += 5;
                players[d.turn - 1].money -= 50;
                marketFrame.errorInfo.setText("you got +5 power.");
                updateStats();
            }
            else {
                marketFrame.errorInfo.setText("Insufficient fund");
            }
        });
        marketFrame.weapons[0].addActionListener(e -> {
            if (players[d.turn-1].money>=50 && players[d.turn-1].weapon < 1) {
                players[d.turn-1].weapon = 1;
                players[d.turn - 1].power = 10;
                players[d.turn - 1].money -= 50;
                marketFrame.errorInfo.setText("The purchase was made successfully");
                updateStats();
            }
            else {
                weaponError(1);
            }
        });
        marketFrame.weapons[1].addActionListener(e -> {
            if (players[d.turn-1].money>=150 && players[d.turn-1].weapon < 2) {
                players[d.turn-1].weapon = 2;
                players[d.turn - 1].power = 40;
                players[d.turn - 1].money -= 150;
                marketFrame.errorInfo.setText("The purchase was made successfully");
                updateStats();
            }
            else {
                weaponError(2);
            }
        });
        marketFrame.weapons[2].addActionListener(e -> {
            if (players[d.turn-1].money>=400 && players[d.turn-1].weapon < 3) {
                players[d.turn-1].weapon = 3;
                players[d.turn - 1].power = 120;
                players[d.turn - 1].money -= 400;
                marketFrame.errorInfo.setText("The purchase was made successfully");
                updateStats();
            }
            else {
                weaponError(3);
            }
        });
        marketFrame.weapons[3].addActionListener(e -> {
            if (players[d.turn-1].money>=1000 && players[d.turn-1].weapon < 4) {
                players[d.turn-1].weapon = 4;
                players[d.turn - 1].power = 350;
                players[d.turn - 1].money -= 1000;
                marketFrame.errorInfo.setText("The purchase was made successfully");
                updateStats();
            }
            else {
                weaponError(4);
            }
        });
        marketFrame.QuestButton.addActionListener(e-> {
            if(countKnowQuestLoc[d.turn-1]>=8){
                marketFrame.errorInfo.setText("You do know all of quest locations");
            } else if (players[d.turn-1].money<250) {
                marketFrame.errorInfo.setText("Insufficient fund");
            }
            else {
                Random r = new Random();
                int x;
                boolean control = true;
                boolean mode = true;
                while (control) {
                    x = r.nextInt(8-questNum+1) + questNum - 1;
                    if (!players[d.turn-1].knowQuestsLoc[x] && !players[d.turn-1].boughtLocation[x]) {
                        control = false;
                        players[d.turn - 1].boughtLocation[x] = true;
                        f.map.toggleQuestLoc(players[d.turn-1], false);
                        //FindQuest(quest);
                        countKnowQuestLoc[d.turn - 1]++;
                        players[d.turn - 1].money -= 250;
                        scoreboard.Money[d.turn - 1].setText(players[d.turn - 1].getName() + " money: " + players[d.turn - 1].money);
                        marketFrame.errorInfo.setText("The purchase was Successful.");
                        correspondStats();
                    }
                    else if (mode) {
                        mode = false;
                        int count = 0;
                        for (int i = 0; i < 8; i++) {
                            if (players[d.turn-1].knowQuestsLoc[i]) {
                                count++;
                            }
                            else if (players[d.turn-1].boughtLocation[i]) {
                                count++;
                            }
                        }
                        if (count == 9 - questNum) {
                            marketFrame.errorInfo.setText("You do know all of quest locations");
                            control = false;
                        }
                    }
                }
            }
        });
        /*marketFrame.QuestButton.addActionListener(e -> {
            if (countKnowQuestLoc[d.turn-1]<8 && players[d.turn-1].money>=250) {
                int quest = random.nextInt(8) + 11;
                for (int k = 0; k < j; k++) {
                    if (quest == SaveRandomQuests[k]) {
                        quest = random.nextInt(8) + 11;
                        k = -1;
                    }
                }
                SaveRandomQuests[j] = quest;
                players[d.turn - 1].boughtLocation[countKnowQuestLoc[d.turn - 1]] = true;
                f.map.toggleQuestLoc(players[d.turn-1], false);
                //FindQuest(quest);
                countKnowQuestLoc[d.turn - 1]++;
                j++;
                players[d.turn - 1].money -= 250;
                scoreboard.Money[d.turn - 1].setText(players[d.turn - 1].getName() + " money: " + players[d.turn - 1].money);
                marketFrame.errorInfo.setText("you got one of treasure locations.");
                correspondStats();
            }
            else if(countKnowQuestLoc[d.turn-1]>=8){
                marketFrame.errorInfo.setText("you know all of quest locations");
            } else if (players[d.turn-1].money<250) {
                marketFrame.errorInfo.setText("Insufficient fund");
            }
        });*/
        marketFrame.Close.addActionListener(e -> {
            f.gameWindow.setEnabled(true);
            marketFrame.marketframe.setVisible(false);
            marketFrame.errorInfo.setText("Waiting for Action");
        });
        f.toggleScoreboard.addActionListener(e-> {
            scoreboard.scoreboard.setVisible(!scoreboard.scoreboard.isVisible());
        }) ;
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
        f.jl.add(OpenMarket,JLayeredPane.MODAL_LAYER);
        GameLoop(playersCount);
    }
}