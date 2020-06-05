package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.Docent;
import org.dhbw.classes.DualStudent;
import org.dhbw.classes.University;

public class AcceptDeleteLectureController {

    private Docent lecture;

    @FXML
    private Button cancelButton;

    public void initVariables(Docent lecture) {
        this.lecture = lecture;
    }

    @FXML
    private void acceptDelete() {
        University.removeDocent(lecture);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
