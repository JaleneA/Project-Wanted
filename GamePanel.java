import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

   // Info
   private Dimension dimension;
   private int currentLevel;
   private List<Shape> shapesList = new ArrayList<>();

   public enum Shapes {
      SQUARE, DIAMOND, CIRCLE, TRIANGLE, NONE
   }

   public GamePanel() {
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
      for (Shape shape : shapesList) {
         shape.draw(g);
     }
   }

   public Shapes isOnShape(int x, int y) {
      for (Shape shape : shapesList) {
          if (shape.isOn(x, y)) {
              if (shape instanceof Square) return Shapes.SQUARE;
              if (shape instanceof Diamond) return Shapes.DIAMOND;
              if (shape instanceof Circle) return Shapes.CIRCLE;
              if (shape instanceof Triangle) return Shapes.TRIANGLE;
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

      int[] gridInfo = getGridInfo();
      int panelW = gridInfo[0];
      int panelH = gridInfo[1];

      Map<Shapes, int[]> shapeSizes = getShapeSizes();

      int offCenter = 50;

      createShape(Shapes.SQUARE, getCenteredX(panelW, shapeSizes, Shapes.SQUARE) + offCenter, getCenteredY(panelH, shapeSizes, Shapes.SQUARE) + offCenter, 0, 0);
      createShape(Shapes.DIAMOND, getCenteredX(panelW, shapeSizes, Shapes.DIAMOND) + offCenter, getCenteredY(panelH, shapeSizes, Shapes.DIAMOND) - offCenter, 0, 0);
      createShape(Shapes.TRIANGLE, getCenteredX(panelW, shapeSizes, Shapes.TRIANGLE) - offCenter, getCenteredY(panelH, shapeSizes, Shapes.TRIANGLE) - offCenter, 0, 0);
      createShape(Shapes.CIRCLE, getCenteredX(panelW, shapeSizes, Shapes.CIRCLE) - offCenter, getCenteredY(panelH, shapeSizes, Shapes.CIRCLE) + offCenter, 0, 0);

      repaint();
   }

   private void levelTwo() {
      shapesList.clear();

      int[] gridInfo = getGridInfo();
      int panelW = gridInfo[0];
      int panelH = gridInfo[1];

      int cellPadding = 5;
      int shapeW = new Square(this, 0, 0, 0, 0).getW();
      int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
      int cols = gridSize[0];
      int rows = gridSize[1];

      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      List<Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

      int oddRow = (int) (Math.random() * rows);
      int oddCol = (int) (Math.random() * cols);

      populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, 0, 0);
      repaint();
   }

   private void levelThree() {
      shapesList.clear();

      int[] gridInfo = getGridInfo();
      int panelW = gridInfo[0];
      int panelH = gridInfo[1];

      int cellPadding = 5;
      int shapeW = new Square(this, 0, 0, 0, 0).getW();
      int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
      int cols = gridSize[0];
      int rows = gridSize[1];

      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      List<Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

      int oddRow = (int) (Math.random() * rows);
      int oddCol = (int) (Math.random() * cols);

      int speedX = 0;
      int speedY = 2;

      populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, speedX, speedY);

      for (Shape shape : shapesList) {
         shape.startMovement();
      }
      repaint();
   }

   // Helper Methods
   private void createShape(Shapes type, int x, int y, int speedX, int speedY) {
      if (type == null) {
          throw new IllegalArgumentException("Shape type cannot be null");
      }

      Shape shape = switch (type) {
         case SQUARE -> new Square(this, x, y, speedX, speedY);
         case DIAMOND -> new Diamond(this, x, y, speedX, speedY);
         case TRIANGLE -> new Triangle(this, x, y, speedX, speedY);
         case CIRCLE -> new Circle(this, x, y, speedX, speedY);
         case NONE -> throw new UnsupportedOperationException("Unimplemented case: " + type);
         default -> throw new IllegalArgumentException("Unexpected value: " + type);
      };
      shapesList.add(shape);
  }

   private int[] getGridInfo() {
      dimension = this.getSize();
      return new int[]{dimension.width, dimension.height};
   }

   private int[] getGridSize(int panelW, int panelH, int shapeW, int padding) {
      int cols = (panelW - padding) / (shapeW + padding);
      int rows = (panelH - padding) / (shapeW + padding);
      return new int[]{cols, rows};
   }

   private List<Shapes> getUnwantedShapes(Shapes wantedShape) {
      Shapes[] shapeTypes = {Shapes.SQUARE, Shapes.DIAMOND, Shapes.TRIANGLE, Shapes.CIRCLE};
      List<Shapes> unwantedShapes = new ArrayList<>();
      for (Shapes shape : shapeTypes) {
         if (shape != wantedShape) {
            unwantedShapes.add(shape);
         }
      }
      return unwantedShapes;
   }

   private void populateGrid(int rows, int cols, int shapeW, int padding, Shapes wantedShape,
                           List<Shapes> unwantedShapes, int oddRow, int oddCol, int speedX, int speedY) {
      for (int row = 0; row < rows; row++) {
         for (int col = 0; col < cols; col++) {
               int x = col * (shapeW + padding) + padding;
               int y = row * (shapeW + padding) + padding;

               if (row == oddRow && col == oddCol) {
                  createShape(wantedShape, x, y, speedX, speedY);
               } else {
                  Shapes randomUnwantedShape = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
                  createShape(randomUnwantedShape, x, y, speedX, speedY);
               }
         }
      }
   }

   private Map<Shapes, int[]> getShapeSizes() {
      Map<Shapes, int[]> shapeSizes = new HashMap<>();
      shapeSizes.put(Shapes.SQUARE, new int[]{new Square(this, 0, 0, 0, 0).getW(), new Square(this, 0, 0, 0, 0).getH()});
      shapeSizes.put(Shapes.DIAMOND, new int[]{new Diamond(this, 0, 0, 0, 0).getW(), new Diamond(this, 0, 0, 0, 0).getH()});
      shapeSizes.put(Shapes.TRIANGLE, new int[]{new Triangle(this, 0, 0, 0, 0).getB(), new Triangle(this, 0, 0, 0, 0).getH()});
      shapeSizes.put(Shapes.CIRCLE, new int[]{new Circle(this, 0, 0, 0, 0).getW(), new Circle(this, 0, 0, 0, 0).getH()});
      return shapeSizes;
   }

   private int getCenteredX(int panelW, Map<Shapes, int[]> shapeSizes, Shapes type) {
      return (panelW / 2) - (shapeSizes.get(type)[0] / 2);
  }

   private int getCenteredY(int panelH, Map<Shapes, int[]> shapeSizes, Shapes type) {
      return (panelH / 2) - (shapeSizes.get(type)[1] / 2);
   }

   public void stopAllThreads() {
      for (Shape shape : shapesList) {
          shape.stopMovement();
      }
  }

   public void setShapesList(List<Shape> shapesList) {
      this.shapesList = shapesList;
   }

   public List<Shape> getShapesList() {
      return shapesList;
   }
}