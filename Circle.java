import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class Circle extends Thread {

   private JPanel panel;

   private int x;
   private int y;

   Ellipse2D.Double circle;

   private int dx;
   private int dy;

   private Color backgroundColour;
   private Dimension dimension;

   public Circle (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();

      x = xPos;
      y = yPos;

      dx = 0;
      dy = 10;
   }

   public void draw () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;
      circle = new Ellipse2D.Double(x, y, 50, 50);
      g2.setColor(Color.BLUE); 
      g2.fill(circle);
      g.dispose();
   }
}