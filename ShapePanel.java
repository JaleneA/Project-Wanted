/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class ShapePanel extends JPanel {
    private Shape currentShape;
    private static GamePanel.Shapes selectedShape;
    private Dimension dimension;

    public ShapePanel() {
        currentShape = null;
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBackground(Color.WHITE);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void pickWantedShape() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(GamePanel.Shapes.values().length - 1);
        selectedShape = GamePanel.Shapes.values()[randomIndex];

        dimension = this.getSize();
        int panelW = dimension.width;
        int panelH = dimension.height;
        currentShape = ShapeFactory.createShape(selectedShape, this, (panelW / 2) - 25, (panelH / 2) - 25, 0, 0, false);
    }

    public void drawWantedShape() {
        repaint();
    }

    public static void setWantedShape(GamePanel.Shapes shapeSelected) {
        selectedShape = shapeSelected;
    }

    public static GamePanel.Shapes getSelectedShape() {
        return selectedShape;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    public void panelEraser() {
        currentShape = null;
        repaint();
    }
}