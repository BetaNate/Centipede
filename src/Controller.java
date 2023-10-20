//Nathan J. Rowe
import java.beans.EventHandler;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Controller {
    
        private GamePanel game;
        private Menu menu;
        KeyCode input = null;
        AnimationTimer centipedeSpawn;
        private boolean paused = false;
        AnimationTimer update;
        AnimationTimer timer;
        Image gameOver = new Image("resources/images/gameover.png");
        Image restartButton = new Image("resources/images/restart.png");
        ImageView gameOverView = new ImageView(gameOver);
        ImageView restart = new ImageView(restartButton);
        VBox gameOverBox = new VBox(gameOverView, restart);

        public Controller(BorderPane root, Canvas field) {
            this.game = new GamePanel(field, 80);
            root.setCenter(game);
            game.initializeField();
            shipControls(game.getShip());
            this.menu = new Menu(game);
            root.setTop(menu);
            start(root);
            gameOverBox.setAlignment(Pos.CENTER);
            restart.getStyleClass().add("restart");
            restart.setOnMouseEntered(e -> {
                restart.setOpacity(0.5);
                restart.setOnMouseExited(e2 -> {
                    restart.setOpacity(1);
                });
            });
            restart.setOnMouseClicked(e -> {
                new Controller(root, field);
            });
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
                if(input == KeyCode.ESCAPE && !paused) {
                    paused = true;
                    ship.setOnKeyPressed(null);
                    pause();
                    unpause();
                    menu.paused(paused);
                }
            });
        }

        private void pause() {
            if(paused == true) {
                for(Centipede centipede : game.getCentipedes()) {
                    centipede.timer.stop();
                }
                timer.stop();
                update.stop();
            }
            else {
                for(Centipede centipede : game.getCentipedes()) {
                    centipede.timer.start();
                }
                timer.start();
                update.start();
            }
        }
    
        private boolean checkDeath(BorderPane root) {
            boolean death = false;
            if(game.getShip().getLives() < 0) {
                death = true;
                update.stop();
                game.clear();
                root.setCenter(gameOverBox);
            }
            return death;
        }

        private void unpause() {
            game.getShip().setOnKeyPressed(e -> {
                input = e.getCode();
                if (input == KeyCode.ESCAPE) {
                    paused = false;
                    game.setOnKeyPressed(null);
                    pause();
                    shipControls(game.getShip());
                    menu.paused(paused);
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
            else if (node.getClass() == Flea.class) {
                Flea spider = (Flea) node;
                spider.getMoves(game, null);
            }
            else {
                return;
            }
        }

        public void start(BorderPane root) {
         timer = new AnimationTimer() {
            int currLives = game.getShip().getLives();
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 25) {
                    lastUpdate = nowDur;  
                    if(checkDeath(root)) {
                        this.stop();
                    }
                    else {
                    if(game.getShip().getLives() != currLives) {
                        currLives = game.getShip().getLives();
                        update.start();
                    }
                    if(game.getScore() == 12000) {
                        game.getShip().setLives(game.getShip().getLives() + 1);
                    }
                    game.cleanUp();
                    menu.update();
                    if(game.getCentipedes().size() == 0) {
                        game.spawnCentipede(0, 0, 12);
                    }
                }
                }
                
            }
        };

        update = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            int count = 0;
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 4000) {
                    if(count >= 300) {
                        game.phase(false);
                        count = 0;
                        this.stop();
                    }
                    count++;
                    System.out.println(count);
                }
            }
        };
        timer.start();
    }
}
