import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {

   private final LevelManager levelManager;
   private Dimension dimension;
   private int currentLevel;
   private Shape wantedShape;
   private List<Shape> shapesList = new ArrayList<>();
   private Timer flickerTimer;


   public static enum Shapes {
      SQUARE, DIAMOND, CIRCLE, TRIANGLE, NONE
   }

   public GamePanel() {
      levelManager = new LevelManager(this);
   }

   public void createGameEntities() {
      levelManager.generateLevel(currentLevel);
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

   // Getter & Setter Methods
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Timer getFlickerTimer() {
        return flickerTimer;
    }

    public void setFlickerTimer(Timer flickerTimer) {
        this.flickerTimer = flickerTimer;
    }

    public Shape getWantedShape() {
        return wantedShape;
    }

    public void setWantedShape(Shape wantedShape) {
        this.wantedShape = wantedShape;
    }

   public void setShapesList(List<Shape> shapesList) {
      this.shapesList = shapesList;
   }

   public List<Shape> getShapesList() {
      return shapesList;
   }
}