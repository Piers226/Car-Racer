import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Car {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 160;
    private int x,y;
    private Image car;



    public Car(String s) {
        try {
            car = ImageIO.read(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x =95;
        y =Main.HEIGHT-200;
    }

    public void drawCar(Graphics2D g2d) {
        g2d.drawImage(car,x,y,Car.WIDTH,Car.HEIGHT,null);
    }

    //creates a rectangle to represent car during collision
    public Rectangle2D rectangle() {
        Rectangle2D copy = new Rectangle2D.Double(x,y,Car.WIDTH-20,Car.HEIGHT);
        return copy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
