//Nathan J. Rowe

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flea extends ImageView implements GameObject{
    private int x, y, initX;
    private final GamePanel game;
    AnimationTimer timer;
    private final Image fleaImage = new Image("/resources/images/flea.png");
    private final int score = 200;

    public Flea(GamePanel game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.initX = x;
        this.setImage(fleaImage);
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
        game.add(this, y ,x);
        move(true);
    }
    public boolean checkCollision() {
        return false;
    }
    public void handleCollision(String target) {
        if(target == "Ship") {
            game.getShip().handleCollision("Centipede");
        }
    }

    public void getMoves(String input) {
        if(game.getCanvas()[this.x][this.y].getUserData().toString() == "Flea") {
            game.getCanvas()[this.x][this.y].setUserData("Empty");
        }
        if(game.getCanvas()[x][y].getUserData().toString() == "Ship") {
            handleCollision("Ship");
        }
        if((this.x - this.initX) >= 2) {
            if(game.getCanvas()[x][y].getUserData().toString() == "Empty") {
                game.growShroom(x, y);
            }
            this.initX = this.x;
        }
        if(x >= game.getRowCount() - 1) {
            game.removeFlea(this);
            timer.stop();
            return;
        }

        this.x++;

        game.setRowIndex(this, this.x);
        if(game.getCanvas()[this.x][this.y].getUserData().toString() == "Empty") {
            game.getCanvas()[this.x][this.y].setUserData("Flea");
        }
        else {
            return;
        }
    }

    public int getYPos() {
        return this.y;
    } 
    public int getXPos() {
        return this.x;
    }

    public int getScore() {
        return this.score;
    }

    public void move(boolean val) {
        if(val) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }
}
