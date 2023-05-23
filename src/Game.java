import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Game extends Sound{
    private static Game game=new Game((byte) 2);
    private final boolean[] canMove = {true, true, true, true};
    byte[] countKnowQuestLoc=new byte[4]; //4 for maximum 4 players
    int i=0 , finaly;
    private final Random random;
    private final JLabel[] icons = {new JLabel(new ImageIcon("images\\icon1.png")), new JLabel(new ImageIcon("images\\icon2.png"))};
    Player[] players;
    ScoreBoard scoreboard;

    ImageIcon cross=new ImageIcon("images\\cross.png");
    JLabel MovesLeft;
    JOptionPane FightWinner;
    JButton changeTurn, Castle, OpenMarket;
    int[][] NotePlaces={{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2},{-2,-2}} ;

    GameWindow f=GameWindow.getF();
    MarketFrame marketFrame=new MarketFrame();
    Dice d=new Dice(f.jl);
    Walls walls = new Walls(f.jl);
    Sound S = new Sound();
    private int questNum = 1;
    Timer timer = null;
    public static Game getGame(){
        return game;
    }
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
        switch (direction) {
            case "Left":
                for (int[] notePlace : NotePlaces) {
                    if (notePlace[0] == (players[d.turn - 1].x) && notePlace[1] == (players[d.turn - 1].y - 1)) {
                        f.error.setBackground(Color.yellow);
                        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                        f.error.setText("Crossed place!");
                        return false;
                    }
                }
                return true;
            case "Right":
                for (int[] notePlace : NotePlaces) {
                    if (notePlace[0] == (players[d.turn - 1].x) && notePlace[1] == (players[d.turn - 1].y + 1)) {
                        f.error.setBackground(Color.yellow);
                        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                        f.error.setText("Crossed place!");
                        return false;
                    }
                }
                return true;
            case "Up":
                for (int[] notePlace : NotePlaces) {
                    if (notePlace[0] == (players[d.turn - 1].x - 1) && notePlace[1] == (players[d.turn - 1].y)) {
                        f.error.setBackground(Color.yellow);
                        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                        f.error.setText("Crossed place!");
                        return false;
                    }
                }
                return true;
            case "Down":
                for (int[] notePlace : NotePlaces) {
                    if (notePlace[0] == (players[d.turn - 1].x + 1) && notePlace[1] == (players[d.turn - 1].y)) {
                        f.error.setBackground(Color.yellow);
                        f.error.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                        f.error.setText("Crossed place!");
                        return false;
                    }
                }
                return true;
        }
        return true;
    }
    private void FightTester(int playerNum) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        for (int i=0 ; i<playerNum-1 ; i++){
            for (int j=1 ; j<playerNum ; j++){
                if (players[i].x==players[j].x && players[i].y==players[j].y){
                    Fight(i,j);
                    return;
                }
            }
        }
    }
    private void Fight(int p1,int p2) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new FightAnimation();
        fightSound();
        timer = new Timer(500, e->{
            if (players[p1].power > players[p2].power) {
                    FightStats(p1, p2);
                } else if (players[p1].power < players[p2].power) {
                    FightStats(p2, p1);
                } else {
                    if (d.turn - 1 == p1) {
                        FightStats(p2, p1);
                    } else FightStats(p1, p2);
                }
                timer.stop();

        });
        timer.start();
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
    private void lootFound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        f.map.LootDraw(d.turn-1, players);
        f.lootDialog();
        changeMoney(70);
        f.map.map[players[d.turn-1].x][players[d.turn-1].y]=0; //null
        coins();
        new LootAnimation(d.turn);
    }
    private void trapMoney() {
        f.TrapDialog(players[d.turn-1].money/10, "Money!");
        changeMoney(-players[d.turn-1].money/10);
    }
    private void trapPower() {
        f.TrapDialog(players[d.turn-1].power/10, "Power!");
        changePower(-players[d.turn-1].power/10);
    }
    private void hitTrap() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new TrapAnimation();
        trapSound();
        f.map.markTrap(players[d.turn-1].x, players[d.turn-1].y, d.turn-1);

        final int[] k = {0};
        timer = new Timer(100, e-> {
            if (k[0] > 11) {
                timer.stop();
                if (players[d.turn-1].money < 50 && players[d.turn-1].power > 10) {
                    trapPower();
                }
                else if (players[d.turn-1].power < 10 && players[d.turn-1].money > 50) {
                    trapMoney();
                }
                else if (players[d.turn-1].power < 10 && players[d.turn-1].money < 50) {
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
            }
            k[0]++;
        });
        timer.start();


    }
    private void checkLocation(boolean startRound) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
                        questFoundSound();
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
    private void move() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
    private void turnFinished() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
        return canMove[0] || canMove[1] || canMove[2] || canMove[3];
    }
    private void stuck() {
        if (!checkCanMove()) {
            d.dice.setEnabled(false);
            d.DiceNumber = 0;
            waitForChangeTurn();
        }
    }
    private void successfulPurchase() {
        marketFrame.errorInfo.setText("The purchase was made successfully");
        try {
            coins();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        updateStats();
    }
    public void MovementActions(){
        i=0;
        f.m.Left.addActionListener(e -> {
            if (players[d.turn-1].y == 0 && d.DiceNumber>0) {
                outOfMapError();
            } else if(!walls.checkWallLeft(players, d.turn) && d.DiceNumber>0) {
                hitWallError();
            }
            if (players[d.turn-1].y > 0 && d.DiceNumber>0 && CheckNotePlaces("Left") && walls.checkWallLeft(players, d.turn)) {
                players[d.turn-1].y = (byte) (players[d.turn-1].y - 1);
                NotePlaces[i][0]=players[d.turn-1].x;
                NotePlaces[i][1]=players[d.turn-1].y+1;
                try {
                    move();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            else {
                canMove[1] = false;
                try {
                    error();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                stuck();
            }
        });
        f.m.Right.addActionListener(e -> {
            if (players[d.turn-1].y == 9 && d.DiceNumber>0) {
                outOfMapError();
            } else if (!walls.checkWallRight(players, d.turn) && d.DiceNumber>0) {
                hitWallError();
            }
            if (players[d.turn-1].y < 9 && d.DiceNumber>0 && CheckNotePlaces("Right") && walls.checkWallRight(players, d.turn)) {
                players[d.turn-1].y = (byte) (players[d.turn-1].y + 1);
                NotePlaces[i][0]=players[d.turn-1].x;
                NotePlaces[i][1]=players[d.turn-1].y-1;
                try {
                    move();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                canMove[2] = false;
                try {
                    error();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                stuck();
            }
        });
        f.m.Up.addActionListener(e -> {
            if (players[d.turn-1].x == 0 && d.DiceNumber>0) {
                outOfMapError();
            } else if(!walls.checkWallUp(players, d.turn) && d.DiceNumber>0) {
                hitWallError();
            }
            if (players[d.turn-1].x > 0 && players[d.turn-1].y>-1 && d.DiceNumber>0 && CheckNotePlaces("Up") && walls.checkWallUp(players, d.turn)) {
                players[d.turn-1].x = (byte) (players[d.turn-1].x - 1);
                NotePlaces[i][0]=players[d.turn-1].x+1;
                NotePlaces[i][1]=players[d.turn-1].y;
                try {
                    move();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                canMove[0] = false;
                try {
                    error();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                stuck();
            }
        });
        f.m.Down.addActionListener(e -> {
            if (players[d.turn-1].x == 9 && d.DiceNumber>0) {
                outOfMapError();
            } else if (!walls.checkWallDown(players, d.turn) && d.DiceNumber>0) {
                hitWallError();
            }
            if (players[d.turn-1].x < 9 && d.DiceNumber>0 && players[d.turn-1].y != 10 && CheckNotePlaces("Down") && walls.checkWallDown(players, d.turn)) {
                players[d.turn-1].x = (byte) (players[d.turn-1].x + 1);
                NotePlaces[i][0]=players[d.turn-1].x-1;
                NotePlaces[i][1]=players[d.turn-1].y;
                try {
                    move();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                canMove[3] = false;
                try {
                    error();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                stuck();
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
    private void finishGame(String winner,boolean isDraw) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        S.gameFinished();
        scoreboard.stopwatch.timer.stop();
//        String winner = "";
//        boolean isDraw = false;
//        if (players[0].completedQuest > players[1].completedQuest) winner = "player 1";
//        else if (players[1].completedQuest > players[0].completedQuest) winner = "player 2";
//        else isDraw = true;
        if (!scoreboard.scoreboard.isVisible()) {
            scoreboard.scoreboard.setVisible(true);
        }
        if (!isDraw) {
            JOptionPane.showMessageDialog(null, winner + " Won! Press \"ok\" to exit", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "DRAW!  Press \"ok\" to exit", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void castleAction() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        castle();
        String in = JOptionPane.showInputDialog("Enter Quest Location");
        if (in == null) {
            error();
            JOptionPane.showMessageDialog(null, "Please enter correct input", "Empty Input!", JOptionPane.WARNING_MESSAGE);
        }
        else if (in.length() < 3) {
            error();
            JOptionPane.showMessageDialog(null, "Input must be at least 3 characters", "Wrong Input!", JOptionPane.WARNING_MESSAGE);
        }
        else if (in.charAt(0) >= 49 && in.charAt(0) <= 57 && in.charAt(1) == ' ' && in.charAt(2) >= 49 && in.charAt(2) <= 57 ) {
            int x = in.charAt(2) - 49;
            int y = in.charAt(0) - 49;
            if (f.map.map[y][x] > 10 && f.map.map[y][x] < 19 && players[d.turn-1].knowQuestsLoc[f.map.map[y][x] - 11] && questNum == f.map.map[y][x] - 10) {
                completeQuest();
                JOptionPane.showMessageDialog(null, "Successfully found quest! your round is over", "Quest is complete!", JOptionPane.INFORMATION_MESSAGE);
                changeMoney(100 + questNum * 100);
                f.map.renderCompleteQuest(questNum-1);
                for (int i = 0; i < 2; i++) {
                    players[i].questsFound[questNum-1] = true;
                }
                questNum++;
                f.questPanel.changeQuestIcon((byte) questNum);
                f.map.map[y][x] = 0;
                players[d.turn-1].completedQuest++;
                scoreboard.quest[d.turn-1].setText("completed quest: "+ players[d.turn-1].completedQuest);
                if (questNum == 9) {
                    String winner = "";
                    boolean isDraw = false;
                    if (players[0].completedQuest > players[1].completedQuest) winner = "player 1";
                    else if (players[1].completedQuest > players[0].completedQuest) winner = "player 2";
                    else isDraw = true;
                    if (winner.equals("player 1")){
                        finaly = 1;
                    } else if (winner.equals("player 2")) {
                        finaly = 2;
                    } else if (isDraw) {
                        finaly = 0;
                    }
                    new winnerAnimation(finaly);
                    finishGame(winner,isDraw);
                }
                else {
                    if (d.turn == 1){
                        players[d.turn-1].x=0;
                        players[d.turn-1].y=10;
                    } else {
                        players[d.turn-1].x=9;
                        players[d.turn-1].y=-1;
                    }
                    updateIcon(d.turn-1);
                }
            }
            else {
                error();
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
        Castle.addActionListener(e -> {
            try {
                castleAction();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    private void updateStats() {
        scoreboard.Power[d.turn - 1].setText( " power: " + players[d.turn - 1].power);
        scoreboard.Money[d.turn - 1].setText( " money: " + players[d.turn - 1].money);
        correspondStats();
    }
    private void weaponError(int in) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (players[d.turn-1].weapon == in) {
            marketFrame.errorInfo.setText("Already bought this item");
        }
        else if (players[d.turn-1].weapon > in) {
            marketFrame.errorInfo.setText("Better weapon has been bought");
        }
        else {
            marketFrame.errorInfo.setText("Insufficient fund");
            error();
        }
    }
    private void MakeCrossedPlaceLabels(){
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
    }
    private void GameLoop(){
        d.turn = 1;
        f.playerTurn.setText("Turn: 1");
        d.dice.addActionListener(e -> {
            try {
                diceSound();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
            f.error.setBackground(Color.GREEN);
            f.error.setText("Go!");
            d.DiceNumber = (byte) ((byte) (Math.random() * d.rang) + d.min);
            //d.DiceNumber = 1;
            d.setTarget(d.DiceNumber);
            d.dice.setIcon(new ImageIcon(d.target));
            d.dice.setEnabled(false);
            MovesLeft.setText("Moves Left: " + d.DiceNumber);
            for (int[] notePlace : NotePlaces) {
                Arrays.fill(notePlace, -2);
            }
            i=0;
        });
        MovementActions();
    }
    private Game(byte playersCount) {
        //new winnerAnimation(0);
        try {
            S.gameMusic();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            throw new RuntimeException(ex);
        }
        random=new Random();
        scoreboard=new ScoreBoard();
        OpenMarket=new JButton(new ImageIcon("images\\marketB.png"));
        setupPlayers(playersCount);

        Arrays.fill(countKnowQuestLoc, (byte) 0);

        MakeCrossedPlaceLabels(); //making labels for crossed places

        OpenMarket.setBounds(1111,330,100,100);
        OpenMarket.setVisible(false);
        OpenMarket.setEnabled(false);
        OpenMarket.setOpaque(true);
        OpenMarket.addActionListener(e -> {
            try {
                marketSound();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            marketFrame.marketFrame.setVisible(true);
            marketFrame.marketFrame.setEnabled(true);
            f.gameWindow.setEnabled(false);
        });

        changeTurn = new JButton(new ImageIcon("images\\turn.png"));
        changeTurn.setBounds(55,480,150,100);
        changeTurn.setVisible(true);
        changeTurn.setVisible(false);
        changeTurn.setEnabled(false);
        changeTurn.setOpaque(true);
        changeTurn.addActionListener(e -> {
            if (!Castle.isEnabled()) {
                Castle.setEnabled(true);
            }
            try {
                FightTester(playersCount);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            d.dice.setEnabled(true);
            d.dice.setIcon(new ImageIcon("images\\FirstDice.png"));
            f.error.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
            f.error.setBackground(Color.orange);
            f.error.setText("Roll the dice!");
            MovesLeft.setText("Roll the dice");
            f.map.LootHider(d.turn-1);
            checkCastleEnabled();
            checkMarketEnabled();
            try {
                turnFinished();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            f.map.LootShower(d.turn-1);
            changeTurn.setEnabled(false);
            changeTurn.setVisible(false);
            for (int k = 0 ; k < 6 && NotePlaces[k][0]!=-2; k++) {
                if (NotePlaces[k][0] > -1 && NotePlaces[k][0] < 10 && NotePlaces[k][1] < 10 && NotePlaces[k][1] > -1) {
                    f.map.crossedPlace[NotePlaces[k][0]][NotePlaces[k][1]].setVisible(false);
                }
            }
        });
        marketFrame.PowerButton.addActionListener(e -> {
            if (players[d.turn-1].money>=50) {
                players[d.turn - 1].power += 5;
                players[d.turn - 1].money -= 50;
                marketFrame.errorInfo.setText("you got +5 power.");
                try {
                    coins();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                updateStats();
            }
            else {
                marketFrame.errorInfo.setText("Insufficient fund");
                try {
                    error();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        marketFrame.weapons[0].addActionListener(e -> {
            if (players[d.turn-1].money>=50 && players[d.turn-1].weapon < 1) {
                players[d.turn-1].weapon = 1;
                players[d.turn - 1].power = 10;
                players[d.turn - 1].money -= 50;
                successfulPurchase();
            }
            else {
                try {
                    weaponError(1);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        marketFrame.weapons[1].addActionListener(e -> {
            if (players[d.turn-1].money>=150 && players[d.turn-1].weapon < 2) {
                players[d.turn-1].weapon = 2;
                players[d.turn - 1].power = 40;
                players[d.turn - 1].money -= 150;
                successfulPurchase();
            }
            else {
                try {
                    weaponError(2);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        marketFrame.weapons[2].addActionListener(e -> {
            if (players[d.turn-1].money>=400 && players[d.turn-1].weapon < 3) {
                players[d.turn-1].weapon = 3;
                players[d.turn - 1].power = 120;
                players[d.turn - 1].money -= 400;
                successfulPurchase();
            }
            else {
                try {
                    weaponError(3);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        marketFrame.weapons[3].addActionListener(e -> {
            if (players[d.turn-1].money>=1000 && players[d.turn-1].weapon < 4) {
                players[d.turn-1].weapon = 4;
                players[d.turn - 1].power = 350;
                players[d.turn - 1].money -= 1000;
                successfulPurchase();
            }
            else {
                try {
                    weaponError(4);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        marketFrame.QuestButton.addActionListener(e-> {
            if(countKnowQuestLoc[d.turn-1]>=8){
                marketFrame.errorInfo.setText("You know all of quest locations");
            } else if (players[d.turn-1].money<250) {
                marketFrame.errorInfo.setText("Insufficient fund");
                try {
                    error();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                Random r = new Random();
                int x;
                int total = 0;
                boolean control = true;
                boolean mode = true;
                while (control) {
                    total++;
                    if (total == 50) {
                        control = false;
                        marketFrame.errorInfo.setText("You know all of quest locations");
                    }
                    x = r.nextInt(8-questNum+1) + questNum - 1;
                    if (!players[d.turn-1].knowQuestsLoc[x] && !players[d.turn-1].boughtLocation[x]) {
                        control = false;
                        players[d.turn - 1].boughtLocation[x] = true;
                        f.map.toggleQuestLoc(players[d.turn-1], false);
                        countKnowQuestLoc[d.turn - 1]++;
                        players[d.turn - 1].money -= 250;
                        scoreboard.Money[d.turn - 1].setText( " money: " + players[d.turn - 1].money);
                        successfulPurchase();
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
                            marketFrame.errorInfo.setText("You know all of quest locations");
                            control = false;
                        }
                    }
                }
            }
        });
        marketFrame.Close.addActionListener(e -> {
            f.gameWindow.setEnabled(true);
            marketFrame.marketFrame.setVisible(false);
            marketFrame.errorInfo.setText("Waiting for Action");
        });
        f.toggleScoreboard.addActionListener(e-> scoreboard.scoreboard.setVisible(!scoreboard.scoreboard.isVisible())) ;
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
        GameLoop();
    }
}