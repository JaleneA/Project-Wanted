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

   // Dummy Shapes
   private Square dummySquare;
   private Diamond dummyDiamond;
   private Triangle dummyTriangle;
   private Circle dummyCircle;

   // Info
   private Dimension dimension;
   private GamePanel.Shapes selectedShape;

   public ShapePanel() {
      // Shapes
      square = null;
      diamond = null;
      triangle = null;
      circle = null;

      // Dummy Shapes
      dummySquare = null;
      dummyDiamond = null;
      dummyTriangle = null;
      dummyCircle = null;
   }

   public void pickWantedShape() {

      // Random Selection
      Random rand = new Random();
      int randomIndex = rand.nextInt(GamePanel.Shapes.values().length - 1);
      selectedShape = GamePanel.Shapes.values()[randomIndex];

      // Dimension Sizes
      dimension = this.getSize();
      int panelW = dimension.width;
      int panelH = dimension.height;

      // Shape Sizes
      dummySquare = new Square(this,0, 0);
      int squareW = dummySquare.getW();
      int squareH = dummySquare.getH();

      dummyDiamond = new Diamond(this, 0, 0);
      int diamondW = dummyDiamond.getW();
      int diamondH = dummyDiamond.getH();

      dummyTriangle = new Triangle(this, 0, 0);
      int triangleB = dummyTriangle.getB();
      int triangleH = dummyTriangle.getH();

      dummyCircle = new Circle(this, 0, 0);
      int circleW = dummyCircle.getW();
      int circleH = dummyCircle.getH();

      // Selection
      switch (selectedShape) {
         case SQUARE -> square = new Square(this, (panelW / 2) - (squareW / 2), (panelH / 2) - (squareH / 2));
         case DIAMOND -> diamond = new Diamond(this, (panelW / 2) - (diamondW / 2), (panelH / 2) - (diamondH / 2));
         case TRIANGLE -> triangle = new Triangle(this, (panelW / 2) - (triangleB / 2), (panelH / 2) - (triangleH / 2));
         case CIRCLE -> circle = new Circle(this, (panelW / 2) - (circleW / 2), (panelH / 2) - (circleH / 2));
      }
   }

   public void drawWantedShape() {
      repaint();
   }

   public GamePanel.Shapes getSelectedShape() {
      return selectedShape;
  }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());

      if (square != null) square.draw(g);
      if (diamond != null) diamond.draw(g);
      if (triangle != null) triangle.draw(g);
      if (circle != null) circle.draw(g);
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