package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CourseController {

    private final ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
    );
    private final ObservableList<CourseRoom> chooseRoomOptions = FXCollections.observableArrayList(
            University.getRooms()
    );
    private final ObservableList<StudyCourse> chooseTypeOptions = FXCollections.observableArrayList(
            StudyCourse.values()
    );

    private Course course_old;

    private final CourseRoom noRoom = new CourseRoom("kein Raum");
    private final CourseRoom newRoom = new CourseRoom("neuer Raum");
    private final Docent noDocent = new Docent("kein Dozent", "", null, null, "", 0);

    @FXML
    private Label errorMessage, title;
    @FXML
    private TextField courseName, insertRoom;
    @FXML
    private ComboBox<StudyCourse> courseType;
    @FXML
    private ComboBox<CourseRoom> courseRoom;
    @FXML
    private DatePicker courseDate;
    @FXML
    private ComboBox<Docent> courseDirector;
    @FXML
    private Button buttonDone, buttonCancel;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        if (course_old != null) {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            App.setRoot("primary");
        }
    }

    /**
     * initializing comboBoxes with object lists
     */
    @FXML
    private void initialize() {
        courseType.getItems().setAll(chooseTypeOptions);
        chooseRoomOptions.sort(Comparator.comparing(CourseRoom::getName));
        chooseRoomOptions.add(0, noRoom);
        chooseRoomOptions.add(1, newRoom);
        courseRoom.getItems().setAll(chooseRoomOptions);
        chooseCourseDirectorOptions.add(0, noDocent);
        courseDirector.getItems().setAll(chooseCourseDirectorOptions);
        Help.addKeyEventDatePicker(courseDate);
    }

    /**
     * setting the textfieds if there is a course to edit
     *
     * @param course edit course or null
     */
    public void initVariables(Course course) {
        course_old = course;
        if (course != null) {
            title.setText("Kurs bearbeiten");
            buttonDone.setText("Speichern");
            courseName.setText(course.getName());
            if (course.getStudyCourse() != null) courseType.setValue(course.getStudyCourse());
            if (course.getRoom() != null) {
                courseRoom.setValue(course.getRoom());
                if (course.getRoom().equals(noRoom))
                    insertRoom.setText("");
                else
                    insertRoom.setText(course.getRoom().getName());
            }
            if (course.getRegistrationDate() != null)
                courseDate.setValue(Help.convertLocalDateDate(course.getRegistrationDate()));
            if (course.getStudyDirector() != null) courseDirector.setValue(course.getStudyDirector());
            courseDate.setDisable(true);
            courseType.setDisable(true);
        }
    }

    /**
     * visualizing information about the chosen room
     */
    @FXML
    private void showRooms() {
        CourseRoom room = courseRoom.getValue();
        if (room.equals(noRoom)) {
            insertRoom.setText("");
            insertRoom.setEditable(false);
        } else if (room.equals(newRoom)) {
            insertRoom.setText("");
            insertRoom.setEditable(true);
        } else {
            insertRoom.setEditable(false);
            insertRoom.setText(room.getName());
        }
    }

    /**
     * change choose room to new room, if typing own text
     */
    @FXML
    private void editRoomText() {
        courseRoom.setValue(newRoom);
    }


    /**
     * reading the textfields
     * generating a new course with the entered information and adding the new course to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = courseName.getText().trim();
        Date date;
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, courseName);
            focus = true;
            errorMessageL.add("Name fehlt");
        } else
            courseName.setStyle(Help.styleRight);

        StudyCourse course = courseType.getValue();
        if (course == null) {
            Help.markWrongField(focus, courseType);
            focus = true;
            errorMessageL.add("Kursart nicht ausgewählt");
        } else
            courseType.setStyle(Help.styleRight);

        CourseRoom room = courseRoom.getValue();
        if (room == null) {
            Help.markWrongField(focus, courseRoom);
            focus = true;
            errorMessageL.add("Kursraum nicht ausgewählt");
        } else if (room.equals(newRoom)) {
            text = insertRoom.getText().trim();
            if (text.isEmpty()) {
                Help.markWrongField(focus, insertRoom);
                focus = true;
                errorMessageL.add("Kursraum fehlt");
            } else if (!Help.validateRoom(text)) {
                Help.markWrongField(focus, insertRoom);
                focus = true;
                errorMessageL.add("Kursraum ist falsch");
            } else {
                insertRoom.setStyle(Help.styleRight);
                courseRoom.setStyle(Help.styleRight);
            }
        } else
            courseRoom.setStyle(Help.styleRight);

        date = Help.convertLocalDateDate(courseDate.getValue());
        if (date == null) {
            Help.markWrongField(focus, courseDate);
            focus = true;
            errorMessageL.add("Datum fehlt");
        } else
            courseDate.setStyle(Help.styleRight);

        Docent docent = courseDirector.getValue();
        if (docent == null) {
            Help.markWrongField(focus, courseDirector);
            focus = true;
            errorMessageL.add("Kursdirektor nicht ausgewählt");
        } else
            courseDirector.setStyle(Help.styleRight);

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            room = courseRoom.getValue();
            if (room.equals(newRoom) && !courseRoom.getEditor().getText().isEmpty())
                room = new CourseRoom(insertRoom.getText());
            else if (room.equals(noRoom) || room.equals(newRoom))
                room = null;

            Docent director = courseDirector.getValue();
            if (courseDirector.getValue().equals(noDocent))
                director = null;
            Course new_course = new Course(
                    courseName.getText(),
                    course,
                    director,
                    date,
                    room
            );
            if (course_old != null)
                University.updateCourse(new_course, course_old);
            else
                University.addCourse(new_course);

            backToOverview();
        }
    }
}
