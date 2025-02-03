import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Triangle {

   private JPanel panel;

   private int x;
   private int y;

   private int base;
   private int height;

   private int dx;
   private int dy;

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

        base = 50;
        height = 50;
    }

    public void draw() {
        Graphics g = panel.getGraphics();
        g.setColor(Color.GREEN);

        Polygon triangle = new Polygon(
            new int[] {x, x + base, x + base / 2},
            new int[] {y + height, y + height, y},
            3
        );

        g.fillPolygon(triangle);
        g.dispose();
    }
}