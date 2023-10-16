//Nathan J. Rowe
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

public class GamePanel extends GridPane{
     
    private final ArrayList<String> shrooms = new ArrayList<String>();
    private Canvas[][] grid;
    private final Random posRandom = new Random();
    private final int s;
    private Ship ship;
    Controller controller;

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
        if(x >= this.grid.length - 4) {
            return;
        }
        if (shrooms.contains("Mushroom " + x + y)) {
           return;
        }

        Mushroom shroom = new Mushroom(this, x, y);
        shrooms.add(shroom.toString());
    }

    private void growShrooms(int amnt) {
        if (amnt <= 0) {
            return;
        }
        int x = posRandom.nextInt(25);
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

    public Ship getShip() {
        return ship;
    }

    public Canvas[][] getCanvas() {
        return grid;
    }

}
