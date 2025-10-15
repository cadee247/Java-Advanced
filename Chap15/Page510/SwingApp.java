package Chap15.Page510;

import javax.swing.*;
import java.awt.*;

public class SwingApp {
    public static void main(String[] args) {
        // Step 1: Create a window (JFrame)
        JFrame frame = new JFrame("My First Swing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Step 2: Create components
        JButton button = new JButton("Click Me!");
        JCheckBox checkBox = new JCheckBox("Choose Me");

        // Step 3: Create a panel to group components
        JPanel panel = new JPanel();
        panel.add(button);
        panel.add(checkBox);

        // Step 4: Add the panel to the frame
        frame.getContentPane().add(panel);

        // Step 5: Show the window
        frame.setVisible(true);
    }
}

