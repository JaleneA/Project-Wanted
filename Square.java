import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Square extends Thread {

   private JPanel panel;

   private int x;
   private int y;
   private int w;
   private int h;
   private int topY;

   private int dx;
   private int dy;

   private Rectangle2D.Double square;
   boolean isRunning;

   private Color backgroundColour;
   private Dimension dimension;

   public Square (JPanel p, int xPos, int yPos) {
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

   // Draw Square
   public void draw (Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      square = new Rectangle2D.Double(x, y, w, h);
      g2.setColor(Color.RED);
      g2.fill(square);
   }

   // Is On Square
   public boolean isOnSquare (int x, int y) {
      if (square == null)
            return false;
      return square.contains(x, y);
   }

   // Erase Square
   public void erase() {
      Graphics g = panel.getGraphics();
      g.setColor(backgroundColour);
      g.fillRect(x, y, w, h);
      g.dispose();
   }

   // Move Square
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
