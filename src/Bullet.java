//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Bullet extends Circle implements GameObject{
    private int x, y;
    private GamePanel game;

    public Bullet(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.setRadius(5);
        this.setFill(Color.RED);
        this.game = game;

        game.getCanvas()[x][y].setUserData("Bullet");
        game.add(this, y, x);
    }

    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }

    public boolean checkCollision() {
        return false;
    }
    public void handleCollision(String target) {
        return;
    }

    public void getMoves(String input) {
        return;
    }

    //Bullet movement animation
    public void move() {
        AnimationTimer timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 25) {
                    lastUpdate = nowDur;    
                    getMoves(null);
                }
            }
        };
        timer.start();
    }
}
