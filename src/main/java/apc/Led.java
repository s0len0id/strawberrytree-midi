package apc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

class Led {
    private final static Logger logger = LoggerFactory.getLogger(Led.class);
    private static final int CHANNEL = 0;

    static List<ShortMessage> generateOnMsgs(List<Position> positions, Color color) {

        List<ShortMessage> msgs = new ArrayList<>();

        positions.forEach(position -> {
            ShortMessage ledOnMsg = new ShortMessage();
            try {
                ledOnMsg.setMessage(ShortMessage.NOTE_ON, CHANNEL, Position.toNote(position), color.velocity());
            } catch (InvalidMidiDataException e) {
                logger.error("erroneous midi message created for position={}", position, new RuntimeException(e));
            }
            msgs.add(ledOnMsg);
        });
        return msgs;
    }
}
