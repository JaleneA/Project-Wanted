import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Triangle extends Thread {

   private JPanel panel;

   private int x;
   private int y;

   private int b;
   private int h;

   private int dx;
   private int dy;

   Polygon triangle;
   boolean isRunning;

   private Color backgroundColour;
   private Dimension dimension;

   public Triangle(JPanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground ();

        x = xPos;
        y = yPos;

        dx = 0;
        dy = 2;

        b = 50;
        h = 50;
    }

    // Draw Triangle
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        triangle = new Polygon(
            new int[] {x, x + b, x + b / 2},
            new int[] {y + h, y + h, y},
            3
        );
        g.fillPolygon(triangle);
    }

    // Is On Triangle
   public boolean isOnTriangle (int x, int y) {
    if (triangle == null)
        return false;

    return triangle.contains(x, y);
   }

    // Erase Triangle
    public void erase() {
        Graphics g = panel.getGraphics();
        g.setColor(backgroundColour);
        g.fillRect(x, y, b, h);
        g.dispose();
     }


    // Move Triangle
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

    public int getB() {
        return b;
    }

    public int getH() {
        return h;
    }

}