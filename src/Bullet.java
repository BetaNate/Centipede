//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/*
 * Bullet class
 * Moves up the screen until it hits something
 * Is a GameObject
 */
public class Bullet extends ImageView implements GameObject{
    //Bullet position
    private int x, y;
    //GamePanel reference
    private final GamePanel game;
    //Animation timer
    private final AnimationTimer timer;
    //Bullet image
    private final Image bullet = new Image("resources/images/bullet.png");

    public Bullet(GamePanel game, int x, int y) {
        //Set properties
        this.x = x;
        this.y = y;
        this.setImage(bullet);
        this.game = game;

        //Set animation timer
        this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 10) {
                    //Update last update time
                    lastUpdate = nowDur;  
                    //Move bullet
                    getMoves(null);
                }
            }
        };
        
        //Add to GridPane
        game.add(this, y , x - 1);
    }

/*
* ---------------------------
*      Getters and Setters
* ---------------------------
*/
    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }

    //Get Moves for Bullet
    //Moves up the screen until it hits something
    public void getMoves(String input) {
            this.x--;
            if(this.x < 0) {
                this.x = 0;
            }
            handleCollision(game.getCanvas()[this.x][this.y].getUserData().toString());
            game.setRowIndex(this, this.x);
    }

/*
* ---------------------------
*     Collision Handling
* ---------------------------
*/
    public void handleCollision(String target) {
        //Local variable for object's score
        int score = 0;
        //Check if bullet is at the top of the screen
        //If so, remove it
        //If not empty, check what it hit
        if(x <= 0) {
            if(target == "Empty") {
                game.setHit(false);
                game.getChildren().remove(this);
                move(false);
                return;
            }
        }
        if (target != "Empty") {
            //Set hit to true
            game.setHit(true); 
            if(target == "Mushroom") {
                //Get mushroom
                Mushroom shroom = game.getShroom(x, y);
                //Reduce health
                shroom.setHealth(shroom.getHealth() - 1);
                //Check if health is 0
                shroom.destroy();
                //Get score
                score = shroom.getScore();
            }
            else if(target == "Centipede"){
                //Code to remove a centipede part
                Centipede pede = game.getCentipede(x, y);
                if(pede == null) {
                    game.getCanvas()[x][y].setUserData("Empty");
                }
                else {
                    centipedePiece[] centipede = pede.getCentipede();
                    for(int i = 0; i < centipede.length; i++) {
                        if(centipede[i].getXPos() == x && centipede[i].getYPos() == y) {
                            pede.split(i, x, y);
                            break;
                        }
                    }
                    score = pede.getScore(x, y); 
                    game.growShroom(x, y);
                }
            }
            else if(target == "Flea") {
                Flea flea = game.getFlea(x, y);
                flea.move(false);
                game.removeFlea(flea);
                score = flea.getScore();
            }
            //Add score
            game.addScore(score);
            //Remove bullet
            game.getChildren().remove(this);
            move(false);
        }
    }

    

    //Bullet movement animation Control
    public void move(boolean val) {
        if(val) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }
}
