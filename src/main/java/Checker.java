import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Checker {

    public static final String APC_KEY_25 = "APC Key 25";
    public static final String EXTERNAL_MIDI_PORT = "External MIDI Port";

    public static void main(String... args) throws MidiUnavailableException, InvalidMidiDataException {
        Checker checker = new Checker();

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        System.out.println("MidiDeviceInfos:" + checker.toString(infos));
//        List<MidiDevice> midiDevices = checker.getDevices(infos);
//          soutv printDevices(midiDevices);

        MidiDevice apcDevice = checker.getAPC(infos);
        apcDevice.open();
        Receiver receiver = apcDevice.getReceiver();

        ShortMessage myMsg = new ShortMessage();
        // Start playing the note Middle C (60),
        // moderately loud (velocity = 93).
        int note = 22;
        int velocity = 3; //
        myMsg.setMessage(ShortMessage.NOTE_ON, 0, note, velocity );
        long timeStamp = -1;
        receiver.send(myMsg, timeStamp);
        apcDevice.close();
    }

    private static boolean testAPC(MidiDevice.Info info) {
        return (info.getName().contains(APC_KEY_25)) && info.getDescription().contains(EXTERNAL_MIDI_PORT);
    }


    private String toString(MidiDevice.Info[] infos) {
        StringBuffer pretty = new StringBuffer();
        for(MidiDevice.Info info : infos){
            pretty.append("\n\nname = " + info.getName());
            pretty.append("\ndesc = " + info.getDescription());
            pretty.append("\nvendor = " + info.getVendor());
            pretty.append("\nversion = " + info.getVersion());
        }
        return pretty.toString();
    }

    private List<MidiDevice> getDevices(MidiDevice.Info[] infos) throws MidiUnavailableException {
        List<MidiDevice> midiDevices = new ArrayList<>();
        for(MidiDevice.Info info : infos) {
            midiDevices.add(MidiSystem.getMidiDevice(info));
        }
        return midiDevices;
    }

    // TODO: BROKEN??
    private void printDevices(List<MidiDevice> devices) throws MidiUnavailableException {

        for (MidiDevice device : devices) {
            device.open();
            final List<Receiver> receivers = device.getReceivers();
            receivers.stream()
                    .forEach(receiver -> System.out.println("receiver = " + receiver));
            final List<Transmitter> transmitters = device.getTransmitters();
            transmitters.stream()
                    .forEach(transmitter -> System.out.println("transmitter = " + transmitter));
            device.close();
        }
    }

    private MidiDevice getAPC(MidiDevice.Info[] infos) throws MidiUnavailableException {
        List<MidiDevice.Info> infosAPC = Arrays.stream(infos)
                .filter(Checker::testAPC)
                .collect(Collectors.toList());
        if (infosAPC.size()!=1) {
            throw new RuntimeException("Require 1 connected APC, but detected: " + infosAPC.size());
        }
        return MidiSystem.getMidiDevice(infosAPC.get(0));
    }
}
