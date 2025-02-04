import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Diamond {

   private JPanel panel;

   private int x;
   private int y;

   private int width;
   private int height;

   private int dx;
   private int dy;

   private Polygon diamond;

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

      width = 50;
      height = 50;
   }

   // Draw Diamond
   public void draw() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.YELLOW);

      double centerX = x + width / 2;
      double centerY = y + height / 2;

      int[] xPoints = { (int) centerX, (int) (centerX + width / 2), (int) centerX, (int) (centerX - width / 2) };
      int[] yPoints = { (int) (centerY - height / 2), (int) centerY, (int) (centerY + height / 2), (int) centerY };

      diamond = new Polygon(xPoints, yPoints, 4);
      g2.fill(diamond);
      g.dispose();
   }

   // Is On Diamond
   public boolean isOnDiamond (int x, int y) {
      if (diamond == null)
            return false;
      return diamond.contains(x, y);
   }
}
