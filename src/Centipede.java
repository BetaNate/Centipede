//Nathan J. Rowe

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Centipede extends Node{
    private int x, y;
    private final int size;
    private final centipedePiece[] centipede;
    private final GamePanel game;
    private String target;
    private String input;
    private AnimationTimer timer;
    centipedePiece head;

    public Centipede(GamePanel game, int size, int x, int y) {
        this.size = size;
        this.game = game;
        centipede = new centipedePiece[size];
        head = new centipedePiece(game, x, y, "head");
        head.setInput("right");
        this.x = x;
        this.y = y;
        centipede[0] = head;
        for(int i = 1; i < size; i++) {
             centipede[i] = new centipedePiece(game, x, y, "tail");
        }
         this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 60) {
                    lastUpdate = nowDur;  
                      
                    for(int i = size - 1; i >= 1; i--) {
                        centipede[i].setXPos(centipede[i - 1].getXPos());
                        centipede[i].setYPos(centipede[i - 1].getYPos());
                    }
                    input = head.getInput();
                    head.getMoves(input);
                    for(int i = size - 1; i >= 1; i--) {
                        game.setColumnIndex(centipede[i], centipede[i].getYPos());
                        game.setRowIndex(centipede[i], centipede[i].getXPos());
                    }
                }
            }
        };
    }


    //Method to move centipede
    //Centipede will spawn all parts at same time, then move one at a time
    //Centipede tail pieces will follow the piece in front of them
    //Centipede head will direct the movement of the tail pieces
    public void move() {
        timer.start();
    }
}
