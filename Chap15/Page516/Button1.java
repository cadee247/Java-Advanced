package Chap15.Page516;

import javax.swing.*;
import java.awt.*;

public class Button1 {
    public static void main(String[] args) {
        Button1 gui = new Button1();  // Create an instance of the Button1 class
        gui.go();                    // Call the go method to set up the GUI
    }

    public void go() {
        JFrame frame = new JFrame();                      // Create a window (frame)

        JButton button = new JButton("There is no spoon...");
        Font bigFont = new Font("serif", Font.BOLD, 28);
        button.setFont(bigFont);
        frame.getContentPane().add(BorderLayout.NORTH, button);

        frame.setSize(200, 200);    // Set the window size to 200x200 pixels
        frame.setVisible(true);                 // Make the window visible on the screen
    }
}
