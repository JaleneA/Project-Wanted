import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Diamond extends Shape {

   // Attributes
   private int w, h;
   boolean isRunning;
   private Color color;
   private JPanel panel;
   private Polygon diamond;
   private Shape lastCollided = null;

   // Constructor
   public Diamond(JPanel panel, int xPos, int yPos, int speedX, int speedY, boolean collisionEnabled) {
      super(xPos, yPos, speedX, speedY, collisionEnabled);  // x, y, dx, dy
      this.panel = panel;
      this.w = 50;
      this.h = 50;
      this.color = Color.YELLOW;
   }

   // Draw Diamond
   @Override
   public void draw(Graphics g) {
   Graphics2D g2 = (Graphics2D) g;
   double centerX = x + w / 2;
   double centerY = y + h / 2;
   int[] xPoints = {(int) centerX, (int) (centerX + w / 2), (int) centerX, (int) (centerX - w / 2)};
   int[] yPoints = {(int) (centerY - h / 2), (int) centerY, (int) (centerY + h / 2), (int) centerY};
   diamond = new Polygon(xPoints, yPoints, 4);
   g2.setColor(color);
   g2.fill(diamond);
}

   // Erase Diamond
   private void erase() {
      Graphics g = panel.getGraphics();
      g.setColor(panel.getBackground());
      g.fillRect(x, y, w, h);
      g.dispose();
   }

   // Move Diamond
   @Override
   public void move() {
      if (!panel.isVisible()) return;
      erase();

      x += speedX;
      y += speedY;

      if (y > panel.getHeight() + 10)
         y = 0;

      if (x > panel.getWidth() + 10)
         x = 0;

      if (panel instanceof GamePanel gamePanel) {
         Shape currCollided = null;

         for (Shape other : gamePanel.getShapesList()) {
               if (other != this && collidesWith(other) && this.isCollisionEnabled()) {
                  currCollided = other;
                  break;
               }
         }
         if (currCollided != null && currCollided != lastCollided) {
               swipeColor(currCollided);
               lastCollided = currCollided;
         } else if (currCollided == null) {
               lastCollided = null;
         }
      }

      panel.repaint();
    }

   // Movement
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

   @Override
   public void startMovement() {
      new Thread(this).start();
   }

   @Override
   public void stopMovement() {
      isRunning = false;
      this.interrupt();
   }

   // Is On Diamond
   @Override
   public boolean isOn(int x, int y) {
      if (diamond == null)
            return false;
      return diamond.contains(x, y);
   }

   @Override
   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double(x, y, w, h);
   }

   // Collision
   @Override
   public boolean collidesWith(Shape otherShape) {
       switch (otherShape) {
           case Square square -> {
               return getBoundingRectangle().intersects(square.getBoundingRectangle());
           }
           case Circle circle1 -> {
               return getBoundingRectangle().intersects(circle1.getBoundingRectangle());
           }
           case Diamond diamond1 -> {
               return getBoundingRectangle().intersects(diamond1.getBoundingRectangle());
           }
           case Triangle triangle1 -> {
               return getBoundingRectangle().intersects(triangle1.getBoundingRectangle());
           }
           default -> {
           }
       }
       return false;
   }

   public int getW() {
      return w;
    }

   public int getH() {
      return h;
    }

   public void setH(int h) {
      this.h = h;
   }

   public void setW(int w) {
         this.w = w;
   }

   @Override
   public Color getColor() {
      return color;
   }

   @Override
   public void setColor(Color color) {
      this.color = color;
   }

   public JPanel getPanel() {
      return panel;
   }

   public void setPanel(JPanel panel) {
      this.panel = panel;
   }
}