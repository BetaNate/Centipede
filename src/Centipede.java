//Nathan J. Rowe

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Centipede extends Node{
    private int x, y;
    private final int size;
    private centipedePiece[] centipede;
    private final GamePanel game;
    private String target;
    private String input = "right";
    private AnimationTimer timer;
    centipedePiece head;

    public Centipede(GamePanel game, int size, int x, int y) {
        this.size = size;
        this.game = game;
        this.x = x;
        this.y = y;
        initCentipede(this.size);
         this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 60) {
                    lastUpdate = nowDur;  
                      
                    for(int i = centipede.length - 1; i >= 1; i--) {
                        centipede[i].updateData();
                        centipede[i].setXPos(centipede[i - 1].getXPos());
                        centipede[i].setYPos(centipede[i - 1].getYPos());
                    }
                    input = head.getInput();
                    head.getMoves(input);
                    for(int i = centipede.length - 1; i >= 1; i--) {
                        game.setColumnIndex(centipede[i], centipede[i].getYPos());
                        game.setRowIndex(centipede[i], centipede[i].getXPos());
                        centipede[i].updateData();
                    }
                }
            }
        };
        move();
    }

    private void initCentipede(int size) {
        centipede = new centipedePiece[size];
        head = new centipedePiece(game, this.x, this.y, "head");
        head.setInput(input);
        centipede[0] = head;
        for(int i = 1; i < size; i++) {
             centipede[i] = new centipedePiece(game, this.x, this.y, "tail");
        }
    }
    public centipedePiece[] getCentipede() {
        return centipede;
    }

    public void split(int index, int x, int y) {
        timer.stop();
        this.x = x;
        this.y =y;
        this.clear();
        if(centipede.length <= 1) {
            this.clear();
            game.getCentipedes().remove(this);
            return;
        }
        else if(index == 0) {
            initCentipede(centipede.length - 1);
            timer.start();
            if(this.input == "left")
            {
                this.input = "right";
            }
            else if(this.input == "right") {
                this.input = "left";
            }
        }
        else {
            initCentipede(centipede.length - index);
            timer.start();
            Centipede pede = game.spawnCentipede(this.x, this.y, index - 1);
            if (pede != null) {
            if(this.input == "left")
            {
                pede.getCentipede()[0].setInput("right");
            }
            else if(this.input == "right") {
                pede.getCentipede()[0].setInput("left");
            }
            }
        }
    }

    public void clear() {
        for(int i = 0; i < centipede.length; i++) {
            int currX = centipede[i].getXPos();
            int currY = centipede[i].getYPos();
            game.getCanvas()[currX][currY].setUserData("Empty");
            game.getChildren().remove(centipede[i]);
        }
        //timer.stop();
    }
    //Method to move centipede
    //Centipede will spawn all parts at same time, then move one at a time
    //Centipede tail pieces will follow the piece in front of them
    //Centipede head will direct the movement of the tail pieces
    public void move() {
        timer.start();
    }
}
