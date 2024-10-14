import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 400;

    public Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setTitle("Car Go Fast");
        // center the window on the viewing screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) screenDim.getWidth() / 2 - WIDTH / 2, (int) screenDim.getHeight() / 2 - HEIGHT / 2);
        // create a panel component and add it to the window
        this.add(new Panel());
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.setVisible(true);
    }

}