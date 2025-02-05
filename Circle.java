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
   private int w;
   private int h;

   Ellipse2D.Double circle;
   boolean isRunning;

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

      w = 50;
      h = 50; 

      dx = 0;
      dy = 2;
   }

   // Draw Circle
   public void draw (Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      circle = new Ellipse2D.Double(x, y, w, h);
      g2.setColor(Color.BLUE); 
      g2.fill(circle);
   }

   // Is On Circle
   public boolean isOnCircle (int x, int y) {
      if (circle == null)
            return false;

      return circle.contains(x, y);
   }

   // Erase Circle
   public void erase() {
      Graphics g = panel.getGraphics();
      g.setColor(backgroundColour);
      g.fillRect(x, y, w, h);
      g.dispose();
   }

   // Move Circle
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