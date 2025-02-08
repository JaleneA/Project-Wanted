import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;

public class LevelManager {
    private GamePanel gamePanel;

    public LevelManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void generateLevel(int level) {
        switch (level) {
            case 1 -> levelOne(); // Easiest
            case 2 -> mimicLevel(false);
            case 3 -> motionLevel(0, 1, 0, 1, false);
            case 4 -> flickerLevel(true, false, 1000, false);
            case 5 -> scatterLevel(150, 20); 
            case 6 ->  mimicLevel(true);
            case 7 -> motionLevel(4, 0, 4, 0, false);
            case 8 -> motionLevel(0, 4, 0, 4, true);
            case 9 -> motionLevel(1, 0, 1, 0, true);
            case 10 -> scatterLevel(200, 15);
            case 11 -> flickerLevel(true, true, 1000, true); // Most Difficult
            // relayLevel()
            default -> System.out.println("Huh- You're Not Supposed To Be Here! Level: " + level);
        }
    }

    // Game Levels
    private void levelOne() {
        gamePanel.getShapesList().clear();

        int[] gridInfo = getPanelInfo();
        int panelW = gridInfo[0];
        int panelH = gridInfo[1];
        Map<GamePanel.Shapes, int[]> shapeSizes = getShapeSizes();

        int offCenter = 50;
        createShape(GamePanel.Shapes.SQUARE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.SQUARE) + offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.SQUARE) + offCenter, 0, 0);
        createShape(GamePanel.Shapes.DIAMOND, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.DIAMOND) + offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.DIAMOND) - offCenter, 0, 0);
        createShape(GamePanel.Shapes.TRIANGLE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.TRIANGLE) - offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.TRIANGLE) - offCenter, 0, 0);
        createShape(GamePanel.Shapes.CIRCLE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.CIRCLE) - offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.CIRCLE) + offCenter, 0, 0);
        gamePanel.repaint();
    }

    private void motionLevel(int speedX, int speedY, int wantedSpeedX, int wantedSpeedY, boolean mimicColors) {
        gamePanel.getShapesList().clear();

        int[] gridInfo = getPanelInfo();
        int panelW = gridInfo[0];
        int panelH = gridInfo[1];

        int cellPadding = 5;
        int shapeW = new Square(gamePanel, 0, 0, 0, 0).getW();
        int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
        int cols = gridSize[0];
        int rows = gridSize[1];

        GamePanel.Shapes wantedShapeType = ShapePanel.getSelectedShape();
        Color wantedColor = getShapeColor(wantedShapeType);
        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        int oddRow = (int) (Math.random() * rows);
        int oddCol = (int) (Math.random() * cols);
        populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, speedX, speedY, wantedSpeedX, wantedSpeedY, wantedColor, mimicColors);

        for (Shape shape : gamePanel.getShapesList()) {
           shape.startMovement();
        }
        gamePanel.repaint();
    }

    private void scatterLevel(int shapeAmount, int distanceVal) {
        Random rand = new Random();
        gamePanel.getShapesList().clear();

        int[] panelInfo = getPanelInfo();
        int panelW = panelInfo[0];
        int panelH = panelInfo[1];

        GamePanel.Shapes wantedShapeType = ShapePanel.getSelectedShape();

        int randX = rand.nextInt(panelW - distanceVal);
        int randY = rand.nextInt(panelH - distanceVal);
        createShape(wantedShapeType, randX, randY, 0, 0);
        gamePanel.setWantedShape(gamePanel.getShapesList().get(gamePanel.getShapesList().size() - 1));

        scatterShapes(panelW, panelH, wantedShapeType, shapeAmount, distanceVal);
        gamePanel.repaint();
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
        int shapeW = new Square(gamePanel, 0, 0, 0, 0).getW();
        int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
        int cols = gridSize[0];
        int rows = gridSize[1];

        GamePanel.Shapes wantedShapeType = ShapePanel.getSelectedShape();
        Color wantedColor = getShapeColor(wantedShapeType);

        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        int oddRow = (int) (Math.random() * rows);
        int oddCol = (int) (Math.random() * cols);
        populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, 0, 0, 0 , 0, wantedColor, mimicColors);
        gamePanel.repaint();
    }

    // Level Helpers
    private Shape createShape(GamePanel.Shapes type, int x, int y, int speedX, int speedY) {
      if (type == null) {
          throw new IllegalArgumentException("Shape type cannot be null");
    }

      Shape shape = switch (type) {
         case SQUARE -> new Square(gamePanel, x, y, speedX, speedY);
         case DIAMOND -> new Diamond(gamePanel, x, y, speedX, speedY);
         case TRIANGLE -> new Triangle(gamePanel, x, y, speedX, speedY);
         case CIRCLE -> new Circle(gamePanel, x, y, speedX, speedY);
         case NONE -> throw new UnsupportedOperationException("Unimplemented case: " + type);
         default -> throw new IllegalArgumentException("Unexpected value: " + type);
      };
      gamePanel.getShapesList().add(shape);
      return shape;
  }

    private void populateGrid(int rows, int cols, int shapeW, int padding, GamePanel.Shapes wantedShape,
        List<GamePanel.Shapes> unwantedShapes, int oddRow, int oddCol, int speedX, int speedY,
        int wantedSpeedX, int wantedSpeedY, Color wantedColor, boolean mimicColors) {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == oddRow && col == oddCol) {
                    continue;
                }

                int x = col * (shapeW + padding) + padding;
                int y = row * (shapeW + padding) + padding;

                GamePanel.Shapes randomUnwantedShape = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
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

    private void scatterShapes(int panelW, int panelH, GamePanel.Shapes wantedShapeType, int shapeAmount, int distanceVal) {
        Random rand = new Random();
        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        for (int i = 0; i < shapeAmount; i++) {
            int randX = rand.nextInt(panelW - distanceVal);
            int randY = rand.nextInt(panelH - distanceVal);
            GamePanel.Shapes shapeType = unwantedShapes.get(rand.nextInt(unwantedShapes.size()));
            createShape(shapeType, randX, randY, 0, 0);
        }
    }

    private void startFlicker(boolean isFlicker, boolean isRand, int flickerFreq, boolean mimicColors) {
        if (!isFlicker) return;
        gamePanel.stopFlicker();
        List<Shape> storedShapes = new ArrayList<>(gamePanel.getShapesList());

        gamePanel.setFlickerTimer( new Timer(flickerFreq, new ActionListener() {
            private boolean isCleared = false;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (isCleared) {
                    if (isRand) {
                        mimicLevel(mimicColors);
                    } else {
                        gamePanel.getShapesList().addAll(storedShapes);
                    }
                } else {
                    gamePanel.getShapesList().clear();
                }
                isCleared = !isCleared;
                gamePanel.repaint();
            }
        }));
        gamePanel.getFlickerTimer().start();
    }

    private void resetGrid() {
        gamePanel.getShapesList().clear();
        gamePanel.repaint();
   }

   private Color getShapeColor(GamePanel.Shapes shapeType) {
    return switch (shapeType) {
       case SQUARE -> new Square(gamePanel, 0, 0, 0, 0).getColor();
       case DIAMOND -> new Diamond(gamePanel, 0, 0, 0, 0).getColor();
       case TRIANGLE -> new Triangle(gamePanel, 0, 0, 0, 0).getColor();
       case CIRCLE -> new Circle(gamePanel, 0, 0, 0, 0).getColor();
       default -> Color.BLACK;
    };
}

    private int[] getPanelInfo() {
    return new int[]{gamePanel.getDimension().width, gamePanel.getDimension().height};
    }

    private int[] getGridSize(int panelW, int panelH, int shapeW, int padding) {
        int cols = (panelW - padding) / (shapeW + padding);
        int rows = (panelH - padding) / (shapeW + padding);
        return new int[]{cols, rows};
    }

    private List<GamePanel.Shapes> getUnwantedShapes(GamePanel.Shapes wantedShape) {
        GamePanel.Shapes[] shapeTypes = {GamePanel.Shapes.SQUARE, GamePanel.Shapes.DIAMOND, GamePanel.Shapes.TRIANGLE, GamePanel.Shapes.CIRCLE};
        List<GamePanel.Shapes> unwantedShapes = new ArrayList<>();
        for (GamePanel.Shapes shape : shapeTypes) {
            if (shape != wantedShape) {
                unwantedShapes.add(shape);
            }
        }
        return unwantedShapes;
    }

    private Map<GamePanel.Shapes, int[]> getShapeSizes() {
        Map<GamePanel.Shapes, int[]> shapeSizes = new HashMap<>();
        shapeSizes.put(GamePanel.Shapes.SQUARE, new int[]{new Square(gamePanel, 0, 0, 0, 0).getW(), new Square(gamePanel, 0, 0, 0, 0).getH()});
        shapeSizes.put(GamePanel.Shapes.DIAMOND, new int[]{new Diamond(gamePanel, 0, 0, 0, 0).getW(), new Diamond(gamePanel, 0, 0, 0, 0).getH()});
        shapeSizes.put(GamePanel.Shapes.TRIANGLE, new int[]{new Triangle(gamePanel, 0, 0, 0, 0).getB(), new Triangle(gamePanel, 0, 0, 0, 0).getH()});
        shapeSizes.put(GamePanel.Shapes.CIRCLE, new int[]{new Circle(gamePanel, 0, 0, 0, 0).getW(), new Circle(gamePanel, 0, 0, 0, 0).getH()});
        return shapeSizes;
    }

    private int getCenteredX(int panelW, Map<GamePanel.Shapes, int[]> shapeSizes, GamePanel.Shapes type) {
        return (panelW / 2) - (shapeSizes.get(type)[0] / 2);
   }

    private int getCenteredY(int panelH, Map<GamePanel.Shapes, int[]> shapeSizes, GamePanel.Shapes type) {
        return (panelH / 2) - (shapeSizes.get(type)[1] / 2);
    }

    // Getter & Setter Methods
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}