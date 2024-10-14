import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Heart {

    private Image life;
    private int x,y;

    public Heart(){
        try {
            life = ImageIO.read(new File("res/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x= 20;
        y = 50;
    }

    //displays heart
    public void drawHeart(Graphics2D g2d) {
        g2d.drawImage(life,x,y,50, 50, null);
    }

    // Arranges hearts vertically on left side
    public static void configure(ArrayList<Heart> input) {
        int offset = 0;
        for (int i = 0; i < input.size(); i++) {
            input.get(i).y+=offset;
            offset +=70;
        }
    }
}
