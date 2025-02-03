import javax.swing.JPanel;

public class GamePanel extends JPanel {

   Bat bat;
   Alien alien;

   // Shapes
   Square square;
   Diamond diamond;
   Triangle triangle;
   Circle circle;

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

   //  bat = new Bat (this, 50, 350); 
   //  alien = new Alien (this, 200, 10); 
   }

   public void drawGameEntities() {

      // Square
      if (square != null) {
         square.draw();
      }

      // Diamond
      if (diamond != null) {
         diamond.draw();
      }

      // Triangle
      if (triangle != null) {
         triangle.draw();
      }

      // Circle
      if (circle != null) {
         circle.draw();
      }

       if (bat != null) {
         bat.draw();
       }
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

   public boolean isOnBat (int x, int y) {
      if (bat != null)
         return bat.isOnBat(x, y);
      else
         return false;
   }
}