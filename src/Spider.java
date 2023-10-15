import javafx.scene.shape.Circle;

public class Spider extends Circle implements GameObject{
    public Spider() {
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
