import javafx.scene.shape.Circle;

public class centipedePart extends Circle implements GameObject{
    public centipedePart() {
    }
    public boolean checkCollision() {
        return false;
    }
    public void handleCollision(Object target) {
        return;
    }
    public void getMoves(GamePanel game, String input) {
        return;
    }
}
