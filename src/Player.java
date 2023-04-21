public class Player {
    private static byte count = 0;
    private String name;
    public byte x;
    public byte y;
    public short money=0;
    public short power=0;
    public boolean[][] places = new boolean[10][10];
    //for faze one setName will be like this.
    private void setName() {
        name = "Player " + count;
    }
    public String getName() {
        return name;
    }
    Player() {
        count++;
        setName();
    }
}
