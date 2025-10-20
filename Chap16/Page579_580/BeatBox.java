import javax.sound.midi.*;     // MIDI sound library
import javax.swing.*;          // GUI components
import java.awt.*;             // Layout and graphics
import java.io.*;              // File I/O for saving/restoring
import java.util.ArrayList;    // Dynamic checkbox list

import static javax.sound.midi.ShortMessage.*; // MIDI message constants

public class BeatBox {
    private ArrayList<JCheckBox> checkboxList; // Holds 256 checkboxes (16 instruments × 16 beats)
    private Sequencer sequencer;               // MIDI sequencer
    private Sequence sequence;                 // MIDI sequence
    private Track track;                       // MIDI track

    // Instrument names and corresponding MIDI codes
    String[] instrumentNames = {
            "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell",
            "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGUI(); // Launch the GUI
    }

    public void buildGUI() {
        // Main window setup
        JFrame frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background = new JPanel(new BorderLayout());
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button panel (Start, Stop, Tempo, Save, Restore)
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(e -> buildTrackAndStart());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> sequencer.stop());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(e -> changeTempo(1.03f));
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(e -> changeTempo(0.97f));
        buttonBox.add(downTempo);

        JButton save = new JButton("serializeIt");
        save.addActionListener(e -> writeFile());
        buttonBox.add(save);

        JButton restore = new JButton("restore");
        restore.addActionListener(e -> readFile());
        buttonBox.add(restore);

        // Instrument name labels
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String instrumentName : instrumentNames) {
            JLabel instrumentLabel = new JLabel(instrumentName);
            instrumentLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1));
            nameBox.add(instrumentLabel);
        }

        // Add panels to layout
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        frame.getContentPane().add(background);

        // 16×16 grid of checkboxes
        JPanel mainPanel = new JPanel(new GridLayout(16, 16, 2, 1));
        background.add(BorderLayout.CENTER, mainPanel);

        checkboxList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi(); // Initialize MIDI system

        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    // MIDI setup
    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Build MIDI track from checkbox selections and start playback
    private void buildTrackAndStart() {
        int[] trackList;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + 16 * i);
                trackList[j] = jc.isSelected() ? key : 0;
            }

            makeTracks(trackList);
            track.add(makeEvent(CONTROL_CHANGE, 1, 127, 0, 16));
        }

        track.add(makeEvent(PROGRAM_CHANGE, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Adjust tempo by multiplier
    private void changeTempo(float tempoMultiplier) {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor(tempoFactor * tempoMultiplier);
    }

    // Create MIDI events from track list
    private void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(makeEvent(NOTE_ON, 9, key, 100, i));
                track.add(makeEvent(NOTE_OFF, 9, key, 100, i + 1));
            }
        }
    }

    // Utility to create MIDI events
    public static MidiEvent makeEvent(int cmd, int chnl, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage();
            msg.setMessage(cmd, chnl, one, two);
            event = new MidiEvent(msg, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    // Save checkbox state to file
    private void writeFile() {
        boolean[] checkboxState = new boolean[256];
        for (int i = 0; i < 256; i++) {
            checkboxState[i] = checkboxList.get(i).isSelected();
        }

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Checkbox.ser"))) {
            os.writeObject(checkboxState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Restore checkbox state from file
    private void readFile() {
        boolean[] checkboxState = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("Checkbox.ser"))) {
            checkboxState = (boolean[]) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 256; i++) {
            checkboxList.get(i).setSelected(checkboxState[i]);
        }

        sequencer.stop();
        buildTrackAndStart();
    }
}

//What is Binary Data Serialization?
//Binary serialization is the process of converting an object's state (its instance variables and referenced objects) into a compact sequence of bytes (binary data) that can be stored in a file, sent over a network, or held in memory. This binary format is not human-readable (it looks like gibberish if opened in a text editor) but is efficient for machines. In Java, this is done using classes like ObjectOutputStream, which "flattens" the object graph into bytes.
//What is Deserialization?
//Deserialization is the reverse: taking the binary byte stream and reconstructing the original object(s) in memory, complete with their state. Java uses ObjectInputStream for this. The JVM allocates space on the heap, restores values, and handles references automatically—without calling constructors (except for non-serializable superclasses).
//Different Types of Serialization:
//While the chapter focuses on Java's built-in binary serialization, here are common types (including non-binary for context):
//
//Binary Serialization (e.g., Java's ObjectOutputStream): Default in Java; stores data as raw bytes. Seen in this chapter's game characters example.
//        XML Serialization (e.g., XMLEncoder in Java, or JAXB): Converts objects to XML text. Human-readable but larger files. Good for interoperability with web services. Not covered here.
//        JSON Serialization (e.g., using Gson or Jackson libraries): Converts to JSON text. Popular for web APIs; lightweight and readable. Not in the book, as it requires external libs.
//Protocol Buffers or Avro (external formats): Binary but schema-based for efficiency in big data systems. Not Java-native.
//
//Why Are They Good?
//
//Binary (as in the chapter): Compact (smaller files), fast (less overhead), automatic (handles graphs without code), and secure for Java-internal use (hard to tamper without Java). Ideal for games or apps where performance matters and data stays within Java.
//        XML/JSON: Readable/editable by humans, cross-platform (works with non-Java apps), and flexible for evolving data structures. Good for data exchange, like APIs or configs.
//Overall benefits: Enables persistence, reduces memory use (e.g., save and reload large objects), and supports distributed systems (e.g., send objects over networks). Drawbacks: Binary can break with class changes (hence serialVersionUID), and non-binary formats may be slower/larger.