import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

import static javax.sound.midi.ShortMessage.*;

public class BeatBoxFinal {
    // GUI component to display list of incoming messages/sequences from other users
    private JList<String> incomingList;

    // Text area for user to type their message
    private JTextArea userMessage;

    // List to hold checkboxes representing beats
    private ArrayList<JCheckBox> checkboxList;

    // Vector to store incoming messages from other users
    private Vector<String> listVector = new Vector<>();

    // Map to store other users' sequences by their user ID/message
    private HashMap<String, boolean[]> otherSeqsMap = new HashMap<>();

    // Stores this user's name
    private String userName;

    // Keeps track of the sequence number for messages from this user
    private int nextNum;

    // For sending data to the server
    private ObjectOutputStream out;

    // For receiving data from the server
    private ObjectInputStream in;

    // MIDI sequencer components
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;

    // Array of instrument names to be displayed in GUI
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat",
            "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
            "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo",
            "Open Hi Conga"};

    // Corresponding MIDI instrument key numbers
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    // ---------- MAIN ----------
    // Entry point of the application
    public static void main(String[] args) {
        // Use default name if no argument is given
        String name = (args.length > 0) ? args[0] : "Guest";
        new BeatBoxFinal().startUp(name);
    }

    // ---------- STARTUP ----------
    // Initializes the client connection and sets up the application
    public void startUp(String name) {
        userName = name;
        // open connection to the server
        try {
            // Attempt to connect to the server at localhost on port 4242
            // Note: The IP "1192.168.0.119" appears invalid; likely a typo for "192.168.0.119"
            Socket socket = new Socket("1192.168.0.119", 4242);

            // Create output stream for sending data to server
            out = new ObjectOutputStream(socket.getOutputStream());

            // Create input stream for receiving data from server
            in = new ObjectInputStream(socket.getInputStream());

            // Run a separate thread to listen for incoming data from server
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(new RemoteReader());
        } catch (Exception ex) {
            // If connection fails, print message and allow local play only
            System.out.println("Couldn’t connect — you’ll have to play alone.");
        }

        // Initialize MIDI system
        setUpMidi();

        // Build the graphical user interface
        buildGUI();
    }

    // ---------- BUILD GUI ----------
    // Constructs the graphical user interface for the beatbox
    public void buildGUI() {
        JFrame frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Vertical box for buttons and other controls
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        // Button to start playing the beat
        JButton start = new JButton("Start");
        start.addActionListener(e -> buildTrackAndStart());
        buttonBox.add(start);

        // Button to stop playing the beat
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> sequencer.stop());
        buttonBox.add(stop);

        // Button to increase tempo
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(e -> changeTempo(1.03f));
        buttonBox.add(upTempo);

        // Button to decrease tempo
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(e -> changeTempo(0.97f));
        buttonBox.add(downTempo);

        // Button to send the message and beat pattern to the server
        JButton sendIt = new JButton("Send It");
        sendIt.addActionListener(e -> sendMessageAndTracks());
        buttonBox.add(sendIt);

        // Text area for user messages
        userMessage = new JTextArea();
        userMessage.setLineWrap(true);
        userMessage.setWrapStyleWord(true);
        JScrollPane messageScroller = new JScrollPane(userMessage);
        buttonBox.add(messageScroller);

        // List to display incoming messages/sequences
        incomingList = new JList<>();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector);

        // Vertical box for instrument labels
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String instrumentName : instrumentNames) {
            JLabel instrumentLabel = new JLabel(instrumentName);
            instrumentLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1));
            nameBox.add(instrumentLabel);
        }

        // Add components to the background panel
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        frame.getContentPane().add(background);

        // Grid for checkboxes (16x16 for instruments and beats)
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);

        JPanel mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        // Initialize checkboxes for beat patterns
        checkboxList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        // Set frame properties and display
        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    // ---------- MIDI SETUP ----------
    // Sets up the MIDI sequencer, sequence, and track
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

    // ---------- BUILD TRACK & PLAY ----------
    // Builds the MIDI track from checkboxes and starts playback
    private void buildTrackAndStart() {
        ArrayList<Integer> trackList;

        // Clear existing track
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        // For each instrument
        for (int i = 0; i < 16; i++) {
            trackList = new ArrayList<>();
            int key = instruments[i];

            // Check each beat for the instrument
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList.add(key);
                } else {
                    trackList.add(null);
                }
            }

            // Add events to the track
            makeTracks(trackList);
            // Add control change event (sustain pedal off)
            track.add(makeEvent(CONTROL_CHANGE, 1, 127, 0, 16));
        }

        // Add program change event for percussion channel
        track.add(makeEvent(PROGRAM_CHANGE, 9, 1, 0, 15));

        try {
            // Set and start the sequencer
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- TEMPO CONTROL ----------
    // Adjusts the playback tempo by a multiplier
    private void changeTempo(float tempoMultiplier) {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor(tempoFactor * tempoMultiplier);
    }

    // ---------- SEND MESSAGE + TRACK ----------
    // Sends the user's message and beat pattern to the server
    private void sendMessageAndTracks() {
        // Capture current checkbox states
        boolean[] checkboxState = new boolean[256];
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            if (check.isSelected()) {
                checkboxState[i] = true;
            }
        }
        try {
            // Write username, sequence number, and message
            out.writeObject(userName + nextNum++ + ": " + userMessage.getText());
            // Write beat pattern
            out.writeObject(checkboxState);
        } catch (IOException e) {
            System.out.println("Could not send data to server.");
            e.printStackTrace();
        }
        // Clear message field
        userMessage.setText("");
    }

    // ---------- LISTENER FOR INCOMING SEQUENCES ----------
    // Listener for selecting an incoming sequence from the list
    public class MyListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent lse) {
            if (!lse.getValueIsAdjusting()) {
                String selected = incomingList.getSelectedValue();
                if (selected != null) {
                    // Get the selected sequence's state
                    boolean[] selectedState = otherSeqsMap.get(selected);
                    // Update local checkboxes
                    changeSequence(selectedState);
                    // Stop current playback and rebuild/start with new sequence
                    sequencer.stop();
                    buildTrackAndStart();
                }
            }
        }
    }

    // ---------- LOAD SELECTED SEQUENCE ----------
    // Updates the checkboxes to match a selected incoming sequence
    private void changeSequence(boolean[] checkboxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            check.setSelected(checkboxState[i]);
        }
    }

    // ---------- CREATE MIDI EVENTS ----------
    // Adds NOTE_ON and NOTE_OFF events to the track for a list of keys
    public void makeTracks(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            Integer instrumentKey = list.get(i);
            if (instrumentKey != null) {
                // Add note on event
                track.add(makeEvent(NOTE_ON, 9, instrumentKey, 100, i));
                // Add note off event one tick later
                track.add(makeEvent(NOTE_OFF, 9, instrumentKey, 100, i + 1));
            }
        }
    }

    // ---------- HELPER: CREATE MIDI EVENT ----------
    // Creates a MIDI event with the given parameters
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

    // ---------- THREAD: RECEIVE DATA FROM SERVER ----------
    // Runnable to handle incoming data from the server in a separate thread
    public class RemoteReader implements Runnable {
        public void run() {
            try {
                Object obj;
                // Continuously read objects from the input stream
                while ((obj = in.readObject()) != null) {
                    System.out.println("Got an object from server: " + obj.getClass());
                    // Read message (username and text)
                    String nameToShow = (String) obj;
                    // Read beat pattern
                    boolean[] checkboxState = (boolean[]) in.readObject();
                    // Store in map and add to list vector
                    otherSeqsMap.put(nameToShow, checkboxState);
                    listVector.add(nameToShow);
                    // Update the incoming list UI
                    incomingList.setListData(listVector);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}