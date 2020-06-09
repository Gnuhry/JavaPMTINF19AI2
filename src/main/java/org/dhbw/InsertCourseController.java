package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import org.dhbw.classes.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertCourseController {

    ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
            //new Docent("Stroetmann", "Karl", new Date(70, Calendar.JANUARY, 1), new Address("Test", "1", "12345", "Test", "Test"))
    );

    @FXML
    private TextField courseName;
    @FXML
    private ComboBox<StudyCourse> courseType;
    @FXML
    private ComboBox<CourseRoom> courseRoom;
    @FXML
    private TextField insertRoom;
    @FXML
    private DatePicker courseDate;
    @FXML
    private ComboBox<Docent> courseDirector;
    @FXML
    private DialogPane showNullPointer;


    /**
     * initializing comboBoxes with object lists
     */
    @FXML
    private void initialize() {
        courseType.getItems().setAll(FXCollections.observableArrayList(StudyCourse.values()));
        courseRoom.getItems().setAll(FXCollections.observableArrayList(University.getRooms()));
        courseDirector.getItems().setAll(FXCollections.observableArrayList(chooseCourseDirectorOptions));
    }

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }


    /**
     * reading the textfields
     * generating a new course with the entered information and adding the new course to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() {

        try {
            if (courseName.getText().trim().isEmpty() || courseType.getValue() == null || courseDate.getValue() == null || courseDirector.getValue() == null) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");    // LOG Datei?
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();
                Instant instantCourseBirth = Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()));
                Date courseRDate = Date.from(instantCourseBirth);

                CourseRoom room;
                if (courseRoom.getValue() != null || !courseRoom.getEditor().getText().isEmpty()) room = courseRoom.getValue();
                else room = new CourseRoom(insertRoom.getText());

                Course course = new Course(
                        courseName.getText(),
                        courseType.getValue(),
                        courseDirector.getValue(),
                        courseRDate,
                        room
                );
                University.addCourse(course);
                backToOverview();
            }
        } catch (NullPointerException | IOException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");    // LOG Datei?
        }


    }

}
