package org.dhbw;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.w3c.dom.Element;

public class PrimaryController {

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

}
