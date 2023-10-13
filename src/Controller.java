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
    
    public void shipControls(Ship ship) {
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
                // game.getShip().shoot();
            }
        });
    }

    public void move(Node node, String input) {
        if (node.getClass() == Ship.class) {
            Ship ship = (Ship) node;
            game.getCanvas()[ship.getXPos()][ship.getYPos()].setUserData("Empty");
            game.getChildren().remove(ship);
            if (input == "up") {
                if (ship.getXPos() > 0) {
                    ship.setXPos(ship.getXPos() - 1);
                }
            }
            if (input == "down") {
                if (ship.getXPos() < 24) {
                    ship.setXPos(ship.getXPos() + 1);
                }
            }
            if (input == "left") {
                if (ship.getYPos() > 0) {
                    ship.setYPos(ship.getYPos() - 1);
                }
            }
            if (input == "right") {
                if (ship.getYPos() < 24) {
                    ship.setYPos(ship.getYPos() + 1);
                }
            }
            game.getCanvas()[ship.getXPos()][ship.getYPos()].setUserData("Ship");
            game.add(ship, ship.getYPos(), ship.getXPos());
        }
    }
}
