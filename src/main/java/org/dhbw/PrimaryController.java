package org.dhbw;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.dhbw.classes.Help;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    public Button buttonLanguage, showTable, addStudent, addLecture, addCourse, addCompany, addRoom;
    @FXML
    private Tooltip tooltipStudent, tooltipLecture, tooltipCourse, tooltipCompany, tooltipRoom;


    /**
     * changing the scene root in App
     */
    @FXML
    private void show() throws IOException {
        buttonLanguage.getScene().setCursor(Cursor.WAIT);
        App.setRoot("show");
    }

    @FXML
    private void insertStudent() throws IOException {
        App.setRoot("student");
    }

    @FXML
    private void insertLecture() throws IOException {
        App.setRoot("docent");
    }

    @FXML
    private void insertCompany() throws IOException {
        App.setRoot("company");
    }

    @FXML
    private void insertCourse() throws IOException {
        App.setRoot("course");
    }

    @FXML
    private void insertRoom() throws IOException {
        App.setRoot("room");
    }

    @FXML
    private void toggleLanguage() throws IOException {
        Help.toggleLocal();
        App.setRoot("primary");
    }

    /**
     * initializing each menu-button with an image and tooltip
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Class<?> clazz = this.getClass();
        ImageView addStudentImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/addStudentHat.png")));
        addStudentImageView.setFitHeight(50);
        addStudentImageView.setFitWidth(50);
        addStudent.setGraphic(addStudentImageView);

        ImageView addLectureImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/addDocent.png")));
        addLectureImageView.setFitWidth(50);
        addLectureImageView.setFitHeight(50);
        addLecture.setGraphic(addLectureImageView);

        ImageView addCourseImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/addCourse.png")));
        addCourseImageView.setFitHeight(50);
        addCourseImageView.setFitWidth(50);
        addCourse.setGraphic(addCourseImageView);

        ImageView addCompanyImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/addCompany.png")));
        addCompanyImageView.setFitWidth(50);
        addCompanyImageView.setFitHeight(50);
        addCompany.setGraphic(addCompanyImageView);

        ImageView addRoomImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/addRoom.png")));
        addRoomImageView.setFitWidth(50);
        addRoomImageView.setFitHeight(50);
        addRoom.setGraphic(addRoomImageView);

        ImageView showTableImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/showTable.png")));
        showTableImageView.setFitHeight(30);
        showTableImageView.setFitWidth(30);
        showTable.setGraphic(showTableImageView);

        tooltipStudent.setShowDelay(new Duration(300));
        tooltipLecture.setShowDelay(new Duration(300));
        tooltipCourse.setShowDelay(new Duration(300));
        tooltipCompany.setShowDelay(new Duration(300));
        tooltipRoom.setShowDelay(new Duration(300));
        buttonLanguage.setText(Help.getLocale().getLanguage());

        showTable.requestFocus();
    }
}
