package org.dhbw.controller;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.dhbw.Database;
import org.dhbw.help.GuiHelp;
import org.dhbw.help.Language;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static HostServices hostServices;
    private static Class<?> class_;
    public static App app;

    /**
     * opening the app stage with parameters
     *
     * @param stage new stage show new window
     */
    @Override
    public void start(Stage stage) throws IOException {
        app=this;
        class_ =this.getClass();
        Database.initialize();
        hostServices = getHostServices();
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(Language.getResourcedBundle().getString("title"));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(GuiHelp.logoPath)));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> Database.closeConnection());
    }

    /**
     * changing the root-fxml-file in the App stage
     *
     * @param fxml filename to switch to
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * link the new file to the current stage which is the parent
     *
     * @param fxml filename to switch to
     * @return parent object of the new file
     */
    private static Parent loadFXML(String fxml) throws IOException {
        return (new FXMLLoader(App.class.getResource(fxml + ".fxml"), Language.getResourcedBundle())).load();
    }

    /**
     * closing Application
     */
    public static void closeApp(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        App.launch();
    }
    //--------------------------Getter----------------------
    public static HostServices getHostService() {
        return hostServices;
    }

    public static Class<?> getClass_() {
        return class_;
    }
}