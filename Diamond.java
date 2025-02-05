import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Diamond extends Thread{

   private JPanel panel;

   private int x;
   private int y;

   private int w;
   private int h;

   private int dx;
   private int dy;

   private Polygon diamond;
   boolean isRunning;

   private double angle = Math.toRadians(45);

   private Color backgroundColour;
   private Dimension dimension;

   public Diamond (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();
      x = xPos;
      y = yPos;

      dx = 0;
      dy = 2;

      w = 50;
      h = 50;
   }

   // Draw Diamond
   public void draw(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.YELLOW);

      double centerX = x + w / 2;
      double centerY = y + h / 2;

      int[] xPoints = { (int) centerX, (int) (centerX + w / 2), (int) centerX, (int) (centerX - w / 2) };
      int[] yPoints = { (int) (centerY - h / 2), (int) centerY, (int) (centerY + h / 2), (int) centerY };

      diamond = new Polygon(xPoints, yPoints, 4);
      g2.fill(diamond);
   }

   // Is On Diamond
   public boolean isOnDiamond (int x, int y) {
      if (diamond == null)
            return false;
      return diamond.contains(x, y);
   }

   // Erase Diamond
   public void erase() {
      Graphics g = panel.getGraphics();
      g.setColor(backgroundColour);
      g.fillRect(x, y, w, h);
      g.dispose();
   }

   // Move Diamond
   public void move() {
      if (!panel.isVisible ()) return;
      erase();
      int panelHeight = panel.getHeight();

      x = x + dx;
      y = y + dy;

      if (y > panelHeight + 10)
         y = 0;

      panel.repaint();
   }

   @Override
   public void run() {
      isRunning = true;
      while (isRunning) {
            move();
            try {
               Thread.sleep(20);
            } catch (InterruptedException e) {
               break;
            }
      }
   }

   public void stopRunning() {
      isRunning = false;
      this.interrupt();
  }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
