//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

public class GamePanel extends GridPane{
     
    private final ArrayList<Mushroom> shrooms = new ArrayList<Mushroom>();
    private final ArrayList<Centipede> centipedes = new ArrayList<Centipede>();
    private Canvas[][] grid;
    private final Random posRandom = new Random();
    private final int s;
    private Ship ship;
    private boolean bulletHit = false;

    public GamePanel(Canvas field, int s) {
        this.setWidth(field.getWidth());
        this.setHeight(field.getHeight());
        this.s = s;

        String background = "-fx-background-color: black;";
        field.setStyle(background);
        this.setStyle(background);
    }

    public void initializeField() {
        int rows = 25;
        int cols = 25;
        grid = new Canvas[rows][cols];
        Canvas canvas;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                canvas = new Canvas(20, 20);
                grid[i][j] = canvas;
                grid[i][j].setUserData("Empty");

                this.add(canvas, j, i, 1, 1);
            }
        }
        growShrooms(this.s);
        spawnShip(24, 10);
    }

    public void growShroom(int x, int y) {
        //Check if a mushroom already exists at the given location
        if(x >= this.grid.length || y >= this.grid.length) {
            return;
        }
        if (shrooms.toString().contains("Mushroom " + x + y)) {
           return;
        }

        Mushroom shroom = new Mushroom(this, x, y);
        shrooms.add(shroom);
    }

    private void growShrooms(int amnt) {
        if (amnt <= 0) {
            return;
        }
        int x = posRandom.nextInt(25 - 4);
        int y = posRandom.nextInt(25);

        growShroom(x, y);
        growShrooms(amnt - 1);
    }

    public void spawnShip(int x, int y) {
        ship = new Ship(this, x, y);
        if (grid[x][y].getUserData().toString() != "Empty") {
            if(grid[x][y].getUserData().toString() != "Ship") {
            spawnShip(x + 1, y);
            }
        }
    }

    public Centipede spawnCentipede(int x, int y, int length) {
        if (length <= 0) {
            return null;
        }
        Centipede centipede = new Centipede(this, length, x, y);
        centipedes.add(centipede);
        return centipede;
    }

    public ArrayList<Centipede> getCentipedes() {
        return centipedes;
    }

    public Centipede getPart(int x, int y){
        for(Centipede centipede : centipedes) {
            centipedePiece[] centipedeArr = centipede.getCentipede();
            int len = centipedeArr.length;
            for(int i = 0; i < len; i++) {
                if(centipedeArr[i].getXPos() == x && centipedeArr[i].getYPos() == y) {
                    return centipede;
                }
            }
        }
        return null;
    }

    public Ship getShip() {
        return ship;
    }

    public Mushroom getShroom(int x, int y) {
        for (Mushroom shroom : shrooms) {
            if(shroom.getXPos() == x && shroom.getYPos() == y) {
                return shroom;
            }
        }
        return null;
    }
    public Canvas[][] getCanvas() {
        return grid;
    }

    public void clear() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++){
                grid[i][j].setUserData("Empty");
            }
        }
        for(Mushroom shroom : shrooms) {
            shroom.destroy();
        }
        for(Centipede centipede : centipedes) {
            centipedes.remove(centipede);
        }
        this.getChildren().clear();
    }

    public void removeShroom(Mushroom shroom) {
        if(shrooms.contains(shroom)) {
            shrooms.remove(shroom);
        }
    }

    //Removes mushrooms that have been destroyed
    //Cleans errors from collisions
    private void cleanUp() {
        for(Mushroom shroom : shrooms) {
            int rIndex = this.getRowIndex(shroom);
            int cIndex = this.getColumnIndex(shroom);

            if(grid[rIndex][cIndex].getUserData().toString() == "Empty") {
                this.getChildren().remove(shroom);
            }
        }
        
    }

    //Setter and Getter to check and modify bullet collisions
    public boolean getHit() {
        return this.bulletHit;
    }
    public void setHit(boolean isHit) {
        this.bulletHit = isHit;
    }

    public void start() {
         AnimationTimer timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 25) {
                    lastUpdate = nowDur;  
                    cleanUp();
                    if(centipedes.size() == 0) {
                        spawnCentipede(0, 0, 12);
                    }
                }
            }
        };
        timer.start();
    }
}
