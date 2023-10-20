//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bullet extends ImageView implements GameObject{
    private int x, y;
    private GamePanel game;
    AnimationTimer timer;
    private Image bullet = new Image("resources/images/bullet.png");

    public Bullet(GamePanel game, int x, int y) {
        this.x = x;
        this.y = y;
        this.setImage(bullet);
        this.game = game;

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
        
        game.add(this, y , x - 1);
    }

    public int getXPos() {
        return this.x;
    }
    public int getYPos() {
        return this.y;
    }

    public void handleCollision(String target) {
        int score = 0;
        if(target == "Mushroom") {
            game.setHit(true);
            Mushroom shroom = game.getShroom(x, y);
            shroom.setHealth(shroom.getHealth() - 1);
            shroom.destroy();
            game.getChildren().remove(this);
            timer.stop();
            score = shroom.getScore();
        }
        else if(target == "Centipede"){
            //Code to remove a centipede part
            game.setHit(true);
            Centipede pede = game.getCentipede(x, y);
            if(pede == null) {
                game.getCanvas()[x][y].setUserData("Empty");
            }
            else {
            centipedePiece[] centipede = pede.getCentipede();
            game.getChildren().remove(this);
            for(int i = 0; i < centipede.length; i++) {
                if(centipede[i].getXPos() == x && centipede[i].getYPos() == y) {
                    pede.split(i, x, y);
                    break;
                }
            }
            score = pede.getScore(x, y);
            game.growShroom(x, y);
            timer.stop();
            }
        }
        else if(target == "Flea") {
            game.setHit(true);
            Flea flea = game.getFlea(x, y);
            game.removeFlea(flea);
            game.getChildren().remove(this);
            timer.stop();
            score = flea.getScore();
        }
        else if(target == "Spider") {
            game.setHit(true);
            game.getChildren().remove(this);
            timer.stop();
            score = 800;
        }
        else if(x <= 0) {
            game.setHit(false);
            game.getChildren().remove(this);
            timer.stop();
        }
        game.addScore(score);
    }

    public void getMoves(String input) {
            if(this.x < 0) {
                System.out.println("Bullet out of bounds");
                this.x = 0;
            }
            else {
                this.x--;
            }
            game.setRowIndex(this, this.x);
            handleCollision(game.getCanvas()[x][y].getUserData().toString());
    }

    //Bullet movement animation
    public void move() {
        timer.start();
    }
}
