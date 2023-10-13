import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Ship extends Rectangle{
 
    private int x, y;
    private int lives;

    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.setWidth(20);
        this.setHeight(20);
        this.setX(x);
        this.setY(y);
        this.setFill(Color.BLUE);
    }

    public Ship() {
        this(0, 0);
    }

    public void setXPos(int x) {
        this.x = x;
    }
    public void setYPos(int y) {
        this.y = y;
    }


    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }
}
