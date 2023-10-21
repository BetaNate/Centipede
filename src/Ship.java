/*
 * Author: Nathan J. Rowe
 * Ship Class
 * Ship is a GameObject
 * Ship is controlled by the player
 */
//For Animation Timer
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javafx.animation.AnimationTimer;
//For Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//For Movement
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

public class Ship extends ImageView implements GameObject{
    //Ship position
    private int x, y;
    //Ship lives
    private int lives;
    //Collision target
    private String target;
    //Ship input
    private String input = null;
    //GamePanel reference
    private final GamePanel game;
    private Canvas canvas;
    //Animation Timer
    private final AnimationTimer timer;
    private boolean firing = false;
    //Ship Image
    private Image ship = new Image("resources/images/ship.png");

/*
 * ---------------------------
 *        Constructor
 * ---------------------------
 */
    public Ship(GamePanel game, int x, int y) {
        //Set properties
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.game = game;
        this.setImage(ship);
        //Set Animation Timer
        timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            //For firing
            private Duration lastFire = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                //For firing
                Duration fireDur = Duration.of(now, ChronoUnit.NANOS);
                //For movement
                if (nowDur.minus(lastUpdate).toMillis() > 30) {
                    lastUpdate = nowDur;    
                    getMoves(input);
                }
                //For firing
                //Firing rate is faster if previous bullet hit
                if(firing) {
                    if(game.getHit()) {
                        if (fireDur.minus(lastFire).toMillis() > 150) {
                            lastFire = fireDur;  
                            GameObject bullet = new Bullet(game, game.getShip().getXPos(), game.getShip().getYPos());
                            bullet.move(true);
                        }
                    }
                    else {
                        if (fireDur.minus(lastFire).toMillis() > 300) {
                            lastFire = fireDur;  
                            GameObject bullet = new Bullet(game, game.getShip().getXPos(), game.getShip().getYPos());
                            bullet.move(true);
                        }
                    }
                }
            }
        };
        //Add to game
        game.getCanvas()[x][y].setUserData("Ship");
        game.add(this, y, x);
    }

    //Overloaded constructor
    public Ship(GamePanel game) {
        this(game, 0, 0);
    }

/*
 * ---------------------------
 *     Getters and Setters
 * ---------------------------
 */
    public void setXPos(int x) {
        this.x = x;
    }
    public void setYPos(int y) {
        this.y = y;
    }

    public void setLives(int life) {
        this.lives = life;
    }
    public int getLives() {
        return this.lives;
    }

    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }

/*
* ---------------------------
*     Collision Handling
* ---------------------------
*/
    public void handleCollision(String target) {
        if (target == "Mushroom") {
            return;
        }
        if(target == "Centipede") {
            this.lives--;
            game.getCanvas()[x][y].setUserData("Empty");
            canvas.setUserData("Empty");
            game.phase(true);
        }
    }

/*
 * ---------------------------
 *     Ship Controls
 * ---------------------------
 */
    public void getMoves(String input) {
            //Update position
            game.getCanvas()[x][y].setUserData("Empty");
            game.getChildren().remove(this);
            if (input == "up") {
                //Check if in bounds
                if (x > 5) {
                    canvas = game.getCanvas()[x-1][y];
                    target = canvas.getUserData().toString();
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
                    canvas = game.getCanvas()[x + 1][y];
                    target = canvas.getUserData().toString();
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
                    canvas = game.getCanvas()[x][y - 1];
                    target = canvas.getUserData().toString();
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
                    canvas = game.getCanvas()[x][y + 1];
                    target = canvas.getUserData().toString();
                    if(target == "Empty") {
                        y++;
                    }
                    else {
                        handleCollision(target);
                    }
                }
            }
            //Check if firing
            if(input == "space") {
                firing = true;
            }
            //Stop movement if no input
            this.setOnKeyReleased(e -> {
                    this.input = null;
                    if(e.getCode() == KeyCode.SPACE) {
                        firing = false;
                    }
            });
            //Update position
            game.getCanvas()[x][y].setUserData("Ship");
            game.add(this, y, x);
    }

/*
 * ---------------------------
 *      Animation Timer
 * ---------------------------
 */

    public void move(String input) {
        this.input = input;
        timer.start();
    }

/*
 * ---------------------------
 *     Unimplemented Methods
 * ---------------------------
 */

    @Override
    public void move(boolean val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
}
