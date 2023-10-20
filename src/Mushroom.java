//Nathan J. Rowe
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Mushroom extends ImageView{

    private int x, y;
    private int health;
    private final GamePanel game;
    private int score = 0;
    Image shroom4 = new Image("resources/images/shroom4.png");
    Image shroom3 = new Image("resources/images/shroom3.png");
    Image shroom2 = new Image("resources/images/shroom2.png");
    Image shroom1 = new Image("resources/images/shroom1.png");

    public Mushroom(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 4;
        this.setImage(shroom4);
        this.game = game;
        game.getCanvas()[x][y].setUserData("Mushroom");
        game.add(this, y, x);
    }

    public int getHealth() {
        return this.health;
    }

    private void update() {
        if(this.health == 3) {
            this.setImage(shroom3);
        }
        else if(this.health == 2) {
            this.setImage(shroom2);
        }
        else if(this.health == 1) {
            this.setImage(shroom1);
        }
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
        update();
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
