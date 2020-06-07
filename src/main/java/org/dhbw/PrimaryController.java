package org.dhbw;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    Class<?> clazz = this.getClass();
    InputStream addStudentStream = clazz.getResourceAsStream("/org/dhbw/images/addStudentHat.png");
    Image addStudentImage = new Image(addStudentStream);
    ImageView addStudentImageView = new ImageView(addStudentImage);
    InputStream addLectureStream = clazz.getResourceAsStream("/org/dhbw/images/addLecture.png");
    Image addLectureImage = new Image(addLectureStream);
    ImageView addLectureImageView = new ImageView(addLectureImage);
    InputStream addCourseStream = clazz.getResourceAsStream("/org/dhbw/images/addCourse.png");
    Image addCourseImage = new Image(addCourseStream);
    ImageView addCourseImageView = new ImageView(addCourseImage);
    InputStream addCompanyStream = clazz.getResourceAsStream("/org/dhbw/images/addCompany.png");
    Image addCompanyImage = new Image(addCompanyStream);
    ImageView addCompanyImageView = new ImageView(addCompanyImage);
    InputStream showTableStream = clazz.getResourceAsStream("/org/dhbw/images/showTable.png");
    Image showTableImage = new Image(showTableStream);
    ImageView showTableImageView = new ImageView(showTableImage);

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



    @FXML
    private void showStudents() throws  IOException {
        App.setRoot("showStudents");
    }

    @FXML
    private void insertStudent() throws IOException {
        App.setRoot("insertStudent");
    }

    @FXML
    private void insertLecture() throws IOException {
        App.setRoot("insertLecture");
    }

    @FXML
    private void insertCompany() throws IOException {
        App.setRoot("insertCompany");
    }

    @FXML
    private void insertCourse() throws IOException {
        App.setRoot("insertCourse");
    }

    @FXML
    private void editStudent() throws IOException{
        App.setRoot("editStudent");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTableImageView.setFitHeight(30);
        showTableImageView.setFitWidth(30);
        showTable.setGraphic(showTableImageView);
        addStudentImageView.setFitHeight(50);
        addStudentImageView.setFitWidth(50);
        addStudent.setGraphic(addStudentImageView);
        addLectureImageView.setFitWidth(50);
        addLectureImageView.setFitHeight(50);
        addLecture.setGraphic(addLectureImageView);
        addCourseImageView.setFitHeight(50);
        addCourseImageView.setFitWidth(50);
        addCourse.setGraphic(addCourseImageView);
        addCompanyImageView.setFitWidth(50);
        addCompanyImageView.setFitHeight(50);
        addCompany.setGraphic(addCompanyImageView);
        tooltipStudent.setShowDelay(new Duration(300));
        tooltipLecture.setShowDelay(new Duration(300));
        tooltipCourse.setShowDelay(new Duration(300));
        tooltipCompany.setShowDelay(new Duration(300));
    }
}
