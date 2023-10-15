import java.beans.EventHandler;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Controller {

    private GamePanel game;
    private Score score;

    public Controller(BorderPane root, Canvas field) {
        this.game = new GamePanel(field, 80);
        this.score = new Score();
        root.setCenter(game);
        game.initializeField();
        shipControls(game.getShip());
    }  
    
    private void shipControls(Ship ship) {
        ship.setFocusTraversable(true);
        ship.setOnKeyPressed(e -> {
            KeyCode input = e.getCode();
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
                e.consume();
            }
            if(input == KeyCode.SPACE) {
                Bullet bullet = new Bullet(game, ship.getXPos(), ship.getYPos());
                move(bullet, null);
            }
        });
    }

    private void move(Node node, String input) {
        if (node.getClass() == Ship.class) {
            Ship ship = (Ship) node;
            ship.getMoves(input);
        }
        else if (node.getClass() == Bullet.class) {
            Bullet bullet = (Bullet) node;
            bullet.getMoves(null);
        }
        else if (node.getClass() == Centipede.class) {
            Centipede centipede = (Centipede) node;
            centipede.getMoves(game, null);
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
