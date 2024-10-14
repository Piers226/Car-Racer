import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Road{

    private Image road;
    private int x,y;
    public static final int LEFT = 80;
    public static final int RIGHT = 334;
    private static int speed;
    private int laps;

    public Road() {
        try {
            road = ImageIO.read(new File("Res/road.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        speed = 7;

    }

    public void updateRoad(){
        if (y >= Main.HEIGHT) {
            y = 0-Main.HEIGHT;
            laps++;
        }
        else y+=speed;
    }

    public void drawImage(Graphics2D g2d){
        g2d.drawImage(road,x,y,Main.WIDTH, Main.HEIGHT + 50, null);

    }
    public static void setSpeed(int speed) {
        Road.speed = speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLaps() {
        return laps;
    }
}
