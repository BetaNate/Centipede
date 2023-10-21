/*
 * Author: Nathan J. Rowe
 * GamePanel Class
 * GamePanel is a GridPane
 * GamePanel is the main game board
 * Contains information on all GameObjects
 * Contains methods to spawn and remove GameObjects
 * Used in most classes
 */
//For Object Storage
import java.util.ArrayList;
//For Random Position
import java.util.Random;
//For Game
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
    private long score = 0;
    //Game size information
    private final int rows = 25;
    private final int cols = 25;

/*
 * ---------------------------
 *        Constructor
 * ---------------------------
 * Input: Number of shrooms to spawn
 */
    public GamePanel(int shroomCount) {
        this.shroomCount = shroomCount;
        this.getStyleClass().add("background-black");
        initializeField();
    }

    //Initialize game elements
    private void initializeField() {
        //Create a grid of canvases
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
        //Spawn shrooms
        growShrooms(this.shroomCount);
        //Spawn ship
        spawnShip(rows - 1, cols/2);
    }

    public Canvas[][] getCanvas() {
        return grid;
    }

/*
 * ---------------------------
 *     Mushroom Methods
 * ---------------------------
 */
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

/*
 * ---------------------------
 *        Ship Methods
 * ---------------------------
 */
    //Spawn a ship
    private void spawnShip(int x, int y) {
        //If something is already at the location, try again
        if (grid[x][y].getUserData().toString() != "Empty") {
            spawnShip(x, posRandom.nextInt(cols - 1));
        }
        else {
            ship = new Ship(this, x, y);
        }
    }

    //Get the ship
    public Ship getShip() {
        return ship;
    }

/*
 * ---------------------------
 *    Centipede Methods
 * ---------------------------
 */
    //Spawn a centipede
    public Centipede spawnCentipede(int x, int y, int length) {
        Centipede centipede;
        //If length is 0, don't spawn
        if(length <= 0) {
            return null;
        }
        //If no centipedes exist, spawn at random location
        if(centipedes.isEmpty()) {
            y = posRandom.nextInt(cols - 1);
            centipede = new Centipede(this, length, 0, y);
        }
        //If centipedes exist, spawn at given location
        //This is used for when a centipede is split
        else {
            centipede = new Centipede(this, length, x, y);
        }
        //Add centipede to list of centipedes
        centipedes.add(centipede);
        return centipede;
    }

    //Get the list of centipedes
    public ArrayList<Centipede> getCentipedes() {
        return centipedes;
    }

    //Game method to retrieve a centipede by piece's location
    //Used for Bullet collision detection
    public Centipede getCentipede(int x, int y){
        for(Centipede centipede : centipedes) {
            centipedePiece[] centipedeArr = centipede.getArray();
            int len = centipedeArr.length;
            for(int i = 0; i < len; i++) {
                if(centipedeArr[i].getXPos() == x && centipedeArr[i].getYPos() == y) {
                    return centipede;
                }
            }
        }
        return null;
    }

    //Game method to remove a centipede
    public void removeCentipede(Centipede centipede) {
        if(centipedes.contains(centipede)) {
            //Stop the centipede's movement
            centipede.move(false);
            //Remove the centipede from the list
            centipedes.remove(centipede);
            //Remove the centipede from the board
            centipedePiece[] centipedeArr = centipede.getArray();
            for(int i = 0; i < centipedeArr.length; i++) {
                grid[centipedeArr[i].getXPos()][centipedeArr[i].getYPos()].setUserData("Empty");
                this.getChildren().remove(centipedeArr[i]);
            }
        }
    }

/*
* ---------------------------
*        Flea Methods
* ---------------------------
*/
    //Spawn a flea
    public void spawnFlea() {
        int y = posRandom.nextInt(cols - 1);
        fleas.add(new Flea(this, 0, y));
    }

    //Get a flea by location
    public Flea getFlea(int x, int y) {
        for(Flea flea : fleas) {
            if(flea.getXPos() == x && flea.getYPos() == y) {
                return flea;
            }
        }
        return null;
    }

    //Remove a flea
    public void removeFlea(Flea flea) {
        if(fleas.contains(flea)) {
            this.getChildren().remove(flea);
            grid[flea.getXPos()][flea.getYPos()].setUserData("Empty");
            fleas.remove(flea);
        }
    }

/*
 * ---------------------------
 *       Game Methods
 * ---------------------------
 */
    //Getter for current score
    public long getScore() {
        return this.score;
    }
    //Add to the current score
    //Used to add points for destroying objects
    public void addScore(int score) {
        this.score += score;
    }

    //Disable all centipedes
    //Used for when the ship is destroyed
    public void phase(boolean val) {
        for(Centipede centipede : centipedes) {
            centipede.disable(val);
        }
    }

    //Setter and Getter to check and modify bullet collisions
    //Used to speed up bullet
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
        fleas.clear();
        this.getChildren().clear();
    }

    //Removes mushrooms that have been destroyed
    //Cleans errors from collisions
    //Used in Controller
    public void cleanUp() {
        //Remove destroyed mushrooms
        //Prevents visual errors from mushroom destruction
        for(Mushroom shroom : shrooms) {
            int rIndex = this.getRowIndex(shroom);
            int cIndex = this.getColumnIndex(shroom);

            if(grid[rIndex][cIndex].getUserData().toString() == "Empty") {
                this.getChildren().remove(shroom);
            }
        }
        
        //Remove leftover data from failed collision handling
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                Canvas canvas = grid[i][j];
                String data = canvas.getUserData().toString();
                if(data != "Empty") {
                    switch(data) {
                        //If centipede is null, remove data
                        case "Centipede":
                            if(getCentipede(i, j) == null) {
                                canvas.setUserData("Empty");
                            }
                            break;
                        //If ship is not at location, remove data
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
