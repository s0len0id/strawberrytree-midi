package apc;

public enum Color {
    OFF(0),
    GREEN(1),
    GREEN_BLINK(2),
    RED(3),
    RED_BLINK(4),
    YELLOW(5),
    YELLO_BLINK(6);

    private int velocity;

    Color(int velocity) {
        this.velocity = velocity;
    }

    public int velocity(){return this.velocity;}
}
