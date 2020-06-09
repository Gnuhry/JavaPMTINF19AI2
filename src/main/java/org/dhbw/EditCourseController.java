package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditCourseController {

    private final ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
    );
    private final ObservableList<CourseRoom> chooseRoomOptions = FXCollections.observableArrayList(
            University.getRooms()
    );

    private Course course_old;
    private final CourseRoom noRoom = new CourseRoom("kein Raum");
    private final Docent noDocent = new Docent("kein Dozent", "", null, null, "", 0);


    @FXML
    private Button cancelButton;
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
     * converting a Date to a LocalDate
     *
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    /**
     * visualizing all information about the course in the textfields
     *
     * @param course student which gets changed
     */
    public void initVariables(Course course) {
        this.course_old = course;
        if (!course.getName().isEmpty()) courseName.setText(course.getName());
        if (course.getStudyCourse() != null) courseType.setValue(course.getStudyCourse());
        if (course.getRoom() != null) {
            courseRoom.setValue(course.getRoom());
            if (course.getRoom().equals(noRoom))
                insertRoom.setText("");
            else
                insertRoom.setText(course.getRoom().getName());
        }
        if (course.getRegistrationDate() != null)
            courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        if (course.getStudyDirector() != null) courseDirector.setValue(course.getStudyDirector());
    }

    /**
     * initializing comboBoxes with object list
     */
    @FXML
    private void initialize() {
        chooseRoomOptions.add(0, noRoom);
        courseRoom.getItems().setAll(chooseRoomOptions);
        chooseCourseDirectorOptions.add(0, noDocent);
        courseDirector.getItems().setAll(chooseCourseDirectorOptions);
    }

    /**
     * visualizing information about the chosen room
     */
    @FXML
    private void showRooms() {
        CourseRoom room = courseRoom.getValue();
        if (room.equals(noRoom))
            insertRoom.setText("");
        else
            insertRoom.setText(room.getName());
    }

    /**
     * change choose room to new room, if typing own text
     */
    @FXML
    private void editRoomText() {
        String text = insertRoom.getText();
        if (!courseRoom.getValue().getName().equals(text))
            courseRoom.setValue(noRoom);
    }

    /**
     * changing the scene root in App to "primary.fxml" and close stage
     */
    @FXML
    private void backToOverview() {
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
                System.out.println("NPE2 found");
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();

                CourseRoom room;
                if (courseRoom.getValue().equals(noRoom)) room = null;
                else //if (courseRoom.getValue() != null || !courseRoom.getEditor().getText().isEmpty())
                    room = courseRoom.getValue();
//                else room = new CourseRoom(insertRoom.getText());

                Docent director = courseDirector.getValue();
                if (courseDirector.getValue().equals(noDocent))
                    director = null;

                University.updateCourse(new Course(
                        courseName.getText(),
                        courseType.getValue(),
                        director,
                        Date.from(Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()))),
                        room
                ), course_old);
                backToOverview();
            }
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");
        }


    }

}
