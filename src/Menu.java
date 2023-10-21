/*
 * Author: Nathan J. Rowe
 * Menu Class
 * Adds menus to the game
 */

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Menu extends HBox{
    //Menu Labels
    private final Label score;
    private final Label esc;
    //GamePanel reference
    private final GamePanel game;
    //Images
    private final Image life = new Image("resources/images/ship.png");
    private final Image controls = new Image("resources/images/controls.png");
    private final ImageView controlsView = new ImageView(controls);
    //Lives Display
    private final HBox livesBox;
    private int currLives;

/*
 * ---------------------------
 *       Constructor
 * ---------------------------
 */
    public Menu(GamePanel game) {
        //Add CSS
        this.getStyleClass().addAll("menu", "border");
        this.game = game;
        this.currLives  = game.getShip().getLives();
        //Initialize Information
        this.livesBox = new HBox();
        this.livesBox.getStyleClass().add("menu");
        for(int i = 0; i < currLives; i++) {
            livesBox.getChildren().add(new ImageView(life));
        }
        this.score = new Label("" + game.getScore());
        score.getStyleClass().add("menuText");
        this.esc = new Label("ESC: Pause");
        esc.getStyleClass().add("menuText");
        this.setAlignment(Pos.CENTER);
        //Initialize Spacers
        Region spacer = new Region();
        Region spacer2 = new Region();
        this.setHgrow(spacer, Priority.ALWAYS);
        this.setHgrow(spacer2, Priority.ALWAYS);
        this.setPrefHeight(50);
        //Add Children
        this.getChildren().addAll(livesBox, spacer, score, spacer2, esc);
    }

    //Update the menu
    public void update() {
        this.currLives = game.getShip().getLives();
        this.livesBox.getChildren().clear();
        for(int i = 0; i < currLives; i++) {
            this.livesBox.getChildren().add(new ImageView(life));
        }
        this.score.setText("" + game.getScore());
    }

    //Update the pause button
    public void paused(boolean pause) {
        if(pause) {
            esc.setText("ESC: Unpause");
        }
        else {
            esc.setText("ESC: Pause");
        }
    }

    //Display the controls
    //Will be separate from the menu
    //Is displayed by image
    public ImageView controls() {
        return controlsView;
    }
}
