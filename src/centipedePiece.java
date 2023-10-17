//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class centipedePiece extends Circle implements GameObject{
    private int x, y;
    private String type;
    private String target;
    private String currDirection = "down";
    private final GamePanel game;
    private int score;
    private String input = "right";
    AnimationTimer timer;

    public centipedePiece(GamePanel game, int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.game = game;
        this.setRadius(10);
        if(type == "head") {
            this.setFill(Color.RED);
            score = 150;
        }
        else {
            this.setFill(Color.GREEN);
            score = 100;
        }
        game.add(this, y, x);

        this.timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 50) {
                    lastUpdate = nowDur;  
                    getMoves(input);
                }
            }
        };
    }

    public void setType(String type) {
        this.type = type;
    }

    public void handleCollision(String target) {
        if(target == "Mushroom") {
            if(this.input == "right") {
                this.input = "left";
            }
            else if(this.input == "left") {
                this.input = "right";
            }
            advance();
        }
        if(target == "Ship") {
            Ship ship = game.getShip();
            ship.setLives(ship.getLives() - 1);
            ship.phase();
        }
    }

    public void getMoves(String input) {
        input = this.input;
        if (this.y <= 0 && input == "left") {
            this.input = "right";
            advance();
        }
        else if (this.y >= game.getColumnCount() - 1 && input == "right") {
            this.input = "left";
            advance();
        }
        else {
            switch(input) {
                case "left":
                    target = game.getCanvas()[x][y - 1].getUserData().toString();
                    if(target != "Empty") {
                        handleCollision(target);
                    }
                    else {                
                        this.y--;
                    }
                    break;
                case "right":
                    target = game.getCanvas()[x][y + 1].getUserData().toString();
                    if(target != "Empty") {
                        handleCollision(target);
                    }
                    else {                
                        this.y++;
                    }
                    break;
                default:
                    System.out.println("No Move ?!");
                    break;
            }
        }
        game.setColumnIndex(this, this.y);
    }

    private void advance() {
        if(currDirection == "up") {
            if (x <= 0) {
                currDirection = "down";
                this.x++;
            }
            else {
                this.x--;
            }
        }
        else if(currDirection == "down")  {
            if (x >= game.getRowCount() - 1) {
                currDirection = "up";
                this.x--;
            }
            else {
                this.x++;
            }
        }
        game.setRowIndex(this, x);
    }

    public int getYPos() {
        return this.y;
    }

    public int getXPos() {
        return this.x;
    }

    public void move() {
        timer.start();
    }
}