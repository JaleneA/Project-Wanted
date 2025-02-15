/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class ShapeFactory {
    public static Shape createShape(GamePanel.Shapes type, JPanel panel, int xPos, int yPos, int speedX, int speedY, boolean collisionEnabled) {
        switch (type) {
            case SQUARE -> {
                return new Square(panel, xPos, yPos, speedX, speedY, collisionEnabled);
            }
            case CIRCLE -> {
                return new Circle(panel, xPos, yPos, speedX, speedY, collisionEnabled);
            }
            case DIAMOND -> {
                return new Diamond(panel, xPos, yPos, speedX, speedY, collisionEnabled);
            }
            case TRIANGLE -> {
                return new Triangle(panel, xPos, yPos, speedX, speedY, collisionEnabled);
            }
            default -> throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }

    public static Color getShapeColor(GamePanel.Shapes shapeType) {
        return switch (shapeType) {
            case SQUARE -> new Square(null, 0, 0, 0, 0, false).getColor();
            case DIAMOND -> new Diamond(null, 0, 0, 0, 0, false).getColor();
            case TRIANGLE -> new Triangle(null, 0, 0, 0, 0, false).getColor();
            case CIRCLE -> new Circle(null, 0, 0, 0, 0, false).getColor();
            default -> Color.BLACK;
        };
    }

    public static Map<GamePanel.Shapes, int[]> getShapeSizes() {
        Map<GamePanel.Shapes, int[]> shapeSizes = new HashMap<>();
        
        shapeSizes.put(GamePanel.Shapes.SQUARE, new int[]{50, 50});
        shapeSizes.put(GamePanel.Shapes.DIAMOND, new int[]{50, 50});
        shapeSizes.put(GamePanel.Shapes.TRIANGLE, new int[]{50, 50});
        shapeSizes.put(GamePanel.Shapes.CIRCLE, new int[]{50, 50});

        return shapeSizes;
    }

    public static GamePanel.Shapes getShapeType(Shape shape) {
        if (shape instanceof Square) return GamePanel.Shapes.SQUARE;
        if (shape instanceof Diamond) return GamePanel.Shapes.DIAMOND;
        if (shape instanceof Circle) return GamePanel.Shapes.CIRCLE;
        if (shape instanceof Triangle) return GamePanel.Shapes.TRIANGLE;
        return GamePanel.Shapes.NONE;
    }
}