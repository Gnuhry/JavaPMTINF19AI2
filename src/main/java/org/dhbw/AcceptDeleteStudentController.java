package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.DualStudent;
import org.dhbw.classes.University;

public class AcceptDeleteStudentController {

    private DualStudent student;

    @FXML
    private Button cancelButton;

    public void initVariables(DualStudent student) {
        this.student = student;
    }

    @FXML
    private void acceptDelete() {
        University.removeStudent(student);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
