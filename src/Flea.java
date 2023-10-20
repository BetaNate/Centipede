//Nathan J. Rowe

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Flea extends ImageView implements GameObject{
    public Flea() {
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
