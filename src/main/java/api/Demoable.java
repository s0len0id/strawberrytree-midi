package api;

import apc.Color;

import javax.sound.midi.InvalidMidiDataException;

public interface Demoable {

    void setLedRow(int row, Color color) throws InvalidMidiDataException;
    void setLedColumn(int column, Color color) throws InvalidMidiDataException;
}
