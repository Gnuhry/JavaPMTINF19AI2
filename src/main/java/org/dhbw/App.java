package org.dhbw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.dhbw.classes.Database;
import org.dhbw.classes.University;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * opening the app stage with parameters
     * @param stage new stage show new window
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("DHBW Datenverwaltung");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/DHBW_Logo_quadrat.png")));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> Database.closeConnection());
    }

    /**
     * changing the root-fxml-file in the App stage
     * @param fxml filename to switch to
     */
    static void setRoot(String fxml) throws IOException {scene.setRoot(loadFXML(fxml));}

    /**
     * link the new file to the current stage which is the parent
     * @param fxml filename to switch to
     * @return parent object of the new file
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * starting the application
     */
    public static void main(String[] args) {
        launch();
    }

}