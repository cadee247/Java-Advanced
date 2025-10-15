package chapter14;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class CatPanel extends JPanel implements ImageObserver {
  public void paintComponent(Graphics g) {
    Image image = new ImageIcon("catzilla.jpg").getImage();

    g.drawImage(image, 3, 4, this);
  }

  public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
    return false;
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.add(new CatPanel());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}
