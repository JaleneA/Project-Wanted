import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

   Bat bat;
   Alien alien;

   // Shapes
   Square square;
   Diamond diamond;
   Triangle triangle;
   Circle circle;

   public enum Shapes {
      SQUARE, DIAMOND, CIRCLE, TRIANGLE, NONE
   }

   public GamePanel() {
      square = null;
      diamond = null;
      triangle = null;
      circle = null;

      bat = null;
      alien = null;
   }

   public void createGameEntities() {
      square = new Square(this, 20, 50);
      diamond = new Diamond(this, 120, 50);
      triangle = new Triangle(this, 220, 50);
      circle = new Circle(this, 320, 50);
   }

   public void drawGameEntities(Graphics g) {

      // Square
      if (square != null) {
         square.draw(g);
      }

      // Diamond
      if (diamond != null) {
         diamond.draw(g);
      }

      // Triangle
      if (triangle != null) {
         triangle.draw(g);
      }

      // Circle
      if (circle != null) {
         circle.draw(g);
      }

      //  if (bat != null) {
      //    bat.draw(g);
      //  }
   }

   public void updateGameEntities(int direction) {
      if (bat == null)
         return;

      bat.erase();
      bat.move(direction);
   }

   public void dropAlien() {
      if (alien != null) {
         alien.start();
      }
   }

   public Shapes isOnShape (int x, int y) {

      // Square
      if (square != null && square.isOnSquare(x, y)) {
         return Shapes.SQUARE ;
      }

      // Diamond
      if (diamond != null && diamond.isOnDiamond(x, y)) {
         return Shapes.DIAMOND;
      }

      // Circle
      if (circle != null && circle.isOnCircle(x, y))
         return Shapes.CIRCLE;

      // Triangle
      if (triangle != null && triangle.isOnTriangle(x, y))
         return Shapes.TRIANGLE;

      return Shapes.NONE;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());

      drawGameEntities(g);
   }

   public void panelEraser() {
      square = null;
      diamond = null;
      triangle = null;
      circle = null;
      repaint();
   }
}