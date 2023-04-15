public class Player {
    private static byte count = 0;
    private String name;
    public byte x;
    public byte y;
    public short money;
    public short power;
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
