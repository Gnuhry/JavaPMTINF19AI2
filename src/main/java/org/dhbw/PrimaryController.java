package org.dhbw;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Button showTable;
    @FXML
    private Button addStudent;
    @FXML
    private Button addLecture;
    @FXML
    private Button addCourse;
    @FXML
    private Button addCompany;
    @FXML
    private Tooltip tooltipStudent;
    @FXML
    private Tooltip tooltipLecture;
    @FXML
    private Tooltip tooltipCourse;
    @FXML
    private Tooltip tooltipCompany;


    /**
     * changing the scene root in App
     */
    @FXML
    private void showStudents() throws IOException {
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

        ImageView showTableImageView = new ImageView(new Image(clazz.getResourceAsStream("/org/dhbw/images/showTable.png")));
        showTableImageView.setFitHeight(30);
        showTableImageView.setFitWidth(30);
        showTable.setGraphic(showTableImageView);

        tooltipStudent.setShowDelay(new Duration(300));
        tooltipLecture.setShowDelay(new Duration(300));
        tooltipCourse.setShowDelay(new Duration(300));
        tooltipCompany.setShowDelay(new Duration(300));
    }
}
