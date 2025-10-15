package Chap15.Page511;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class NestedPanelsVertical {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Nested Panels Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null); // Center the window

        // Create Panel A and give it a titled border
        JPanel panelA = new JPanel();
        panelA.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Panel A",
                TitledBorder.CENTER,
                TitledBorder.BOTTOM
        ));

        // Create Panel B and give it a titled border
        JPanel panelB = new JPanel();
        panelB.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Panel B",
                TitledBorder.CENTER,
                TitledBorder.BOTTOM
        ));

        // Set layout for Panel B to stack buttons vertically
        panelB.setLayout(new BoxLayout(panelB, BoxLayout.Y_AXIS));

        // Add buttons to Panel B
        panelB.add(new JButton("button 1"));
        panelB.add(Box.createRigidArea(new Dimension(0, 5))); // spacer
        panelB.add(new JButton("button 2"));
        panelB.add(Box.createRigidArea(new Dimension(0, 5))); // spacer
        panelB.add(new JButton("button 3"));

        // Add Panel B to Panel A
        panelA.add(panelB);

        // Add Panel A to the frame
        frame.add(panelA);

        // Make the frame visible
        frame.setVisible(true);
    }
}





