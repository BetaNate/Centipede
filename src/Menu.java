//Nathan J. Rowe

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Menu extends HBox{
    private Label lives;
    private Label score;
    private ImageView[] liveImg;
    private Label esc;
    private GamePanel game;
    private Image life = new Image("resources/images/ship.png");
    private ImageView[] livesImg;
    private HBox livesBox;
    int currLives;

    public Menu(GamePanel game) {
        this.getStyleClass().addAll("menu", "border");
        this.game = game;
        this.currLives  = game.getShip().getLives();
        //this.lives = new Label("Lives: " + currLives);
       // lives.getStyleClass().add("menuText");
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
        Region spacer = new Region();
        Region spacer2 = new Region();
        this.setPrefHeight(50);
        this.setHgrow(spacer, Priority.ALWAYS);
        this.setHgrow(spacer2, Priority.ALWAYS);
        /*
        this.setMargin(lives, new javafx.geometry.Insets(0, 0, 0, 10));
        this.setMargin(esc, new javafx.geometry.Insets(0, 10, 0, 0));
        */
        this.getChildren().addAll(livesBox, spacer, score, spacer2, esc);
    }

    public void update() {
        this.currLives = game.getShip().getLives();
        this.livesBox.getChildren().clear();
        for(int i = 0; i < currLives; i++) {
            this.livesBox.getChildren().add(new ImageView(life));
        }
        this.score.setText("" + game.getScore());
    }

    public void paused(boolean pause) {
        if(pause) {
            esc.setText("ESC: Unpause");
        }
        else {
            esc.setText("ESC: Pause");
        }
    }
}
