public class Player {
    private static byte count = 0;
    private String name;
    private byte x;
    private byte y;
    private short money;
    private short strength;
    //for faze one setName will be be like this.
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
