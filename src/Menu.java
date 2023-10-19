//Nathan J. Rowe

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Menu extends HBox{
    private Label lives;
    private Label score;
    private ImageView[] liveImg;
    private Label esc;
    private GamePanel game;
    int currLives;

    public Menu(GamePanel game) {
        this.game = game;
        this.currLives  = game.getShip().getLives();
        this.lives = new Label("Lives: " + currLives);
        this.score = new Label("Score: " + game.getScore());
        this.esc = new Label("ESC: Pause");
        this.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        Region spacer2 = new Region();
        this.setPrefHeight(50);
        this.setHgrow(spacer, Priority.ALWAYS);
        this.setHgrow(spacer2, Priority.ALWAYS);
        this.setMargin(lives, new javafx.geometry.Insets(0, 0, 0, 10));
        this.setMargin(esc, new javafx.geometry.Insets(0, 10, 0, 0));
        this.getChildren().addAll(lives, spacer, score, spacer2, esc);
    }

    public void update() {
        this.currLives = game.getShip().getLives();
        this.lives.setText("Lives: " + currLives);
        this.score.setText("Score: " + game.getScore());
    }
}
