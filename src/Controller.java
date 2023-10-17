//Nathan J. Rowe
import java.beans.EventHandler;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCodeCombination;

public class Controller {

    private GamePanel game;
    private Score score;
    KeyCode input = null;
    AnimationTimer bulletSpawn;

    public Controller(BorderPane root, Canvas field) {
        this.game = new GamePanel(field, 80);
        this.score = new Score();
        root.setCenter(game);
        game.initializeField();
        shipControls(game.getShip());
        Centipede pedle = new Centipede (game, 12, 0, 0);
        pedle.move();
        game.start();
        System.out.println(game.getColumnCount());
    }  
    
    private void shipControls(Ship ship) {
        ship.setFocusTraversable(true);
        ship.setOnKeyPressed(e -> {
            input = e.getCode();
            if (input == KeyCode.LEFT || input == KeyCode.A) {
                move(ship, "left");
            }
            if (input == KeyCode.RIGHT || input == KeyCode.D) {
                move(ship, "right");
            }
            if(input == KeyCode.UP || input == KeyCode.W) {
                move(ship, "up");
            }
            if(input == KeyCode.DOWN || input == KeyCode.S) {
                move(ship, "down");
            }
            if(input == KeyCode.SPACE) {
                move(ship, "space");
            }
        });
    }

    private void move(Node node, String input) {
        if (node.getClass() == Ship.class) {
            Ship ship = (Ship) node;
            ship.move(input);
        }
        else if (node.getClass() == Bullet.class) {
            Bullet bullet = (Bullet) node;
            bullet.move();
        }
        else if (node.getClass() == Spider.class) {
            Spider spider = (Spider) node;
            spider.getMoves(game, null);
        }
        else {
            return;
        }
    }

}
