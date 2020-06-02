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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class InsertCourseController {

    ObservableList<Person> chooseCourseRepresentOptions = FXCollections.observableArrayList(
            new Person("Silas", "Wessely", "")
    );

    ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            new Docent("Stroetmann", "Karl", new Date(70, Calendar.JANUARY, 1), new Address("Test", "1", "12345", "Test", "Test"))
    );

    @FXML
    private TextField courseName;
    @FXML
    private ComboBox<StudyCourse> courseType;
    @FXML
    private ComboBox<CourseRoom> courseRoom;
    @FXML
    private DatePicker courseDate;
    @FXML
    private ComboBox<Person> courseRepresent;
    @FXML
    private ComboBox<Docent> courseDirector;
    @FXML
    private DialogPane showNullPointer;


    @FXML
    private void initialize() {
        courseType.getItems().setAll(FXCollections.observableArrayList(StudyCourse.values()));
        courseRoom.getItems().setAll(FXCollections.observableArrayList(CourseRoom.values()));
        courseRepresent.getItems().setAll(FXCollections.observableArrayList(chooseCourseRepresentOptions));
        courseDirector.getItems().setAll(FXCollections.observableArrayList(chooseCourseDirectorOptions));
    }


    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void submit() {

        try {
            if (courseName.getText().trim().isEmpty() || courseType.getEditor().getText().trim().isEmpty() || courseRoom.getEditor().getText().trim().isEmpty() || courseDate.getValue() == null || courseRepresent.getEditor().getText().trim().isEmpty() || courseDirector.getEditor().getText().trim().isEmpty()) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");    // LOG Datei?
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();
                Instant instantCourseBirth = Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()));
                Date courseRDate = Date.from(instantCourseBirth);

                Course course = new Course(
                        courseName.getText(),
                        (StudyCourse)courseType.getValue(),
                        new Docent("Dozentname", "Dozentvorname", new Date(2000, 05,27), new Address("s", "2", "2", "s", "s")),//courseDirector.getAccessibleText(),             Combobox -> Dozent
                        10,//courseRepresent.getAccessibleText(),            Combodbox -> KurssprecherID (int)
                        courseRDate,
                        (CourseRoom)courseRoom.getValue()
                );

                System.out.println(course);
                University.addCourse(course);

            }
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");    // LOG Datei?
        }


    }

}
