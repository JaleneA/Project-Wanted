import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class ShapePanel extends JPanel {

   // Shapes
   private Square square;
   private Diamond diamond;
   private Triangle triangle;
   private Circle circle;

   private Dimension dimension;
   private GamePanel.Shapes selectedShape;

   public ShapePanel() {
      square = null;
      diamond = null;
      triangle = null;
      circle = null;
   }

   public void pickWantedShape() {
      Random rand = new Random();
      int randomIndex = rand.nextInt(GamePanel.Shapes.values().length - 1);
      selectedShape = GamePanel.Shapes.values()[randomIndex];

      switch (selectedShape) {
         case SQUARE -> square = new Square(this, 0, 0);
         case DIAMOND -> diamond = new Diamond(this, 0, 0);
         case TRIANGLE -> triangle = new Triangle(this, 0, 0);
         case CIRCLE -> circle = new Circle(this, 0, 0);
      }
   }

   public void drawWantedShape() {

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
   }

   public GamePanel.Shapes getSelectedShape() {
      return selectedShape;
  }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());
   }

   public void panelEraser() {
      square = null;
      diamond = null;
      triangle = null;
      circle = null;
      repaint();
   }

   public void isOnWantedShape (int x, int y) {

   }
}