//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Ship extends Rectangle implements GameObject{
 
    private int x, y;
    private int lives;
    private String target;
    private final GamePanel game;
    private final AnimationTimer timer;
    private final AnimationTimer bulletSpawn;
    private String input = null;
    Canvas canvas;

    public Ship(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.game = game;

        this.setWidth(20);
        this.setHeight(20);
        this.setFill(Color.BLUE);
        
        game.getCanvas()[x][y].setUserData("Ship");
        game.add(this, y, x);

        timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 30) {
                    lastUpdate = nowDur;    
                    getMoves(input);
                }
            }
        };
        bulletSpawn = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if(game.getHit() == true) {
                    if (nowDur.minus(lastUpdate).toMillis() > 150) {
                        lastUpdate = nowDur;  
                        Bullet bullet = new Bullet(game, game.getShip().getXPos(), game.getShip().getYPos());
                        bullet.move();
                    }
                }
                else {
                    if (nowDur.minus(lastUpdate).toMillis() > 400) {
                        lastUpdate = nowDur;  
                        Bullet bullet = new Bullet(game, game.getShip().getXPos(), game.getShip().getYPos());
                        bullet.move();
                    }
                }
            }
        };
    }

    public Ship(GamePanel game) {
        this(game, 0, 0);
    }

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

    public void handleCollision(String target) {
        if (target == "Mushroom") {
            return;
        }
        if(target == "Centipede") {
            this.lives--;
            game.getCanvas()[x][y].setUserData("Empty");
            canvas.setUserData("Empty");
            game.phase(true);
            game.update.start();
        }
    }

    public void getMoves(String input) {
            game.getCanvas()[x][y].setUserData("Empty");
            game.getChildren().remove(this);
            if (input == "up") {
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
            if(input == "space") {
                bulletSpawn.start();
            }

            this.setOnKeyReleased(e -> {
                    timer.stop();
                    if(e.getCode() == KeyCode.SPACE) {
                        bulletSpawn.stop();
                    }
            });
            game.getCanvas()[x][y].setUserData("Ship");
            game.add(this, y, x);
    }

    public void unphase() {
        this.setVisible(true);
    }

    public void move(String input) {
        this.input = input;
        timer.start();
    }
}
