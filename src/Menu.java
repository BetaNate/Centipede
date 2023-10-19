//Nathan J. Rowe

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Menu extends HBox{
    private Text lives;
    private Text score;
    private ImageView[] liveImg;
    private Label esc;
    private GamePanel game;
    int currLives;

    public Menu(GamePanel game) {
        this.game = game;
        this.currLives  = game.getShip().getLives();
        this.lives = new Text("Lives: " + currLives);
        lives.getStyleClass().add("menuText");
        this.score = new Text("Score: " + game.getScore());
        score.getStyleClass().add("menuText");
        this.esc = new Label("ESC: Pause");
        esc.getStyleClass().add("menuText");
        this.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        Region spacer2 = new Region();
        this.setPrefHeight(50);
        this.setHgrow(spacer, Priority.ALWAYS);
        this.setHgrow(spacer2, Priority.ALWAYS);
        /*
        this.setMargin(lives, new javafx.geometry.Insets(0, 0, 0, 10));
        this.setMargin(esc, new javafx.geometry.Insets(0, 10, 0, 0));
        */
        this.getChildren().addAll(lives, spacer, score, spacer2, esc);
    }

    public void update() {
        this.currLives = game.getShip().getLives();
        this.lives.setText("Lives: " + currLives);
        this.score.setText("Score: " + game.getScore());
    }
}
