package chapter14;

import javax.swing.*;
import java.awt.event.*;

class MyGui {
  private JFrame frame;
  private JLabel label;

  void gui() {
    // code to instantiate the two listeners and register one
    // with the color button and the other with the label button
  }
}

class ColorButtonListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
//    frame.repaint();
  }
}


class LabelButtonListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
//    label.setText("That hurt!");
  }
}

