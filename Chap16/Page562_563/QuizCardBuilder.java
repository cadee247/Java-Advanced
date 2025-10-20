import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class QuizCardBuilder {
    private ArrayList<QuizCard> cardList = new ArrayList<>();

    // Text areas for inputting question and answer
    private JTextArea question;
    private JTextArea answer;

    // Main application window
    private JFrame frame;

    // Entry point of the program
    public static void main(String[] args) {
        new QuizCardBuilder().go(); // Creates instance and starts GUI
    }

    // Method to set up and display the GUI
    public void go() {
        frame = new JFrame("Quiz Card Builder"); // Window title
        JPanel mainPanel = new JPanel(); // Main panel for layout

        // Font for text areas
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        // Create question text area with scroll pane
        question = createTextArea(bigFont);
        JScrollPane qScroller = createScroller(question);

        // Create answer text area with scroll pane
        answer = createTextArea(bigFont);
        JScrollPane aScroller = createScroller(answer);

        // Add labels and scroll panes to main panel
        mainPanel.add(new JLabel("Question:"));
        mainPanel.add(qScroller);
        mainPanel.add(new JLabel("Answer:"));
        mainPanel.add(aScroller);

        // Create 'Next Card' button
        JButton nextButton = new JButton("Next Card");
        nextButton.addActionListener(e -> nextCard()); // Action listener
        mainPanel.add(nextButton); // Add button to panel

        // Create menu bar with 'File' menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        // 'New' menu item to clear the card list
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(e -> clearAll());

        // 'Save' menu item to save cards to file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> saveCard());

        // Add items to 'File' menu, then to menu bar
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        // Attach menu bar and main panel to the frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        // Set window size
        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    // Creates a JScrollPane that wraps the given JTextArea
    // Always shows vertical scrollbar, never shows horizontal scrollbar
    private JScrollPane createScroller(JTextArea textArea) {
        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroller;
    }

    // Creates a JTextArea with specified font and text wrapping enabled
    private JTextArea createTextArea(Font font) {
        JTextArea textArea = new JTextArea(6, 20); // 6 rows, 20 columns
        textArea.setLineWrap(true); // Wrap lines to fit width
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        textArea.setFont(font); // Set font style and size
        return textArea;
    }

    // Creates a new QuizCard from text fields and adds it to the list
    // Then clears the input fields for the next entry
    private void nextCard() {
        QuizCard card = new QuizCard(question.getText(), answer.getText());
        cardList.add(card);
        clearCard();
    }

    // Adds the current card to the list, then opens a file chooser dialog
    // Saves all cards to the selected file
    private void saveCard() {
        QuizCard card = new QuizCard(question.getText(), answer.getText());
        cardList.add(card);
        JFileChooser fileSave = new JFileChooser();
        fileSave.showSaveDialog(frame);
        saveFile(fileSave.getSelectedFile());
    }

    // Clears the entire list of cards and clears the input fields
    private void clearAll() {
        cardList.clear();
        clearCard();
    }

    // Clears the question and answer input fields, sets focus back to question
    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    // Writes all quiz cards to the given file
    // Each card is saved as "question/answer" on a new line
    private void saveFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/"); // Write question followed by separator
                writer.write(card.getAnswer() + "\n");  // Write answer followed by newline
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't write the cardList out: " + e.getMessage());
        }
    }
}
