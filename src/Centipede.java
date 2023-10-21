/*
 * Author: Nathan J. Rowe
 * Centipede Class
 * Centipede is an array of centipede parts
 */
//For Animation Timer
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javafx.animation.AnimationTimer;

//See centipedePiece.java for more information
public class Centipede {
    //Centipede head position
    private int x, y;
    //Centipede size
    private int size;
    //Centipede head input
    private String input = "right";
    //Centipede array, represents the centipede
    private centipedePiece[] centipede;
    //GamePanel reference
    private final GamePanel game;
    //Animation timer
    private final AnimationTimer timer;
    //Centipede head reference
    private centipedePiece head;

/*
* ---------------------------
*       Constructor
* ---------------------------
*/
    public Centipede(GamePanel game, int size, int x, int y) {
        //Set properties
        this.size = size;
        this.game = game;
        this.x = x;
        this.y = y;
        //Initialize centipede
        initCentipede();
        //Set animation timer
        this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 60) {
                    lastUpdate = nowDur;  
                    //Move centipede
                    getMoves();   
                }
            }
        };
        //Start animation timer
        move(true);
    }

/*
* ---------------------------
*       Centipede Array
* ---------------------------
*/
    //Initialize centipede
    //Centipede will be an array of centipede pieces
    private void initCentipede() {
        //Initialize centipede array
        centipede = new centipedePiece[this.size];
        //Initialize centipede pieces
        //Centipede head will be at index 0
        head = new centipedePiece(game, this.x, this.y, "head");
        centipede[0] = head;
        //Gives movement input to centipede head
        head.setInput(input);
        //Each successive centipede piece will be a tail piece
        for(int i = 1; i < size; i++) {
             centipede[i] = new centipedePiece(game, this.x, this.y, "tail");
        }
    }
    //Get centipede array
    public centipedePiece[] getArray() {
        return this.centipede;
    }

/*
 * ---------------------------
 *      Event Handling
 * ---------------------------
 */
    //Split centipede
    //Input: index of centipede piece to split at, x and y position of new centipede
    //Output: new centipede
    public void split(int index, int x, int y) {
        this.x = x;
        this.y =y;
        //Remove this centipede from the game
        //If centipede is one piece, remove it
        if(this.size <= 1) {
            game.removeCentipede(this);
            return;
        }
        //If not one piece, split centipede
        //Check if index is at head
        else if(index == 0) {
            clear();
            //Update centipede size
            this.size -= 1;
            initCentipede();
        }
        //If not at head, split centipede
        else {
            clear();
            //Update centipede size
            this.size -= index;
            initCentipede();
            //Spawn new centipede
            Centipede pede = game.spawnCentipede(this.x, this.y, index - 1);
            if (pede != null) {
                //If centipede is moving left, new centipede will move right
                //Visa versa
                if(this.input == "left")
                {
                    pede.getArray()[0].setInput("right");
                }
                else if(this.input == "right") {
                    pede.getArray()[0].setInput("left");
                }
            }
        }
    }
    //Get score of centipede piece at x and y position
    public int getScore(int x, int y) {
        for(int i = 0; i < centipede.length; i++) {
            if(centipede[i].getXPos() == x && centipede[i].getYPos() == y) {
                return centipede[i].getScore();
            }
        }
        return 0;
    }

    //Disable each centipede piece
    public void disable(boolean val) {
        for(int i = 0; i < this.size; i++) {
            centipede[i].disable(val);
        }
    }

    private void clear() {
        disable(true);
        for(int i = 0; i < this.size; i++) {
            game.getChildren().remove(centipede[i]);
        }
        centipede = null;
    }

/*
 * ---------------------------
 *          Movement
 * ---------------------------
 */
  
    //Method to move centipede
    //Centipede will spawn all parts at same time, then move one at a time
    //Centipede tail pieces will follow the piece in front of them
    //Centipede head will direct the movement of the tail pieces
    public void getMoves() {
        for(int i = this.size - 1; i >= 1; i--) {
            centipede[i].setInput(centipede[i - 1].getInput());
            centipede[i].updateData();
            centipede[i].setXPos(centipede[i - 1].getXPos());
            centipede[i].setYPos(centipede[i - 1].getYPos());
        }
        input = head.getInput();
        head.getMoves(input);
        for(int i = this.size - 1; i >= 1; i--) {
            game.setColumnIndex(centipede[i], centipede[i].getYPos());
            game.setRowIndex(centipede[i], centipede[i].getXPos());
            centipede[i].updateData();
        }
    }

    //Animation Control
    public void move(boolean val) {
        if(val) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }
}
