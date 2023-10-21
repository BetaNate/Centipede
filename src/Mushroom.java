/*
 * Author: Nathan J. Rowe
 * Mushroom Class
 * Mushrooms are "walls" in the game
 * Mushrooms are worth 20 points on destruction
 * Mushrooms are destroyed in 4 bullet hits
 */
//For Image
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class Mushroom extends ImageView{
    //Mushroom position
    private int x, y;
    //Mushroom health
    private int health;
    //GamePanel reference
    private final GamePanel game;
    //Mushroom Score
    private int score = 0;
    //Mushroom Image States
    Image shroom4 = new Image("resources/images/shroom4.png");
    Image shroom3 = new Image("resources/images/shroom3.png");
    Image shroom2 = new Image("resources/images/shroom2.png");
    Image shroom1 = new Image("resources/images/shroom1.png");

/*
 * ---------------------------
 *        Constructor
 * ---------------------------
 */
    public Mushroom(GamePanel game, int x, int y) {
        //Set properties
        this.x = x;
        this.y = y;
        this.health = 4;
        this.setImage(shroom4);
        this.game = game;
        //Add to game
        game.getCanvas()[x][y].setUserData("Mushroom");
        game.add(this, y, x);
    }

/*
 * ---------------------------
 *     Getters and Setters
 * ---------------------------
 */
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

/*
 * ---------------------------
 *      Mushroom Methods
 * ---------------------------
 */
    //Update mushroom image
    //Called when mushroom is hit
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
    
    //Destroy mushroom
    //Called when mushroom is hit
    //If health is 0, mushroom is destroyed
    public void destroy() {
        update();
        //Destroy mushroom
        if(this.health <= 0) {
            this.score = 20;
            game.removeShroom(this);
            game.getCanvas()[x][y].setUserData("Empty");
            game.getChildren().remove(this);
        }
    }

    //Mushroom toString
    //Used to prevent duplicate mushrooms in GamePanel
    @Override
    public String toString() {
        return ("Mushroom " + this.x + " " + this.y);
    }
}
