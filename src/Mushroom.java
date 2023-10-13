import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Mushroom extends Rectangle{

    private double x, y;
    private int health;

    public Mushroom(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = 4;
        this.setWidth(20);
        this.setHeight(20);
        this.setX(x + 10);
        this.setY(y + 10);
        this.setFill(Color.BROWN);
    }

    /*
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
*/
    public int getHealth() {
        return health;
    }
    
}
