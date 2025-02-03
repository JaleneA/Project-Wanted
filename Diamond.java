import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Diamond {

   private JPanel panel;

   private int x;
   private int y;

   private int width;
   private int height;

   private int dx;
   private int dy;

   private Rectangle2D.Double diamond;

   private double angle = Math.toRadians(45);

   private Color backgroundColour;
   private Dimension dimension;

   public Diamond (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();
      x = xPos;
      y = yPos;

      dx = 10;
      dy = 0;

      width = 40;
      height = 40;
   }

   public void draw () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.YELLOW);
      g2.translate(x + width / 2, y + height / 2);
      g2.rotate(angle);
      diamond = new Rectangle2D.Double(-width / 2, -height / 2, width, height);
      g2.fill(diamond);
      g2.setTransform(g2.getTransform());
      g.dispose();
   }
}
