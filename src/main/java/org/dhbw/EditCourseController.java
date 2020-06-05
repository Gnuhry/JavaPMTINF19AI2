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

    ObservableList<Person> chooseCourseRepresentOptions = FXCollections.observableArrayList(
            University.getStudents()
            //new Person("Silas", "Wessely", "")
    );

    ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
            //new Docent("Stroetmann", "Karl", new Date(70, Calendar.JANUARY, 1), new Address("Test", "1", "12345", "Test", "Test"))
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

    public void initVariables(Course course) {
        this.course_old = course;
        if (!course.getName().isEmpty()) courseName.setText(course.getName());
        if (course.getStudyCourse() != null) courseType.setValue(course.getStudyCourse());
        if (course.getRoom()!= null) courseRoom.setValue(course.getRoom());
        if (course.getRegistrationDate() != null) courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        if (course.getStudyDirector() != null) courseDirector.setValue(course.getStudyDirector());
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    private void initialize() {
        courseType.getItems().setAll(FXCollections.observableArrayList(StudyCourse.values()));
        courseRoom.getItems().setAll(FXCollections.observableArrayList(CourseRoom.values()));
        courseDirector.getItems().setAll(FXCollections.observableArrayList(chooseCourseDirectorOptions));
    }


    @FXML
    private void backToOverview() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

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

            }
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");    // LOG Datei?
        }


    }

}