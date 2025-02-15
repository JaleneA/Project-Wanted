/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Triangle extends Shape {

    // Attributes
    private int b, h;
    boolean isRunning;
    private Color color;
    private JPanel panel;
    private Polygon triangle;
    private Shape lastCollided = null;
    private Thread movementThread;

    // Constructor
    public Triangle(JPanel panel, int xPos, int yPos, int speedX, int speedY, boolean collisionEnabled) {
        super(xPos, yPos, speedX, speedY, collisionEnabled); // x, y, dx, dy
        this.panel = panel;
        this.b = 50;
        this.h = 50;
        this.color = Color.decode("#81C784");
    }

    // Draw Triangle
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        triangle = new Polygon(
            new int[] {x, x + b, x + b / 2},
            new int[] {y + h, y + h, y},
            3
        );
        g.fillPolygon(triangle);
    }

    // Erase Triangle
    private void erase() {
        Graphics g = panel.getGraphics();
        g.setColor(panel.getBackground());
        g.fillRect(x, y, b, h);
        g.dispose();
    }

    // Move Triangle
    @Override
    public void move() {
        if (!panel.isVisible()) return;
        erase();

        x += speedX;
        y += speedY;

        if (y > panel.getHeight() + 10)
            y = 0;

        if (x > panel.getWidth() + 10)
            x = 0;

        if (panel instanceof GamePanel gamePanel) {
            Shape currCollided = null;

            for (Shape other : gamePanel.getShapesList()) {
                if (other != this && collidesWith(other) && this.isCollisionEnabled()) {
                    currCollided = other;
                    break;
                }
            }
            if (currCollided != null && currCollided != lastCollided) {
                swipeColor(currCollided);
                lastCollided = currCollided;
            } else if (currCollided == null) {
                lastCollided = null;
            }
        }

        panel.repaint();
    }

    // Movement
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            move();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void startMovement() {
        if (movementThread == null || !movementThread.isAlive()) {
            movementThread = new Thread(this);
            movementThread.start();
        }
    }

    @Override
    public void stopMovement() {
        isRunning = false;
        if (movementThread != null && movementThread.isAlive()) {
            movementThread.interrupt();
        }
    }

    // Is On Triangle
    @Override
    public boolean isOn(int x, int y) {
        if (triangle == null)
            return false;
        return triangle.contains(x, y);
    }

    @Override
    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, b, h);
    }

    // Collision
    @Override
    public boolean collidesWith(Shape otherShape) {
        return getBoundingRectangle().intersects(otherShape.getBoundingRectangle());
    }

    public int getB() {
        return b;
    }

    public int getH() {
        return h;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}