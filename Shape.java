import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Shape extends Thread {
    protected int x, y;
    protected int speedX, speedY;

    public Shape(int x, int y, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Abstract Methods
    public abstract void move();
    public abstract void stopMovement();
    public abstract void startMovement();
    public abstract void setColor(Color color);
    public abstract void draw(Graphics g);
    public abstract boolean isOn(int x, int y);
    public abstract boolean collidesWith(Shape otherShape);
    public abstract Rectangle2D.Double getBoundingRectangle();

    // Getters And Setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }
}