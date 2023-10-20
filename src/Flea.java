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
                if (nowDur.minus(lastUpdate).toMillis() > 10) {
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
        if(x >= game.getRowCount()) {
            game.getChildren().remove(this);
            return;
        }
        if(game.getCanvas()[x][y].getUserData().toString() == "Ship") {
            handleCollision("Ship");
        }
        if(initX - x > 3) {
            initX = x;
            if(game.getCanvas()[x][y].getUserData().toString() == "Empty") {
                game.growShroom(x, y);
            }
        }
        this.x++; 
    }

    public int getYPos() {
        return this.y;
    } 
    public int getXPos() {
        return this.x;
    }

    public void move(boolean input) {
        if(input) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }
}
