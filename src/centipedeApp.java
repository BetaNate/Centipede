import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;

public class centipedeApp extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    final BorderPane root = new BorderPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Centipede");
        Canvas field = new Canvas(80, 300);
        Controller controller = new Controller(root, field);
        Scene scene = new Scene(root);

        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(100); 
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


    
