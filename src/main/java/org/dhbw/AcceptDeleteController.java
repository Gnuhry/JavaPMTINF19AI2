package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.dhbw.classes.*;

public class AcceptDeleteController {

    private Object object;
    private DualStudent student;
    private Docent lecture;
    private Course course;
    private Company company;

    @FXML
    private Button cancelButton;

    public void initVariables(Object object) {
        if (object instanceof DualStudent) {
            this.student = (DualStudent) object;
        } else if (object instanceof Docent) {
            this.lecture = (Docent) object;
        } else if (object instanceof Course) {
            this.course = (Course) object;
        } else if (object instanceof Company) {
            this.company = (Company) object;
        }
        this.object = object;
    }

    @FXML
    private void acceptDelete() {
        if (object instanceof DualStudent) {
            University.removeStudent(student);
        } else if (object instanceof Docent) {
            University.removeDocent(lecture);
        } else if (object instanceof Course) {
            University.removeCourse(course);
        } else if (object instanceof Company) {
            University.removeCompany(company);
        }
        cancel();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
