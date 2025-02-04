import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Triangle {

   private JPanel panel;

   private int x;
   private int y;

   private int b;
   private int h;

   private int dx;
   private int dy;

   Polygon triangle;

   private Color backgroundColour;
   private Dimension dimension;

   public Triangle(JPanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground ();

        x = xPos;
        y = yPos;

        dx = 0;
        dy = 10;

        b = 50;
        h = 50;
    }

    // Draw Triangle
    public void draw() {
        Graphics g = panel.getGraphics();
        g.setColor(Color.GREEN);

        triangle = new Polygon(
            new int[] {x, x + b, x + b / 2},
            new int[] {y + h, y + h, y},
            3
        );

        g.fillPolygon(triangle);
        g.dispose();
    }

    // Is On Triangle
   public boolean isOnTriangle (int x, int y) {
    if (triangle == null)
        return false;

    return triangle.contains(x, y);
   }

    // Erase Triangle - Square Code
    // public void erase () {
    //     Graphics g = panel.getGraphics ();
    //     Graphics2D g2 = (Graphics2D) g;

    //     g2.setColor (backgroundColour);
    //     g2.fill (new Rectangle2D.Double (x, y, width, height));

    //     g.dispose();
    // }

    public int getB() {
        return b;
    }

    public int getH() {
        return h;
    }

}