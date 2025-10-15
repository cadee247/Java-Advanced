package Chap15.Page530_533;

import javax.sound.midi.*;              // For MIDI music functionality
import javax.swing.*;                   // For creating GUI components like buttons and checkboxes
import java.awt.*;                      // For layout and window design
import java.util.ArrayList;             // For using ArrayList to hold checkboxes
import static javax.sound.midi.ShortMessage.*; // Import MIDI message constants

public class BeatBox {

    private ArrayList<JCheckBox> checkboxList;  // List to store all checkboxes for beats
    private Sequencer sequencer;                // MIDI sequencer to play the music
    private Sequence sequence;                  // Holds the musical sequence
    private Track track;                        // A track inside the sequence

    // Names of the instruments that will appear on the interface
    String[] instrumentNames = {
            "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare",
            "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
            "Maracas", "Whistle", "Low Conga", "Cowbell",
            "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };

    // MIDI key numbers for each instrument
    int[] instruments = {
            35, 42, 46, 38, 49, 39, 50, 60,
            70, 72, 64, 56, 58, 47, 67, 63
    };

    // Main method: Entry point of the program
    public static void main(String[] args) {
        new BeatBox().buildGUI();  // Creates a BeatBox object and calls buildGUI to set up the interface
    }

    // Builds and displays the graphical user interface (GUI) for the BeatBox
    public void buildGUI() {
        JFrame frame = new JFrame("Cyber BeatBox"); // Creates a window titled "Cyber BeatBox"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the program when the window is closed

        BorderLayout layout = new BorderLayout(); // Uses BorderLayout to organize components
        JPanel background = new JPanel(layout);   // Creates a main panel with BorderLayout
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds 10-pixel padding around the panel

        Box buttonBox = new Box(BoxLayout.Y_AXIS); // Creates a vertical box to hold control buttons

        // Creates and configures the Start button
        JButton start = new JButton("Start");
        start.addActionListener(e -> buildTrackAndStart()); // When clicked, builds and plays the MIDI track
        buttonBox.add(start);

        // Creates and configures the Stop button
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> sequencer.stop()); // When clicked, stops the MIDI playback
        buttonBox.add(stop);

        // Creates and configures the Tempo Up button
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(e -> changeTempo(1.03f)); // Increases playback speed by 3% when clicked
        buttonBox.add(upTempo);

        // Creates and configures the Tempo Down button
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(e -> changeTempo(0.97f)); // Decreases playback speed by 3% when clicked
        buttonBox.add(downTempo);

        // Creates a vertical box to hold instrument name labels
        Box nameBox = new Box(BoxLayout.Y_AXIS);

        // Adds a label for each instrument name to the name box
        for (String instrumentName : instrumentNames) {
            JLabel instrumentLabel = new JLabel(instrumentName); // Creates a label with the instrument name
            instrumentLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1)); // Adds small padding around the label
            nameBox.add(instrumentLabel); // Adds the label to the vertical box
        }

        // Places the button box (Start, Stop, etc.) on the right side of the main panel
        background.add(BorderLayout.EAST, buttonBox);

        // Places the instrument name labels on the left side of the main panel
        background.add(BorderLayout.WEST, nameBox);

        // Adds the main panel to the center of the frame
        frame.getContentPane().add(background);

        // Creates a 16x16 grid layout for checkboxes (16 instruments x 16 beats)
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1); // Sets a 1-pixel vertical gap between checkboxes
        grid.setHgap(2); // Sets a 2-pixel horizontal gap between checkboxes

        // Creates a panel to hold the checkbox grid
        JPanel mainPanel = new JPanel(grid);

        // Adds the checkbox grid panel to the center of the main background panel
        background.add(BorderLayout.CENTER, mainPanel);

        // Initializes the list to store all 256 checkboxes
        checkboxList = new ArrayList<>();

        // Creates and adds 256 checkboxes to the grid and the list
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox(); // Creates a new checkbox
            c.setSelected(false); // Sets it to unchecked by default
            checkboxList.add(c); // Adds it to the list for tracking
            mainPanel.add(c); // Adds it to the grid panel
        }

        // Sets up the MIDI system for sound playback
        setUpMidi();

        // Sets the window's initial position and size
        frame.setBounds(50, 50, 300, 300);

        // Resizes the window to fit all components
        frame.pack();

        // Makes the window visible
        frame.setVisible(true);
    }

    // Initializes the MIDI system for playing sounds
    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer(); // Gets a MIDI sequencer
            sequencer.open(); // Opens the sequencer for use
            sequence = new Sequence(Sequence.PPQ, 4); // Creates a sequence with 4 ticks per beat
            track = sequence.createTrack(); // Creates a new track in the sequence
            sequencer.setTempoInBPM(120); // Sets default tempo to 120 beats per minute
        } catch (Exception e) {
            e.printStackTrace(); // Prints error details if MIDI setup fails
        }
    }

    // Builds a MIDI track from selected checkboxes and starts playing it
    private void buildTrackAndStart() {
        int[] trackList;

        // Clears the existing track to start fresh
        sequence.deleteTrack(track);
        track = sequence.createTrack(); // Creates a new track

        // Loops through each of the 16 instruments
        for (int i = 0; i < 16; i++) {
            trackList = new int[16]; // Array to store beat pattern for one instrument
            int key = instruments[i]; // Gets the MIDI key for the current instrument

            // Loops through 16 beats for the current instrument
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + 16 * i); // Gets the checkbox for this beat
                if (jc.isSelected()) {
                    trackList[j] = key; // If checked, marks the instrument to play at this beat
                } else {
                    trackList[j] = 0; // If unchecked, no sound at this beat
                }
            }

            // Adds the instrument's beat pattern to the MIDI track
            makeTracks(trackList);

            // Adds a control change event to ensure sequencer responsiveness
            track.add(makeEvent(CONTROL_CHANGE, 1, 127, 0, 16));
        }

        // Adds a program change event for MIDI compatibility
        track.add(makeEvent(PROGRAM_CHANGE, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence); // Loads the sequence into the sequencer
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); // Sets the track to loop indefinitely
            sequencer.setTempoInBPM(120); // Sets the tempo to 120 BPM
            sequencer.start(); // Starts playing the track
        } catch (Exception e) {
            e.printStackTrace(); // Prints error details if playback fails
        }
    }

    // Adjusts the playback speed by multiplying the current tempo by a factor
    private void changeTempo(float tempoMultiplier) {
        float tempoFactor = sequencer.getTempoFactor(); // Gets the current tempo factor
        sequencer.setTempoFactor(tempoFactor * tempoMultiplier); // Adjusts tempo (e.g., 1.03 for faster, 0.97 for slower)
    }

    // Adds MIDI note events to the track for an instrument's beat pattern
    private void makeTracks(int[] list) {
        // Loops through 16 beats for the instrument
        for (int i = 0; i < 16; i++) {
            int key = list[i]; // Gets the MIDI key for this beat
            if (key != 0) {
                // If the beat is active, adds a NOTE_ON event at the current tick
                track.add(makeEvent(NOTE_ON, 9, key, 100, i));
                // Adds a NOTE_OFF event one tick later to stop the sound
                track.add(makeEvent(NOTE_OFF, 9, key, 100, i + 1));
            }
        }
    }

    // Creates a MIDI event with specified command, channel, data, and timing
    public static MidiEvent makeEvent(int cmd, int chnl, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage(); // Creates a MIDI message
            msg.setMessage(cmd, chnl, one, two); // Sets message details (e.g., NOTE_ON, channel, note, velocity)
            event = new MidiEvent(msg, tick); // Wraps the message in an event at the specified tick
        } catch (Exception e) {
            e.printStackTrace(); // Prints error details if event creation fails
        }
        return event; // Returns the MIDI event
    }
}