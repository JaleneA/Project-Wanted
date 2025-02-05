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

    // Constructor
    public Triangle(JPanel panel, int xPos, int yPos, int speedX, int speedY) {
        super(xPos, yPos, speedX, speedY); // x, y, dx, dy
        this.panel = panel;
        this.b = 50;
        this.h = 50;
        this.color = Color.GREEN;
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
        switch (otherShape) {
            case Square square -> {
                return getBoundingRectangle().intersects(square.getBoundingRectangle());
            }
            case Circle circle -> {
                return getBoundingRectangle().intersects(circle.getBoundingRectangle());
            }
            case Diamond diamond -> {
                return getBoundingRectangle().intersects(diamond.getBoundingRectangle());
            }
            case Triangle triangle1 -> {
                return getBoundingRectangle().intersects(triangle1.getBoundingRectangle());
            }
            default -> {
            }
        }
        return false;
    }

    public int getB() {
        return b;
    }

    public int getH() {
        return h;
    }
}