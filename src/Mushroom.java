//Nathan J. Rowe
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Mushroom extends Rectangle{

    private int x, y;
    private int health;
    private final GamePanel game;
    private int score = 0;

    public Mushroom(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 4;
        this.setWidth(20);
        this.setHeight(20);
        this.game = game;
        //this.setX(x + 10);
        //this.setY(y + 10);
        this.setFill(Color.BROWN);
        game.getCanvas()[x][y].setUserData("Mushroom");
        game.add(this, y, x);
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }
    public int getScore() {
        return this.score;
    }
    
    public void destroy() {
        if(this.health <= 0) {
            this.score = 20;
            game.removeShroom(this);
            game.getCanvas()[x][y].setUserData("Empty");
            game.getChildren().remove(this);
        }
    }
    @Override
    public String toString() {
        return ("Mushroom " + this.x + " " + this.y);
    }
}
