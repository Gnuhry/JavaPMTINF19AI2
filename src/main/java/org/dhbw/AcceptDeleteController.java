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

    /**
     * initializing student, lecture, course or company with object value; the choose which object gets initialized depends on the type of the over given object
     */
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

    /**
     * deleting the object from the database; the choose which object gets initialized depends on the type of the over given object
     */
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

    /**
     * closing the stage / window
     */
    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
