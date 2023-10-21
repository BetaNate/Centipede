/*
 * Author: Nathan J. Rowe
 * Main Class
 * Launches the application
 */

//For JavaFX
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;

public class centipedeApp extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    //Root pane
    private final BorderPane root = new BorderPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set up the stage
        primaryStage.setTitle("Centipede");
        root.getStyleClass().add("background-black");
        new Controller(root);
        //Set up the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/css/style.css");
        //Add the scene to the stage
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(100); 
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


    
