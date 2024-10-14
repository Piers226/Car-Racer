public class ObstacleCar extends Car {

    private static int lastY; // represents the last Y position of last obstacle car created

    public ObstacleCar(String s, int l) {
        super(s);
        setX(l);
        lastY += (int)(Math.random() * -1000) - Car.HEIGHT; // sets y position randomly above last
        setY(lastY);
    }

    public void updateObstacle() {
        setY(getY() + (int)(Road.getSpeed() * .8)); // updates to move slower than road
    }

    public static void setLastY(int lastY) {
        ObstacleCar.lastY = lastY;
    }

    public static int getLastY() {
        return lastY;
    }
}

