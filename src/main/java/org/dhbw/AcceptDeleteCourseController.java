package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.Course;
import org.dhbw.classes.DualStudent;
import org.dhbw.classes.University;

public class AcceptDeleteCourseController {

    private Course course;

    @FXML
    private Button cancelButton;

    public void initVariables(Course course) {
        this.course = course;
    }

    @FXML
    private void acceptDelete() {
        University.removeCourse(course);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
