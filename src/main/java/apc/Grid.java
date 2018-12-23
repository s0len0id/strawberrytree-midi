package apc;

import api.Demoable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;


public class Grid implements Demoable {

    private final Logger logger = LoggerFactory.getLogger(Position.class);

    private final static int MAX_X = 7;
    private final static int MAX_Y = 4;
    private final Receiver receiver;

    private List<Integer> row = new ArrayList<>();
    private List<Integer> column = new ArrayList<>();

    public Grid(Receiver receiver) {
        this.receiver = receiver;

        for (int x = 0; x <= MAX_X; x++) {
            column.add(x);
        }
        for (int y = 0; y <= MAX_Y; y++) {
            row.add(y);
        }
    }


    @Override
    public void setLedRow(int row, Color color) {
        List<Position> positions = getRowPositions(row);
        List<ShortMessage> msgs = Led.generateOnMsgs(positions, color);
        setLeds(color, msgs);
    }

    private List<Position> getRowPositions(int row) {
        if (row > MAX_X) {
            logger.error("maximum x = {} but was {}.", MAX_X, row, new IndexOutOfBoundsException("x position too large."));
        }
        List<Position> positions = new ArrayList<>(5);
        for (int x = 0; x <= MAX_X; x++) {
            positions.add(Position.newBuilder().setX(x).setY(row).build());
        }
        return positions;
    }

    @Override
    public void setLedColumn(int column, Color color) {
        List<Position> positions = getColumnPositions(column);
        List<ShortMessage> msgs = Led.generateOnMsgs(positions, color);
        setLeds(color, msgs);
    }

    private List<Position> getColumnPositions(int column) {
        if (column > MAX_Y) {
            logger.error("maximum y = {} but was {}.", MAX_Y, column, new IndexOutOfBoundsException("y position too large."));
        }
        List<Position> positions = new ArrayList<>(8);
        for (int y = 0; y <= MAX_Y; y++) {
            positions.add(Position.newBuilder().setX(column).setY(y).build());
        }
        return positions;
    }

    private void setLeds(Color color, List<ShortMessage> msgs) {
        msgs.forEach(msg -> this.receiver.send(msg, -1));
    }
}
