//Nathan J. Rowe
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

public class GamePanel extends GridPane{
    
    //ArrayLists for data on board
    private final ArrayList<Mushroom> shrooms = new ArrayList<Mushroom>();
    private final ArrayList<Centipede> centipedes = new ArrayList<Centipede>();
    private final ArrayList<Flea> fleas = new ArrayList<Flea>();
    //Play field
    private Canvas[][] grid;
    //Random position generator
    private final Random posRandom = new Random();
    //Number of shrooms on board initialization
    private final int shroomCount;
    //Ship event information
    private Ship ship;
    private boolean bulletHit = false;
    //Current Score
    private int score = 0;
    //Game size information
    private final int rows = 25;
    private final int cols = 25;

    public GamePanel(int shroomCount) {
        this.shroomCount = shroomCount;
        this.getStyleClass().add("background-black");
        initializeField();
    }

    //Initialize game elements
    private void initializeField() {
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
        growShrooms(this.shroomCount);
        spawnShip(rows - 1, cols/2);
    }

    public void growShroom(int x, int y) {
        //Check if a mushroom already exists at the given location
        if(x >= this.grid.length || y >= this.grid.length) {
            return;
        }
        if (shrooms.toString().contains("Mushroom " + x + y)) {
           return;
        }
        //Add a new Mushroom to the field
        Mushroom shroom = new Mushroom(this, x, y);
        shrooms.add(shroom);
    }


    private void growShrooms(int amnt) {
        if (amnt <= 0) {
            return;
        }
        //Choose random location for mushroom
        int x = posRandom.nextInt(rows - 2);
        int y = posRandom.nextInt(cols);
        //Recursively call growShrooms unitl all are added
        growShroom(x, y);
        growShrooms(amnt - 1);
    }

    public Mushroom getShroom(int x, int y) {
        for (Mushroom shroom : shrooms) {
            if(shroom.getXPos() == x && shroom.getYPos() == y) {
                return shroom;
            }
        }
        return null;
    }

    public void removeShroom(Mushroom shroom) {
        if(shrooms.contains(shroom)) {
            shrooms.remove(shroom);
        }
    }

    //Spawn a ship
    private void spawnShip(int x, int y) {
        if (grid[x][y].getUserData().toString() != "Empty") {
            spawnShip(x, posRandom.nextInt(cols - 1));
        }
        else {
            ship = new Ship(this, x, y);
        }
    }

    public Ship getShip() {
        return ship;
    }

    //Spawn a centipede
    public Centipede spawnCentipede(int x, int y, int length) {
        Centipede centipede;
        if(length <= 0) {
            return null;
        }

        if(centipedes.isEmpty()) {
            y = posRandom.nextInt(cols - 1);
            centipede = new Centipede(this, length, 0, y);
        }
        else {
            centipede = new Centipede(this, length, x, y);
        }
        centipedes.add(centipede);
        return centipede;
    }

    public ArrayList<Centipede> getCentipedes() {
        return centipedes;
    }

    //Game method to retrieve a centipede by piece's location
    public Centipede getCentipede(int x, int y){
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

    public void spawnFlea() {
        int y = posRandom.nextInt(cols - 1);
        fleas.add(new Flea(this, 0, y));
    }

    public Flea getFlea(int x, int y) {
        for(Flea flea : fleas) {
            if(flea.getXPos() == x && flea.getYPos() == y) {
                return flea;
            }
        }
        return null;
    }
    public void removeFlea(Flea flea) {
        if(fleas.contains(flea)) {
            this.getChildren().remove(flea);
            grid[flea.getXPos()][flea.getYPos()].setUserData("Empty");
            fleas.remove(flea);
        }
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Canvas[][] getCanvas() {
        return grid;
    }

     public void phase(boolean val) {
        for(Centipede centipede : centipedes) {
            centipede.disable(val);
        }
    }

    //Setter and Getter to check and modify bullet collisions
    public boolean getHit() {
        return this.bulletHit;
    }
    public void setHit(boolean isHit) {
        this.bulletHit = isHit;
    }

    //Clear the game
    public void clear() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++){
                grid[i][j].setUserData("Empty");
            }
        }
        shrooms.clear();
        centipedes.clear();
        this.getChildren().clear();
    }

    //Removes mushrooms that have been destroyed
    //Cleans errors from collisions
    public void cleanUp() {
        for(Mushroom shroom : shrooms) {
            int rIndex = this.getRowIndex(shroom);
            int cIndex = this.getColumnIndex(shroom);

            if(grid[rIndex][cIndex].getUserData().toString() == "Empty") {
                this.getChildren().remove(shroom);
            }
        }
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                Canvas canvas = grid[i][j];
                String data = canvas.getUserData().toString();
                if(data != "Empty") {
                    switch(data) {
                        case "Centipede":
                            if(getCentipede(i, j) == null) {
                                canvas.setUserData("Empty");
                            }
                            break;
                        case "Ship": 
                            int x = ship.getXPos();
                            int y = ship.getYPos();
                            if(x != i || y != j) {
                                canvas.setUserData("Empty");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
