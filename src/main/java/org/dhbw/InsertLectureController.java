package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InsertLectureController {

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextField lectureNumberField;

    @FXML
    private void generateLN() throws IOException {
        String lectureNumber = "d" + (100000+(int)(Math.random()*999999));
        lectureNumberField.setText(lectureNumber);
    }

}
