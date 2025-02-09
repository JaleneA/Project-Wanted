import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;

public class LevelManager {
    private GamePanel gamePanel;
    private boolean tagFlag = true; // Tag You're It!

    private enum Difficulty {
        EASY, NORMAL, HARD, FREEFORALL
    }

    public LevelManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tagFlag = true;
    }

    public void generateLevel(int level) {
        Difficulty difficulty = (level <= 10) ? Difficulty.EASY 
                   : (level <= 20) ? Difficulty.NORMAL 
                   : (level <= 30) ? Difficulty.HARD 
                   : Difficulty.FREEFORALL;

        switch (difficulty) {
            case EASY -> {
                if (level % 3 == 0) scatterLevel(50, 100);
                else if (level % 2 == 0) mimicLevel(false);
                else easyPeasy();
            }
            case NORMAL -> {
                if (level % 4 == 0) {
                    if (tagFlag) motionLevel(1, 0, 1, 0, false, false); 
                    else motionLevel(0, 1, 0, 1, false, false);
                }
                else if (level % 3 == 0) {
                    if (tagFlag) {
                        flickerLevel(true, false, 1000, false);
                        tagFlag = !tagFlag;
                    }
                    else {
                        flickerLevel(true, false, 1000, true);
                        tagFlag = !tagFlag;
                    }
                }
                else if (level % 2 == 0) scatterLevel(100, 50);
                else mimicLevel(true);
            }
            case HARD -> {
                if (level % 4 == 0) {
                    if (tagFlag) {
                        flickerLevel(true, true, 1500, false);
                        tagFlag = !tagFlag;
                    }
                    else { flickerLevel(true, true, 1500, true);
                        tagFlag = !tagFlag;
                    }
                }
                else if (level % 3 == 0)  {
                    if (tagFlag) motionLevel(2, 2, 2, 2, false, true);
                    else motionLevel(2, 2, 2, 2, true, false);
                }
                else if (level % 2 == 0) flickerLevel(true, false, 1500, true);
                else scatterLevel(200, 15);
            }
            case FREEFORALL -> {
            Random rand = new Random();
            List<Runnable> freeForAllLevels = Arrays.asList(
                () -> scatterLevel(200, 10),
                () -> flickerLevel(true, true, 1200, false),
                () -> flickerLevel(true, true, 1200, true),
                () -> motionLevel(2, 0, 2, 0, true, false),
                () -> motionLevel(0, 2, 0, 2, true, false),
                () -> motionLevel(0, 2, 0, 2, false, true),
                () -> motionLevel(2, 0, 2, 0, false, true),
                () -> mimicLevel(true),
                () -> mimicLevel(false),
                () -> easyPeasy()
            );
            freeForAllLevels.get(rand.nextInt(freeForAllLevels.size())).run();
            }
        }
    }

    // Game Levels
    private void easyPeasy() {
        gamePanel.getShapesList().clear();

        int[] gridInfo = getPanelInfo();
        int panelW = gridInfo[0];
        int panelH = gridInfo[1];
        Map<GamePanel.Shapes, int[]> shapeSizes = getShapeSizes();

        int offCenter = 50;
        createShape(GamePanel.Shapes.SQUARE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.SQUARE) + offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.SQUARE) + offCenter, 0, 0, false);
        createShape(GamePanel.Shapes.DIAMOND, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.DIAMOND) + offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.DIAMOND) - offCenter, 0, 0, false);
        createShape(GamePanel.Shapes.TRIANGLE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.TRIANGLE) - offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.TRIANGLE) - offCenter, 0, 0, false);
        createShape(GamePanel.Shapes.CIRCLE, getCenteredX(panelW, shapeSizes, GamePanel.Shapes.CIRCLE) - offCenter, getCenteredY(panelH, shapeSizes, GamePanel.Shapes.CIRCLE) + offCenter, 0, 0, false);
        gamePanel.repaint();
    }

    private void motionLevel(int speedX, int speedY, int wantedSpeedX, int wantedSpeedY, boolean mimicColors, boolean relayEnabled) {
        gamePanel.getShapesList().clear();

        int[] gridInfo = getPanelInfo();
        int panelW = gridInfo[0];
        int panelH = gridInfo[1];

        int cellPadding = 5;
        int shapeW = new Square(gamePanel, 0, 0, 0, 0, false).getW();
        int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
        int cols = gridSize[0];
        int rows = gridSize[1];

        GamePanel.Shapes wantedShapeType = ShapePanel.getSelectedShape();
        Color wantedColor = getShapeColor(wantedShapeType);
        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        int oddRow = (int) (Math.random() * rows);
        int oddCol = (int) (Math.random() * cols);
        populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, speedX, speedY, wantedSpeedX, wantedSpeedY, wantedColor, mimicColors, relayEnabled);

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
        createShape(wantedShapeType, randX, randY, 0, 0, false);
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
        int shapeW = new Square(gamePanel, 0, 0, 0, 0, false).getW();
        int[] gridSize = getGridSize(panelW, panelH, shapeW, cellPadding);
        int cols = gridSize[0];
        int rows = gridSize[1];

        GamePanel.Shapes wantedShapeType = ShapePanel.getSelectedShape();
        Color wantedColor = getShapeColor(wantedShapeType);

        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        int oddRow = (int) (Math.random() * rows);
        int oddCol = (int) (Math.random() * cols);
        populateGrid(rows, cols, shapeW, cellPadding, wantedShapeType, unwantedShapes, oddRow, oddCol, 0, 0, 0 , 0, wantedColor, mimicColors, false);
        gamePanel.repaint();
    }

    // Level Helpers
    private Shape createShape(GamePanel.Shapes type, int x, int y, int speedX, int speedY, boolean collision) {
      if (type == null) {
          throw new IllegalArgumentException("Shape type cannot be null");
    }

      Shape shape = switch (type) {
         case SQUARE -> new Square(gamePanel, x, y, speedX, speedY, collision);
         case DIAMOND -> new Diamond(gamePanel, x, y, speedX, speedY, collision);
         case TRIANGLE -> new Triangle(gamePanel, x, y, speedX, speedY, collision);
         case CIRCLE -> new Circle(gamePanel, x, y, speedX, speedY, collision);
         case NONE -> throw new UnsupportedOperationException("Unimplemented case: " + type);
         default -> throw new IllegalArgumentException("Unexpected value: " + type);
      };
      gamePanel.getShapesList().add(shape);
      return shape;
  }

    private void populateGrid(int rows, int cols, int shapeW, int padding, GamePanel.Shapes wantedShape,
        List<GamePanel.Shapes> unwantedShapes, int oddRow, int oddCol, int speedX, int speedY,
        int wantedSpeedX, int wantedSpeedY, Color wantedColor, boolean mimicColors, boolean relayEnabled) {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == oddRow && col == oddCol) {
                    continue;
                }

                int x = col * (shapeW + padding) + padding;
                int y = row * (shapeW + padding) + padding;

                GamePanel.Shapes randomUnwantedShape = unwantedShapes.get((int) (Math.random() * unwantedShapes.size()));
                Shape shape = createShape(randomUnwantedShape, x, y, speedX, speedY, relayEnabled);

                if (mimicColors) {
                shape.setColor(wantedColor);
                }
            }
        }

        int wantedX = oddCol * (shapeW + padding) + padding;
        int wantedY = oddRow * (shapeW + padding) + padding;
        createShape(wantedShape, wantedX, wantedY, wantedSpeedX, wantedSpeedY, relayEnabled);
    }

    private void scatterShapes(int panelW, int panelH, GamePanel.Shapes wantedShapeType, int shapeAmount, int distanceVal) {
        Random rand = new Random();
        List<GamePanel.Shapes> unwantedShapes = getUnwantedShapes(wantedShapeType);

        for (int i = 0; i < shapeAmount; i++) {
            int randX = rand.nextInt(panelW - distanceVal);
            int randY = rand.nextInt(panelH - distanceVal);
            GamePanel.Shapes shapeType = unwantedShapes.get(rand.nextInt(unwantedShapes.size()));
            createShape(shapeType, randX, randY, 0, 0, false);
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
        case SQUARE -> new Square(gamePanel, 0, 0, 0, 0, false).getColor();
        case DIAMOND -> new Diamond(gamePanel, 0, 0, 0, 0, false).getColor();
        case TRIANGLE -> new Triangle(gamePanel, 0, 0, 0, 0, false).getColor();
        case CIRCLE -> new Circle(gamePanel, 0, 0, 0, 0, false).getColor();
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
        shapeSizes.put(GamePanel.Shapes.SQUARE, new int[]{new Square(gamePanel, 0, 0, 0, 0, false).getW(), new Square(gamePanel, 0, 0, 0, 0, false).getH()});
        shapeSizes.put(GamePanel.Shapes.DIAMOND, new int[]{new Diamond(gamePanel, 0, 0, 0, 0, false).getW(), new Diamond(gamePanel, 0, 0, 0, 0, false).getH()});
        shapeSizes.put(GamePanel.Shapes.TRIANGLE, new int[]{new Triangle(gamePanel, 0, 0, 0, 0, false).getB(), new Triangle(gamePanel, 0, 0, 0, 0, false).getH()});
        shapeSizes.put(GamePanel.Shapes.CIRCLE, new int[]{new Circle(gamePanel, 0, 0, 0, 0, false).getW(), new Circle(gamePanel, 0, 0, 0, 0, false).getH()});
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