import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Square extends Shape {

    // Attributes
    private int w, h;
    boolean isRunning;
    private Color color;
    private JPanel panel;
    private Rectangle2D.Double square;
    private Shape lastCollided = null;

    // Constructor
    public Square(JPanel panel, int xPos, int yPos, int speedX, int speedY, boolean collisionEnabled) {
        super(xPos, yPos, speedX, speedY, collisionEnabled); // x, y, dx, dy
        this.panel = panel;
        this.w = 50;
        this.h = 50;
        this.color = Color.RED;
    }

    // Draw Square
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        square = new Rectangle2D.Double(x, y, w, h);
        g2.setColor(color);
        g2.fill(square);
    }

    // Erase Square
    private void erase() {
        Graphics g = panel.getGraphics();
        g.setColor(panel.getBackground());
        g.fillRect(x, y, w, h);
        g.dispose();
    }

    // Move Square
    @Override
    public void move() {
        if (!panel.isVisible()) return;
        erase();

        int newX = x + speedX;
        int newY = y + speedY;

        x = newX;
        y = newY;

        if (y > panel.getHeight() + 10)
            y = 0;

        if (x > panel.getWidth() + 10)
            x = 0;

        if (panel instanceof GamePanel gamePanel) {
            Shape currCollided = null;

            for (Shape other : gamePanel.getShapesList()) {
                if (other != this && collidesWith(other) && this.isCollisionEnabled() && other.isCollisionEnabled()) {
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
        new Thread(this).start();
    }

    @Override
    public void stopMovement() {
        isRunning = false;
        this.interrupt();
    }

    // Is On Square
    @Override
    public boolean isOn(int x, int y) {
        if (square == null)
            return false;
        return square.contains(x, y);
    }

    @Override
    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, w, h);
    }

    // Collision
    @Override
    public boolean collidesWith(Shape otherShape) {
        switch (otherShape) {
            case Square square1 -> {
                return getBoundingRectangle().intersects(square1.getBoundingRectangle());
            }
            case Circle circle -> {
                return getBoundingRectangle().intersects(circle.getBoundingRectangle());
            }
            case Diamond diamond -> {
                return getBoundingRectangle().intersects(diamond.getBoundingRectangle());
            }
            case Triangle triangle -> {
                return getBoundingRectangle().intersects(triangle.getBoundingRectangle());
            }
            default -> {
            }
        }
        return false;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setW(int w) {
        this.w = w;
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