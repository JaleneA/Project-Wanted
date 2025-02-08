import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {

   private Dimension dimension;
   private int currentLevel;
   private Shape wantedShape;
   private List<Shape> shapesList = new ArrayList<>();
   private Timer flickerTimer;

   public static enum Shapes {
      SQUARE, DIAMOND, CIRCLE, TRIANGLE, NONE
   }

   public GamePanel() {
   }

   public void createGameEntities() {
      switch (currentLevel) {
         case 1 -> mimicLevel(true);
         case 2 -> mimicLevel(false);
         case 3 -> motionLevel(0, 1, 0, 1, false);
         case 4 -> flickerLevel(true, false, 1000, false);
         case 5 -> scatterLevel(150, 20); 
         case 6 -> motionLevel(4, 0, 4, 0, false);
         case 7 -> flickerLevel(true, true, 1000, true);
         case 8 -> motionLevel(0, 4, 0, 4, true);
         case 9 -> motionLevel(1, 0, 1, 0, true);
         case 10 -> scatterLevel(200, 15);
         case 11 -> levelOne();
         // relayLevel()
         default -> System.out.println("Huh- You're Not Supposed To Be Here! Level: " + currentLevel);
      }
   }

   public void drawGameEntities(Graphics g) {
      for (Shape shape : shapesList) {
         shape.draw(g);
     }
   }

   public Shapes isOnShape(int x, int y) {
      if (wantedShape != null && wantedShape.isOn(x, y)) {
         return ShapePanel.getSelectedShape();
     }

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

      if (wantedShape != null) {
         wantedShape.draw(g);
     }
   }

   public void panelEraser() {
      shapesList.clear();
      wantedShape = null;
      repaint();
   }

   public void setLevel(int level) {
      this.currentLevel = level;
   }

   private void levelOne() {
      shapesList.clear();

      int[] gridInfo = getPanelInfo();
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

   private void motionLevel(int speedX, int speedY, int wantedSpeedX, int wantedSpeedY, boolean mimicColors) {
      shapesList.clear();

      int[] gridInfo = getPanelInfo();
      int panelW = gridInfo[0];
      int panelH = gridInfo[1];

      int cellPadding = 5;
      int shapeW = new Square(this, 0, 0, 0, 0).getW();
      int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
      int cols = gridSize[0];
      int rows = gridSize[1];

      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      Color wantedColor = getShapeColor(wantedShapeType);
      List<Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

      int oddRow = (int) (Math.random() * rows);
      int oddCol = (int) (Math.random() * cols);

      populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, speedX, speedY, wantedSpeedX, wantedSpeedY, wantedColor, mimicColors);

      for (Shape shape : shapesList) {
         shape.startMovement();
      }
      repaint();
   }

   private void scatterLevel(int shapeAmount, int distanceVal) {
      Random rand = new Random();
      shapesList.clear();

      int[] panelInfo = getPanelInfo();
      int panelW = panelInfo[0];
      int panelH = panelInfo[1];

      Shapes wantedShapeType = ShapePanel.getSelectedShape();

      int randX = rand.nextInt(panelW - distanceVal);
      int randY = rand.nextInt(panelH - distanceVal);
      createShape(wantedShapeType, randX, randY, 0, 0);
      wantedShape = shapesList.get(shapesList.size() - 1);

      scatterShapes(panelW, panelH, wantedShapeType, shapeAmount, distanceVal);
      repaint();
   }

   private void flickerLevel(boolean isFlicker, boolean isRand, int flickerFreq, boolean mimicColors) {
      mimicLevel(mimicColors);
      startFlicker(isFlicker, isRand, flickerFreq, mimicColors);
   }

   private void mimicLevel(boolean mimicColors) {
      resetGrid();

      int[] gridInfo = getPanelInfo();
      int panelW = gridInfo[0];
      int panelH = gridInfo[1];

      int cellPadding = 5;
      int shapeW = new Square(this, 0, 0, 0, 0).getW();
      int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
      int cols = gridSize[0];
      int rows = gridSize[1];

      Shapes wantedShapeType = ShapePanel.getSelectedShape();
      Color wantedColor = getShapeColor(wantedShapeType);

      List<Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

      int oddRow = (int) (Math.random() * rows);
      int oddCol = (int) (Math.random() * cols);

      populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, 0, 0, 0 , 0, wantedColor, mimicColors);
      repaint();
   }

   // Helper Methods
   private Shape createShape(Shapes type, int x, int y, int speedX, int speedY) {
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
      return shape;
  }

   private Color getShapeColor(Shapes shapeType) {
       return switch (shapeType) {
           case SQUARE -> new Square(this, 0, 0, 0, 0).getColor();
           case DIAMOND -> new Diamond(this, 0, 0, 0, 0).getColor();
           case TRIANGLE -> new Triangle(this, 0, 0, 0, 0).getColor();
           case CIRCLE -> new Circle(this, 0, 0, 0, 0).getColor();
           default -> Color.BLACK;
       };
   }

   private int[] getPanelInfo() {
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
      List<Shapes> unwantedShapes, int oddRow, int oddCol, int speedX, int speedY,
      int wantedSpeedX, int wantedSpeedY, Color wantedColor, boolean mimicColors) {

      for (int row = 0; row < rows; row++) {
         for (int col = 0; col < cols; col++) {
            if (row == oddRow && col == oddCol) {
                  continue;
            }

            int x = col * (shapeW + padding) + padding;
            int y = row * (shapeW + padding) + padding;

            Shapes randomUnwantedShape = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
            Shape shape = createShape(randomUnwantedShape, x, y, speedX, speedY);
            
            if (mimicColors) {
               shape.setColor(wantedColor);
            }
         }
      }

      int wantedX = oddCol * (shapeW + padding) + padding;
      int wantedY = oddRow * (shapeW + padding) + padding;
      createShape(wantedShape, wantedX, wantedY, wantedSpeedX, wantedSpeedY);
   }

   private void scatterShapes(int panelW, int panelH, Shapes wantedShapeType, int shapeAmount, int distanceVal) {
      Random rand = new Random();
      List<Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

      for (int i = 0; i < shapeAmount; i++) {
         int randX = rand.nextInt(panelW - distanceVal);
         int randY = rand.nextInt(panelH - distanceVal);
         Shapes shapeType = unwantedShapes.get(rand.nextInt(unwantedShapes.size()));
         createShape(shapeType, randX, randY, 0, 0);
      }
   }

   private void startFlicker(boolean isFlicker, boolean isRand, int flickerFreq, boolean mimicColors) {
       if (!isFlicker) return;

       stopFlicker();

       List<Shape> storedShapes = new ArrayList<>(shapesList);

       flickerTimer = new Timer(flickerFreq, new ActionListener() {
           private boolean isCleared = false;
   
           @Override
           public void actionPerformed(java.awt.event.ActionEvent e) {
               if (isCleared) {
                   if (isRand) {
                       mimicLevel(mimicColors);
                   } else {
                       shapesList.addAll(storedShapes);
                   }
               } else {
                   shapesList.clear();
               }
               isCleared = !isCleared;
               repaint();
           }
       });
       flickerTimer.start();
   }

   private void resetGrid() {
      shapesList.clear();
      repaint();
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

   public void stopFlicker() {
      if (flickerTimer != null && flickerTimer.isRunning()) {
         flickerTimer.stop();
      }
   }

   public void setShapesList(List<Shape> shapesList) {
      this.shapesList = shapesList;
   }

   public List<Shape> getShapesList() {
      return shapesList;
   }
}