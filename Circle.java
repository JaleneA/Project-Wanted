/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Circle extends Shape {

    // Attributes
    private int w, h;
    boolean isRunning;
    private Color color;
    private JPanel panel;
    private Ellipse2D.Double circle;
    private Shape lastCollided = null;
    private Thread movementThread;

    // Constructor
    public Circle(JPanel panel, int xPos, int yPos, int speedX, int speedY, boolean collisionEnabled) {
        super(xPos, yPos, speedX, speedY, collisionEnabled);  // x, y, dx, dy
        this.panel = panel;
        this.w = 50;
        this.h = 50;
        this.color = Color.decode("#64B5F6");
    }

    // Draw Circle
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        circle = new Ellipse2D.Double(x, y, w, h);
        g2.setColor(color);
        g2.fill(circle);
    }

    // Erase Circle
    private void erase() {
        Graphics g = panel.getGraphics();
        g.setColor(panel.getBackground());
        g.fillRect(x, y, w, h);
        g.dispose();
    }

    // Move Circle
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

    // Is On Circle
    @Override
    public boolean isOn(int x, int y) {
        if (circle == null)
            return false;

        return circle.contains(x, y);
    }

    @Override
    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, w, h);
    }

    // Collision
    @Override
    public boolean collidesWith(Shape otherShape) {
        return getBoundingRectangle().intersects(otherShape.getBoundingRectangle());
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
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