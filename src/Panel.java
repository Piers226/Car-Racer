import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Panel extends JPanel{

    private Car car;
    private Road img,img2;
    private ArrayList<ObstacleCar> obs;
    private int timeElapsed;
    private boolean up,down,left,right;
    private final int SPEEDX = 3;
    private final int SPEEDY = 1;
    private ArrayList<Heart> lives;
    private Timer t;
    private PowerUp p;
    private int numObstacles;
    private boolean menu;
    private int roadSpeed;

    public Panel() {
        menu = true;
        numObstacles = 20;
        roadSpeed = 7;
        lives = new ArrayList<>();
        lives.add(new Heart());
        lives.add(new Heart());
        lives.add(new Heart());
        img = new Road();
        img2 = new Road();
        img2.setY(-(Main.HEIGHT + 30));
        car = new Car("Res/car.png");
        obs = new ArrayList<>();
        for (int i = 0; i <numObstacles; i++) { //add obstacles to obs list
            obs.add(createObstacleCar());
        }
        p = new PowerUp();
        addMouseListener(new MouseAdapter() { // check if user clicked on either button during menu screen
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getX() >=140 && e.getX() <= 260 && e.getY() >= 370 && e.getY() <= 430) {
                    menu = false;
                    System.out.println("LOADING EASY MODE...");
                    repaint();
                }
                if (e.getX() >=140 && e.getX() <= 260 && e.getY() >= 460 && e.getY() <= 520) {
                    roadSpeed = 12;
                    menu = false;
                    System.out.println("LOADING HARD MODE...");
                    repaint();
                }
            }
        });
        Heart.configure(lives);// order vertically

        //updates screen every 10 milliseconds
        t = new Timer(10, e -> {
            timeElapsed+=10;
            updateGame();
            repaint();
        });
        addKeyListener(new KeyAdapter() { // smooth key using boolean to update players car
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
                if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
                if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
            }
        });

        Road.setSpeed(roadSpeed);
        setFocusable(true);
        t.start();
    }

    // create new obstacles with either blue,green,orange and left,right,middle of road
    public ObstacleCar createObstacleCar() {
        double num = Math.random();
        if ( num<.4)
            return new ObstacleCar("res/BlueCar.png",95); //left side of road
        else if (num<.8)
            return new ObstacleCar("res/GreenCar.png",230); // right side of road
        else
            return new ObstacleCar("res/OrangeCar.png",Main.WIDTH/2-Car.WIDTH/2); //middle road

    }

    public void updateGame() {
        if (menu == false) {
            ObstacleCar.setLastY(ObstacleCar.getLastY() + (int)(Road.getSpeed() * .8)); // move last position of obstacle car down
            img.updateRoad();
            img2.updateRoad();
            //update obstacle cars, if reach end of screen, respawn
            for (int i = 0; i <obs.size() ; i++) {
                obs.get(i).updateObstacle();
                if (obs.get(i).getY() > Main.HEIGHT) {
                    obs.set(i, createObstacleCar());
                }
            }

            //Smooth key operations to update car pos
            if (left) {
                if (car.getX() != Road.LEFT) car.setX(car.getX() - SPEEDX);
            }
            if (right) {
                if (car.getX() + Car.WIDTH != Road.RIGHT) car.setX(car.getX() + SPEEDX);
            }
            if (up) {
                if (car.getY() <= 0) car.setY(Main.HEIGHT);
                else car.setY(car.getY() - SPEEDY);
            }
            if (down) {
                if (car.getY() >= Main.HEIGHT) car.setY(0);
                else car.setY(car.getY() + SPEEDY);
            }

            //colliding with obstacles, if true, remove life and respawn obstacle car
            for (int i = 0; i < obs.size(); i++) {
                if (!p.isOn() && car.rectangle().intersects(obs.get(i).rectangle())) {
                    if (lives.size() > 0) {
                        lives.remove(0);
                        obs.set(i, createObstacleCar());
                    }
                    if (lives.size() == 0) { //if runs out of lives
                        t.stop();
                        repaint();
                    }
                }
            }
            //colliding with powerup, if true, enable fast speed and invincibility
            if (!p.isOn()) p.setY(p.getY() +(int)(Road.getSpeed() * .5));
            if (car.rectangle().intersects(p.rectangle())) {
                p.setY((int) (Math.random() * -10000) - 2000);
                Road.setSpeed(Road.getSpeed() * p.getSpeedUpgrade());
                p.setOn(true);
                p.setInitime(timeElapsed);
            }

            //check if time of powerup is over
            if (p.isOn()) {
                if (timeElapsed - p.getInitime() == p.getTime()) {
                    p.setOn(false);
                    Road.setSpeed(roadSpeed);
                }
            }
        }
        

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        if (menu) { // set up graphics for menu screen
            g2d.setColor(new Color(5, 110, 234));
            g2d.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
            g2d.setColor(new Color(3, 232, 26));
            g2d.fillRect(Main.WIDTH/2-60,Main.HEIGHT/2-30,120,60);
            g2d.fillRect(Main.WIDTH/2-60,Main.HEIGHT/2 + 60,120,60);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(Main.WIDTH/2-60,Main.HEIGHT/2-30,120,60);
            g2d.drawRect(Main.WIDTH/2-60,Main.HEIGHT/2 + 60,120,60);
            showText("Welcome", g2d, Main.WIDTH/2,70,60);
            showText("to", g2d, Main.WIDTH/2,120,60);
            showText("Car Racer", g2d, Main.WIDTH/2,170,60);
            showText("Easy", g2d, Main.WIDTH/2,410,40);
            showText("Hard", g2d, Main.WIDTH/2,500,40);
            showText("HighScore:  37", g2d, Main.WIDTH/2,700,40);
            try {
                g2d.drawImage(ImageIO.read(new File("Res/Car.png")),Main.WIDTH/2-30,200,60,120,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else { // draw each of the components
            img.drawImage(g2d);
            img2.drawImage(g2d);
            car.drawCar(g2d);
            for (ObstacleCar c : obs)
                c.drawCar(g2d);

            for (Heart h : lives) {
                h.drawHeart(g2d);
            }
            p.drawStar(g2d);
            showText("Score: " + img.getLaps(), g2d, Main.WIDTH/2,50,60);
            if (lives.size() == 0) {
                showText("You Lose", g2d, Main.WIDTH/2,Main.HEIGHT / 2,60);
            }
        }

    }
    // display different texts method
    public void showText(String s, Graphics g2d,int x, int y, int size) {
        Font font = new Font("Copperplate",Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(new Color(246, 109, 5, 210));
        Rectangle2D stringBounds = font.getStringBounds(s, g2d.getFontMetrics().getFontRenderContext());
        g2d.drawString(s, (int) (x - stringBounds.getWidth()/2),y);

    }

}

