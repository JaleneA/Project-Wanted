/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
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

   public GamePanel(SplashPanel splashPanel) {
		this.setBackground(Color.BLACK);
		this.setDimension(new Dimension(665, 390));
		this.setPreferredSize(new Dimension(665, 390));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
      levelManager = new LevelManager(this);
      this.add(splashPanel);
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
            return ShapeFactory.getShapeType(shape);
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
      stopAllThreads();
      stopFlicker();
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

   private void setDimension(Dimension dimension) {
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