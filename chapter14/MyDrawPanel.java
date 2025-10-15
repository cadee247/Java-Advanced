package chapter14;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class MyDrawPanel extends JPanel {

  public void paintComponent(Graphics g) {
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    Random random = new Random();
    int red = random.nextInt(256);
    int green = random.nextInt(256);
    int blue = random.nextInt(256);

    Color randomColor = new Color(red, green, blue);
    g.setColor(randomColor);
    g.fillOval(70, 70, 100, 100);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MyDrawPanel());
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}