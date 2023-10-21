/*
 * Author: Nathan J. Rowe
 * Flea Class
 * Flea is a GameObject
 * Flea moves down the screen until it hits something
 * Flea is worth 200 points
 * Spawns Mushrooms every 2 spaces
 */
//For Animation Timer
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javafx.animation.AnimationTimer;
//For Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flea extends ImageView implements GameObject{
    //Flea position
    private int x, y, initX;
    //GamePanel reference
    private final GamePanel game;
    //Animation Timer
    private final AnimationTimer timer;
    //Flea Image
    private final Image fleaImage = new Image("/resources/images/flea.png");
    //Flea Score
    private final int score = 200;

/*
 * ---------------------------
 *      Flea Constructor
 * ---------------------------
 * Input: Current Game, x position, y position
 */
    public Flea(GamePanel game, int x, int y) {
        //Set properties
        this.game = game;
        this.x = x;
        this.y = y;
        this.initX = x;
        this.setImage(fleaImage);
        //Set Animation Timer
        this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 50) {
                    lastUpdate = nowDur;  
                    getMoves(null);
                }
            }
        };
        //Add to GamePanel
        game.add(this, y ,x);
        //Start Flea
        move(true);
    }

/*
 * ---------------------------
 *     Collision Handler
 * ---------------------------
 */
    //Handle Collision with Ship
    //Same as Centipede
    public void handleCollision(String target) {
        if(target == "Ship") {
            game.getShip().handleCollision("Centipede");
        }
    }

/*
 * ---------------------------
 *      Flea Movement
 * ---------------------------
 */
    //Flea moves down the screen
    //Spawns Mushrooms every 2 spaces
    public void getMoves(String input) {
        //If current position is not empty, set to empty
        if(game.getCanvas()[this.x][this.y].getUserData().toString() == "Flea") {
            game.getCanvas()[this.x][this.y].setUserData("Empty");
        }
        //Check for collision
        if(game.getCanvas()[x][y].getUserData().toString() == "Ship") {
            handleCollision("Ship");
        }
        //Check if Flea has moved 2 spaces
        if((this.x - this.initX) >= 2) {
            if(game.getCanvas()[x][y].getUserData().toString() == "Empty") {
                game.growShroom(x, y);
            }
            this.initX = this.x;
        }
        //Check if Flea has reached bottom of screen
        if(x >= game.getRowCount() - 1) {
            game.removeFlea(this);
            timer.stop();
            return;
        }

        //Move Flea down
        this.x++;
        game.setRowIndex(this, this.x);
        if(game.getCanvas()[this.x][this.y].getUserData().toString() == "Empty") {
            game.getCanvas()[this.x][this.y].setUserData("Flea");
        }
    }

/*
 * ---------------------------
 *          Getters
 * ---------------------------
 */
    public int getYPos() {
        return this.y;
    } 
    public int getXPos() {
        return this.x;
    }

    public int getScore() {
        return this.score;
    }

/*
 * ---------------------------
 *         Animation
 * ---------------------------
 */
    public void move(boolean val) {
        if(val) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }

/*
 * ---------------------------
 *     Unused Methods
 * ---------------------------
 */
    public boolean checkCollision() {
        return false;
    }
}
