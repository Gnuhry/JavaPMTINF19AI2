package org.dhbw;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.dhbw.classes.Database;
import org.dhbw.classes.Help;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static HostServices hostServices;

    /**
     * opening the app stage with parameters
     *
     * @param stage new stage show new window
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        Database.initialize();
        hostServices = getHostServices();
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(Help.getResourcedBundle().getString("title"));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/dhbwLogoSquare.png")));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> Database.closeConnection());
    }

    /**
     * changing the root-fxml-file in the App stage
     *
     * @param fxml filename to switch to
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * link the new file to the current stage which is the parent
     *
     * @param fxml filename to switch to
     * @return parent object of the new file
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), Help.getResourcedBundle());
        return fxmlLoader.load();
    }

    /**
     * starting the application
     */
    public static void main(String[] args) {
        launch();
    }

    //--------------------------Getter----------------------
    public static HostServices getHostService() {
        return hostServices;
    }
}