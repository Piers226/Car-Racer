import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class PowerUp {

    private int speedUpgrade;
    private int time;
    private int initime;
    private boolean on;
    private Image star;
    private int x,y;
    public static final int LENGTH = 50;

    public PowerUp() {
        speedUpgrade = 3;
        initime = 0;
        time = 5000;
        on = false;

        try {
            star = ImageIO.read(new File("res/star.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Math.random() <0.5) x = 107; // randomly spawn on left or right side of road
        else x = 260;
        y = (int)(Math.random() * -2000);

    }
    public void drawStar(Graphics2D g2d) {
        g2d.drawImage(star,x,y,PowerUp.LENGTH, PowerUp.LENGTH,null);
    }

    // returns rectangle 2D to measure intersection
    public Rectangle2D rectangle() {
        Rectangle2D copy = new Rectangle2D.Double(x,y,PowerUp.LENGTH,PowerUp.LENGTH);
        return copy;
    }

    public int getTime() {
        return time;
    }

    public int getInitime() {
        return initime;
    }

    public int getSpeedUpgrade() {
        return speedUpgrade;
    }

    public void setInitime(int initime) {
        this.initime = initime;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
