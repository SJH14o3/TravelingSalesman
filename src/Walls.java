import javax.swing.*;
import java.util.Random;

public class Walls {
    public int[][][] walls = new int[2][10][10];
    private void placeWall(int direct, int x, int y) {
        walls[direct][x][y] = 1;
    }
    private void drawHWall(JLayeredPane jl, int x, int y) {
        JLabel label = new JLabel(new ImageIcon("images\\HWall.png"));
        label.setBounds(320 + 65*x, 129 + 65*y, 61, 5);
        jl.add(label, JLayeredPane.MODAL_LAYER);
    }
    private void drawVWall(JLayeredPane jl, int x, int y) {
        JLabel label = new JLabel(new ImageIcon("images\\VWall.png"));
        label.setBounds(380 + 65*x, 69 + 65*y, 5, 61);
        jl.add(label, JLayeredPane.MODAL_LAYER);
    }
    boolean checkWallUp(Player[] players, int turn) {
        if(walls[0][players[turn-1].y][players[turn-1].x-1] == 1) {
            System.out.println("hit a wall");
            return false;
        }
        return true;
    }
    boolean checkWallDown(Player[] players, int turn) {
        if(walls[0][players[turn-1].y][players[turn-1].x] == 1) {
            System.out.println("hit a wall");
            return false;
        }
        return true;
    }
    boolean checkWallRight(Player[] players, int turn) {
        if (players[turn-1].y == -1) {
            return true;
        }
        if(walls[1][players[turn-1].y][players[turn-1].x] == 1) {
            System.out.println("hit a wall");
            return false;
        }
        return true;
    }
    boolean checkWallLeft(Player[] players, int turn) {
        if (players[turn-1].y == 10) {
            return true;
        }
        if(walls[1][players[turn-1].y-1][players[turn-1].x] == 1) {
            System.out.println("hit a wall");
            return false;
        }
        return true;
    }
    Walls(JLayeredPane jl) {
        int[][] houses = new int[10][10];
        int x, y;
        Random rng = new Random();
        for (int i = 0; i < 10;) {
            x = rng.nextInt(10);
            y = rng.nextInt(8) + 1;
            if (houses[x][y+1] == 0 && houses[x][y] == 0) {
                houses[x][y+1] = houses[x][y] = 1;
                placeWall(0, x, y);
                drawHWall(jl, x, y);
                //System.out.println("Horizontal Wall at " + x + " " + y);
                i++;
            }
            //else System.out.println("placing H wall was canceled : " + x + " " + y);
        }
        for (int i = 0; i < 10;) {
            y = rng.nextInt(10);
            x = rng.nextInt(8) + 1;
            if (houses[x+1][y] == 0 && houses[x][y] == 0) {
                houses[x+1][y] = houses[x-1][y] = 1;
                placeWall(1, x, y);
                drawVWall(jl, x, y);
                //System.out.println("Vertical Wall at " + x + " " + y);
                i++;
            }
            //else System.out.println("placing V wall was canceled : " + x + " " + y);
        }
    }
}