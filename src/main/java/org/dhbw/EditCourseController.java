package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditCourseController {

    ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
    );

    private Course course_old;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField courseName;
    @FXML
    private ComboBox<StudyCourse> courseType;
    @FXML
    private ComboBox<CourseRoom> courseRoom;
    @FXML
    private DatePicker courseDate;
    @FXML
    private ComboBox<Docent> courseDirector;
    @FXML
    private DialogPane showNullPointer;

    /**
     * converting a Date to a LocalDate
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) { return new java.sql.Date(dateToConvert.getTime()).toLocalDate(); }

    /**
     * visualizing all information about the course in the textfields
     * @param course student which gets changed
     */
    public void initVariables(Course course) {
        this.course_old = course;
        if (!course.getName().isEmpty()) courseName.setText(course.getName());
        if (course.getStudyCourse() != null) courseType.setValue(course.getStudyCourse());
        if (course.getRoom()!= null) courseRoom.setValue(course.getRoom());
        if (course.getRegistrationDate() != null) courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        if (course.getStudyDirector() != null) courseDirector.setValue(course.getStudyDirector());
    }

    /**
     * initializing comboBoxes with object list
     */
    @FXML
    private void initialize() {
        courseType.getItems().setAll(FXCollections.observableArrayList(StudyCourse.values()));
        courseRoom.getItems().setAll(FXCollections.observableArrayList(University.getRooms()));
        courseDirector.getItems().setAll(FXCollections.observableArrayList(chooseCourseDirectorOptions));
    }

    /**
     * changing the scene root in App to "primary.fxml" and close stage
     */
    @FXML
    private void backToOverview() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * reading the textfields
     * generating a new course with the entered information and adding the new course to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() {

        try {
            if (courseName.getText().trim().isEmpty() || courseType.getValue() == null || courseRoom.getValue() == null || courseDate.getValue() == null || courseDirector.getValue() == null) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");    // LOG Datei?
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();
                Instant instantCourseBirth = Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()));
                Date courseRDate = Date.from(instantCourseBirth);

                Course course = new Course(
                        courseName.getText(),
                        courseType.getValue(),
                        courseDirector.getValue(),
                        courseRDate,
                        courseRoom.getValue()
                );
                University.updateCourse(course, course_old);
                backToOverview();
            }
        } catch (NullPointerException | IOException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");    // LOG Datei?
        }


    }

}
