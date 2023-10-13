import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class GamePanel extends GridPane{
     
    private final ArrayList<Mushroom> shrooms = new ArrayList<Mushroom>();
    private Canvas[][] grid;
    private final Random posRandom = new Random();
    private final int s;
    private Ship ship = new Ship();
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
                canvas = new Canvas(this.getWidth() / rows, this.getHeight() / cols);
                grid[i][j] = canvas;
                grid[i][j].setUserData("Empty");

                this.add(canvas, j, i, 1, 1);
            }
        }
        growShrooms(this.s);
        spawnShip(24, 10);
    }

    public void growShroom(int x, int y) {
        Mushroom shroom = new Mushroom(x, y);
        //Check if a mushroom already exists at the given location
        if (shrooms.contains(shroom)) {
           return;
        }
        if(x >= 20) {
            return;
        }

        shrooms.add(shroom);
        grid[x][y].setUserData("Mushroom");
        this.add(shroom, y, x);
        
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
        if (grid[x][y].getUserData().equals("Mushroom")) {
            spawnShip(x + 1, y);
        }

        ship.setXPos(x);
        ship.setYPos(y);
        grid[x][y].setUserData("Ship");
        this.add(ship, y, x);
    }

    public Ship getShip() {
        return ship;
    }

    //Verify the key presses are occuring
    //Move back to Controller.java
    

    public Canvas[][] getCanvas() {
        return grid;
    }
}
