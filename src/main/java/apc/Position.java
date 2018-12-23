package apc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Position {

    final static Logger logger = LoggerFactory.getLogger(Position.class);

    private int x;
    private int y;

    private Position(Builder builder) {
        x = builder.x;
        y = builder.y;
    }

    public String toString() {
        return ("(x,y) = " + getX() + "," + getY());
    }

    static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Position copy) {
        Builder builder = new Builder();
        builder.x = copy.getX();
        builder.y = copy.getY();
        return builder;
    }

    private int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    static int toNote(Position position) {
        return position.getY()*8+position.getX();
    }

    public static Position toPosition(int note) {
        int y = note/8;
        int x = note-y*8;
        return Position.newBuilder().setX(x).setY(y).build();
    }

    public static final class Builder {
        private int x;
        private int y;

        private Builder() {
        }

        Builder setX(int x) {
            this.x = x;
            return this;
        }

        Builder setY(int y) {
            this.y = y;
            return this;
        }

        Position build() {
            return new Position(this);
        }
    }
}
