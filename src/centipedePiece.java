//Nathan J. Rowe
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class centipedePiece extends ImageView implements GameObject{
    private int x, y;
    private String type;
    private String target;
    private String currDirection = "down";
    private final GamePanel game;
    private int score;
    private String input = "right";
    AnimationTimer timer;
    private boolean disabled = false;
    private int currImage = 0;

    Image head1 = new Image("resources/images/head1.png");
    Image head2 = new Image("resources/images/head2.png");
    Image head3 = new Image("resources/images/head3.png");
    Image[] headImages = new Image[] {head1, head2, head3};
    Image body1 = new Image("resources/images/body1.png");
    Image body2 = new Image("resources/images/body2.png");
    Image body3 = new Image("resources/images/body3.png");
    Image[] bodyImages = new Image[] {body1, body2, body3};

    public centipedePiece(GamePanel game, int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.game = game;
        if(type == "head") {
            this.setImage(headImages[0]);
            this.score = 300;
        }
        else {
            this.setImage(bodyImages[0]);
            this.score = 100;
        }
        game.getCanvas()[x][y].setUserData("Centipede");
        game.add(this, y, x);
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public void getMoves(String input) {
        input = this.input;
        this.updateData();
        if (this.y <= 0 && input == "left") {
            this.input = "right";
            advance();
        }
        else if (this.y >= game.getColumnCount() - 1 && input == "right") {
            this.input = "left";
            advance();
        }
        else {
            switch(input) {
                case "left":
                    target = game.getCanvas()[x][y - 1].getUserData().toString();
                    if(target == "Mushroom") {
                        handleCollision(target);
                    }
                    else {                
                        this.y--;
                    }
                    break;
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
                    System.out.println("No Move ?!");
                    break;
            }
        }
        game.setColumnIndex(this, this.y);
        this.updateData();
    }

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
        game.setRowIndex(this, x);
    }

    public void disable(boolean val) {
        if(val == true) {
            this.setEffect(new ColorAdjust(0, 0, -0.5, 0));
        }
        else {
            this.setEffect(null);
        }
        this.disabled = val;
    }

    public int getScore() {
        return this.score;
    }

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

    @Override
    public void move(boolean val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
}