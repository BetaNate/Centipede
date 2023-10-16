//Nathan J. Rowe
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Ship extends Rectangle implements GameObject{
 
    private int x, y;
    private int lives;
    private String target;
    private final GamePanel game;

    public Ship(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.game = game;

        this.setWidth(20);
        this.setHeight(20);
        this.setFill(Color.BLUE);
        
        game.getCanvas()[x][y].setUserData("Ship");
        game.add(this, y, x);
    }

    public Ship(GamePanel game) {
        this(game, 0, 0);
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

    public void handleCollision(String target) {
        if (target == "Mushroom") {
            return;
        }
    }

    public void getMoves(String input) {
            game.getCanvas()[x][y].setUserData("Empty");
            game.getChildren().remove(this);
            if (input == "up") {
                if (x > 5) {
                    target = game.getCanvas()[x - 1][y].getUserData().toString();
                    if(target == "Empty") {
                        x--;
                    }
                    else {
                        handleCollision(target);
                    }
                }
            }
            if (input == "down") {
                if (x < 24) {
                    target = game.getCanvas()[x + 1][y].getUserData().toString();
                    if(target == "Empty") {
                        x++;
                    }
                    else {
                        handleCollision(target);
                    }
                }
            }
            if (input == "left") {
                if (y > 0) {
                    target = game.getCanvas()[x][y - 1].getUserData().toString();
                    if(target == "Empty") {
                      y--;
                    }
                    else {
                        handleCollision(target);
                    }
                }
            }
            if (input == "right") {
                if (y < 24) {
                    target = game.getCanvas()[x][y + 1].getUserData().toString();
                    if(target == "Empty") {
                        y++;
                    }
                    else {
                        handleCollision(target);
                    }
                }
            }
            game.getCanvas()[x][y].setUserData("Ship");
            game.add(this, y, x);
    }
}
