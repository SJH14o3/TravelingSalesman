public class Player {
    private static byte count = 0;
    private String name;
    public byte x;
    public byte y;
    public short money=0;
    public short power=0;
    public boolean[] knowQuestsLoc = new boolean[8];
    public byte[][] questLoc = new byte[8][2]; //8 quests and 0 for x and 1 for y
    public boolean[] questsFound = new boolean[8];
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