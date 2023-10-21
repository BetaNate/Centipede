/*
 * Author: Nathan J. Rowe
 * CentipedePiece Class
 * CentipedePiece is a piece of the centipede
 * Used in Centipede.java
 */

 //For Image
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class centipedePiece extends ImageView implements GameObject{
    //CentipedePiece position
    private int x, y;
    //CentipedePiece type
    private String type;
    //Target for collision
    private String target;
    //CentipedePiece direction, up or down
    //Only used for head
    private String currDirection = "down";
    //CentipedePiece input, left or right
    //Only used for head
    private String input = "right";
    //GamePanel reference
    private final GamePanel game;
    //CentipedePiece score
    private int score;
    //CentipedePiece disabled value
    //Used for when centipedePiece hits user
    private boolean disabled = false;
    //Current image for animation
    private int currImage = 0;

    //Images for head
    Image head1 = new Image("resources/images/head1.png");
    Image head2 = new Image("resources/images/head2.png");
    Image head3 = new Image("resources/images/head3.png");
    Image[] headImages = new Image[] {head1, head2, head3};
    //Images for body (tail pieces)
    Image body1 = new Image("resources/images/body1.png");
    Image body2 = new Image("resources/images/body2.png");
    Image body3 = new Image("resources/images/body3.png");
    Image[] bodyImages = new Image[] {body1, body2, body3};

/*
* ------------------------
*       Constructor
* ------------------------
* Input: Current Game, x position, y position, type
*/
    public centipedePiece(GamePanel game, int x, int y, String type) {
        //Set properties
        this.x = x;
        this.y = y;
        this.type = type;
        this.game = game;
        //Set Image Set
        if(type == "head") {
            this.setImage(headImages[0]);
            this.score = 300;
        }
        else {
            this.setImage(bodyImages[0]);
            this.score = 100;
        }
        //Add to game
        game.getCanvas()[x][y].setUserData("Centipede");
        game.add(this, y, x);
    }

/*
 * ------------------------
 *    Collision Handling
 * ------------------------
 */

    //Method for handling collision
    //Input: Target of collision
    public void handleCollision(String target) {
        if(target == "Mushroom") {
            if(this.input == "right") {
                this.input = "left";
            }
            else if(this.input == "left") {
                this.input = "right";
            }
            advance();
        }
        else {
            System.out.println("Invalid Collision");
        }
    }

/*
 * ------------------------
 *        MOVEMENT
 * ------------------------
 */
    //Method for getting moves
    public void getMoves(String input) {
        //Get current direction
        input = this.input;
        this.updateData();
        //Check if at edge of screen
        if (this.y <= 0 && input == "left") {
            this.input = "right";
            advance();
        }
        else if (this.y >= game.getColumnCount() - 1 && input == "right") {
            this.input = "left";
            advance();
        }
        else {
            //Handle normal movement
            switch(input) {
                //Move left
                case "left":
                    target = game.getCanvas()[x][y - 1].getUserData().toString();
                    if(target == "Mushroom") {
                        handleCollision(target);
                    }
                    else {                
                        this.y--;
                    }
                    break;
                //Move right
                case "right":
                    target = game.getCanvas()[x][y + 1].getUserData().toString();
                    if(target == "Mushroom") {
                        handleCollision(target);
                    }
                    else {                
                        this.y++;
                    }
                    break;
                default:
                    break;
            }
        }
        //Update position
        game.setColumnIndex(this, this.y);
        this.updateData();
    }

    //Method for advancing centipedePiece
    //Moves up or down depending on current direction
    public void advance() {
        if(currDirection == "up") {
            if (x <= 0) {
                currDirection = "down";
                this.x++;
            }
            else {
                this.x--;
            }
        }
        else if(currDirection == "down")  {
            if (x >= game.getRowCount() - 1) {
                currDirection = "up";
                this.x--;
            }
            else {
                this.x++;
            }
        }
        //Update position
        game.setRowIndex(this, x);
    }

    //Method for updating data
    //Used to handle image changes and collisions
    public void updateData() {
        String data = game.getCanvas()[x][y].getUserData().toString();
        if(currImage >= 2) {
            currImage = 0;
        }
        else {
            currImage++;
        }
        if(type == "head") {
            this.setImage(headImages[currImage]);
        }
        else {
            this.setImage(bodyImages[currImage]);
        }
        switch(input) {
            case "left":
                this.setRotate(0);
                break;
            case "right":
                this.setRotate(180);
                break;
        }
        if(data != "Mushroom") {
            if(disabled == true) {
                game.getCanvas()[x][y].setUserData("Empty");
            }
            else if(data == "Ship") {
                game.getCanvas()[x][y].setUserData("Empty");
                game.getShip().handleCollision("Centipede");
            }
            else if (data == "Empty") {
            game.getCanvas()[x][y].setUserData("Centipede");
            }
            else {
            game.getCanvas()[x][y].setUserData("Empty");
            }
        }
    }


/*
 * ------------------------
 *   GETTERS AND SETTERS
 * ------------------------
 */
    //Change color if disabled 
    public void disable(boolean val) {
        if(val == true) {
            this.setEffect(new ColorAdjust(0, 0, -0.5, 0));
        }
        else {
            this.setEffect(null);
        }
        //Set disabled value
        this.disabled = val;
    }

    public int getScore() {
        return this.score;
    }

    public String getInput() {
        return this.input;
    }
    public void setInput(String input) {
        this.input = input;
    }
    
    public int getYPos() {
        return this.y;
    }

    public int getXPos() {
        return this.x;
    }
    public void setYPos(int y) {
        this.y = y;
    }
    public void setXPos(int x) {
        this.x = x;
    }

    /*
     * ------------------------
     *      UNUSED METHODS
     * ------------------------
     */
    public void move(boolean val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
}