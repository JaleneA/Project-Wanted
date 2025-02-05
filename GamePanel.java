import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

   // Shapes
   Square square;
   Diamond diamond;
   Triangle triangle;
   Circle circle;

   // Dummy Shapes
   private Square dummySquare;
   private Diamond dummyDiamond;
   private Triangle dummyTriangle;
   private Circle dummyCircle;

   // Info
   private Dimension dimension;
   private int currentLevel;
   private List<Object> shapesList = new ArrayList<>();

   public enum Shapes {
      SQUARE, DIAMOND, CIRCLE, TRIANGLE, NONE
   }

   public GamePanel() {
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

   public void createGameEntities() {
      switch (currentLevel) {
         case 1 -> levelOne();
         case 2 -> levelTwo();
         case 3 -> levelThree();
         case 4 -> levelTwo();
         case 5 -> levelOne();
         case 6 -> levelTwo();
         case 7 -> levelOne();
         case 8 -> levelTwo();
         case 9 -> levelOne();
         case 10 -> levelTwo();
         default -> System.out.println("Huh- You're Not Supposed To Be Here! Level: " + currentLevel);
     }
 }

   public void drawGameEntities(Graphics g) {
      for (Object shape : shapesList) {
         if (shape instanceof Square) {
             ((Square) shape).draw(g);
         } else if (shape instanceof Diamond) {
             ((Diamond) shape).draw(g);
         } else if (shape instanceof Triangle) {
             ((Triangle) shape).draw(g);
         } else if (shape instanceof Circle) {
             ((Circle) shape).draw(g);
         }
     }
   }

   public void updateGameEntities(int direction) {
     
   }

   public Shapes isOnShape(int x, int y) {
      for (Object shape : shapesList) {
          if (shape instanceof Square && ((Square) shape).isOnSquare(x, y)) {
              return Shapes.SQUARE;
          }
          if (shape instanceof Diamond && ((Diamond) shape).isOnDiamond(x, y)) {
              return Shapes.DIAMOND;
          }
          if (shape instanceof Circle && ((Circle) shape).isOnCircle(x, y)) {
              return Shapes.CIRCLE;
          }
          if (shape instanceof Triangle && ((Triangle) shape).isOnTriangle(x, y)) {
              return Shapes.TRIANGLE;
          }
      }
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
      shapesList.clear();
      repaint();
   }

   public void setLevel(int level) {
      this.currentLevel = level;
   }

   private void levelOne() {

      shapesList.clear();

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

      // Positioning Offset
      int offCenter = 50;

      createShape(Shapes.SQUARE, (panelW / 2) - (squareW / 2) + offCenter, (panelH / 2) - (squareH / 2) + offCenter);
      createShape(Shapes.DIAMOND, (panelW / 2) - (diamondW / 2) + offCenter, (panelH / 2) - (diamondH / 2) - offCenter);
      createShape(Shapes.TRIANGLE, (panelW / 2) - (triangleB / 2) - offCenter, (panelH / 2) - (triangleH / 2) - offCenter);
      createShape(Shapes.CIRCLE, (panelW / 2) - (circleW / 2) - offCenter, (panelH / 2) - (circleH / 2) + offCenter);

      repaint();
   }

   private void levelTwo() {

      shapesList.clear();

      // Dimension Sizes
      dimension = this.getSize();
      int panelW = dimension.width;
      int panelH = dimension.height;
  
      // Shape Sizes
      dummySquare = new Square(this, 0, 0);
      int shapeW = dummySquare.getW();
  
      // Define Grid Size
      int cellPadding = 5;
      int cols = (panelW - cellPadding) / (shapeW + cellPadding);
      int rows = (panelH - cellPadding) / (shapeW + cellPadding);

      Shapes[] shapeTypes = {Shapes.SQUARE, Shapes.DIAMOND, Shapes.TRIANGLE, Shapes.CIRCLE};
      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      
      List<Shapes> unwantedShapes = new ArrayList<>();
      for (Shapes shape : shapeTypes) {
         if (shape != wantedShapeType) {
            unwantedShapes.add(shape);
         }
      }

      int oddRow = (int) (Math.random() * rows);
      int oddCol = (int) (Math.random() * cols);

      for (int row = 0; row < rows; row++) {
          for (int col = 0; col < cols; col++) {
              int x = col * (shapeW + cellPadding) + cellPadding;
              int y = row * (shapeW + cellPadding) + cellPadding;
              
              if (row == oddRow && col == oddCol) {
                 createShape(wantedShapeType, x, y);
              } else {
                  Shapes randomUnwantedShape = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
                  createShape(randomUnwantedShape, x, y);
              }
          }
      }
      repaint();
  }

   private void levelThree() {
      shapesList.clear();

      int panelWidth = getWidth();
      int panelHeight = getHeight();

      int shapeWidth = 50; 
      int shapeHeight = 50;
      int offsetX = shapeWidth + 10;
      int offsetY = shapeHeight + 10;

      int cols = panelWidth / offsetX;
      int rows = panelHeight / offsetY;

      Shapes[] shapeTypes = {Shapes.SQUARE, Shapes.DIAMOND, Shapes.TRIANGLE, Shapes.CIRCLE};
      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      List<Shapes> unwantedShapes = new ArrayList<>();
      for (Shapes shape : shapeTypes) {
            if (shape != wantedShapeType) {
               unwantedShapes.add(shape);
            }
      }

      int wantedShapeX = (int) (Math.random() * cols) * offsetX;
      int wantedShapeY = (int) (Math.random() * rows) * offsetY;

      for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
               int x = col * offsetX;
               int y = row * offsetY;

               if (x == wantedShapeX && y == wantedShapeY) {
                  createShape(wantedShapeType, x, y);
               } else {
                  Shapes shapeToCreate = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
                  createShape(shapeToCreate, x, y);
               }
               for (Object shape : shapesList) {
                  if (shape instanceof Thread && !((Thread) shape).isAlive()) {
                        ((Thread) shape).start();
                  }
               }
            }
      }
      repaint();
   }

   public void stopAllThreads() {
      for (Object shape : shapesList) {
         if (shape instanceof Thread thread) {
              thread.interrupt();
         }
      }
   }

   private void createShape(Shapes type, int x, int y) {
      if (type == null) {
         throw new IllegalArgumentException("Shape type cannot be null");
     }

      Object shape = switch (type) {
            case SQUARE -> new Square(this, x, y);
            case DIAMOND -> new Diamond(this, x, y);
            case TRIANGLE -> new Triangle(this, x, y);
            case CIRCLE -> new Circle(this, x, y);
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
      };

      if (shape != null) {
         shapesList.add(shape);
      }
   }
}