package Chap15.Page515;

import javax.swing.*;
import java.awt.*;

public class Button1 {
    public static void main(String[] args) {
        Button1 gui = new Button1();  // Create an instance of the Button1 class
        gui.go();                    // Call the go method to set up the GUI
    }

    public void go() {
        JFrame frame = new JFrame();                      // Create a window (frame)
//        JButton button = new JButton("click me");
        JButton button = new JButton("click like you mean it");

        // Add the button to the right side (EAST) of the frame's content area
        frame.getContentPane().add(BorderLayout.EAST, button);

        frame.setSize(200, 200);    // Set the window size to 200x200 pixels
        frame.setVisible(true);                 // Make the window visible on the screen
    }
}
