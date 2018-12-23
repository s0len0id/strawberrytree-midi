package apc;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

public class Led {
    public static final int CHANNEL = 0;

    public static ShortMessage on(Position position, Color color) throws InvalidMidiDataException {
        ShortMessage myMsg = new ShortMessage();
        myMsg.setMessage(ShortMessage.NOTE_ON, CHANNEL, Position.toNote(position), color.velocity());
        return myMsg;
    }

    public static ShortMessage off(Position position) throws InvalidMidiDataException {
        return on(position, Color.OFF);
    }

}
