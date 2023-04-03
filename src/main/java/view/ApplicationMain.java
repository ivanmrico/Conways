package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
public class ApplicationMain extends Application;
The application loader class. Uses FXMLLoader to load the ConwayGUI.fxml file and load the scene from
the FXML file. Also sets stage properties. Contains the main method and launches the application.
 */
public class ApplicationMain extends Application {

    /*
    public void start(Stage stage) throws IOException;
    This method is built in to JavaFX and chooses the scene to load when the application is launched.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("ConwayGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Conway's Game of Life");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
   }

   /*
   public static void main(String[] args);
   The main method runs when the application starts and will launch the scene.
    */
    public static void main(String[] args) {
        launch();
    }
}