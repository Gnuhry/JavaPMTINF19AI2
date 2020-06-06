package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.*;

public class AcceptDeleteController {

    private DualStudent student;
    private Docent lecture;
    private Course course;
    private Company company;
    private String setString;

    @FXML
    private Button cancelButton;

    public void initVariables(Object object, String setString) {
        this.setString = setString;
        switch (setString) {
            case "student":
                this.student = (DualStudent)object;
                break;
            case "lecture":
                this.lecture = (Docent)object;
                break;
            case "course":
                this.course = (Course)object;
                break;
            case "company":
                this.company = (Company)object;
                break;
        }
    }

    @FXML
    private void acceptDelete() {
        switch (setString) {
            case "student":University.removeStudent(student);
                break;
            case "lecture":
                University.removeDocent(lecture);
                break;
            case "course":
                University.removeCourse(course);
                break;
            case "company":
                University.removeCompany(company);
                break;
        }
        cancel();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
