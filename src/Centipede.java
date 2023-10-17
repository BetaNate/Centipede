//Nathan J. Rowe
public class Centipede extends centipedePiece {
    private final int size;
    private final centipedePiece[] centipede;
    public Centipede(GamePanel game, int size, int x, int y) {
        super(game, x, y, null);
        this.size = size;
        centipede = new centipedePiece[size];

        for(int i = 0; i < size; i++) {
             centipede[i] = new centipedePiece(game, x, i, getTypeSelector());
        }
        initCentipede();
    }

    private void initCentipede() {
        centipede[0].setType("head");
        for(int i = 1; i < this.size; i++) {
            centipede[i].setType("tail");
        }
    }


}
