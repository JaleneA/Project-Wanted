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
   private int w;
   private int h;

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

      w = 50;
      h = 50;
   }

   // Draw Square
   public void draw () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;

      square = new Rectangle2D.Double(x, y, w, h);
      g2.setColor(Color.RED);
      g2.fill(square);
      g.dispose();
   }

   // Is On Square
   public boolean isOnSquare (int x, int y) {
      if (square == null)
            return false;
      return square.contains(x, y);
   }

   // Erase Square
   public void erase() {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor (backgroundColour);
      g2.fill (new Rectangle2D.Double (x, y, w, h));

      g.dispose();
   }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
