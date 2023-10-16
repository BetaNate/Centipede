//Nathan J. Rowe
import javafx.scene.shape.Circle;

public class centipedePiece extends Circle implements GameObject{
    public centipedePiece() {
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