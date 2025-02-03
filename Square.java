import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Square {

   private JPanel panel;

   private int x;
   private int y;

   private int width;
   private int height;

   private int dx;
   private int dy;

   private Rectangle2D.Double square;

   private Color backgroundColour;
   private Dimension dimension;

   public Square (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();
      x = xPos;
      y = yPos;

      dx = 10;
      dy = 0;

      width = 50;
      height = 50;
   }

   public void draw () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;

      square = new Rectangle2D.Double(x, y, width, height);
      g2.setColor(Color.RED);
      g2.fill(square);
      g.dispose();
   }
}
