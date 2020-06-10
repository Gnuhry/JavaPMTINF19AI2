package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EditCourseController {

    private final ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
    );
    private final ObservableList<CourseRoom> chooseRoomOptions = FXCollections.observableArrayList(
            University.getRooms()
    );

    private Course course_old;
    private final CourseRoom noRoom = new CourseRoom("keiner Raum");
    private final CourseRoom newRoom = new CourseRoom("neuer Raum");
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
    private Label errorMessage;

    /**
     * converting a Date to a LocalDate
     *
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return dateToConvert == null ? null : new java.sql.Date(dateToConvert.getTime()).toLocalDate();
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
        chooseRoomOptions.sort(Comparator.comparing(CourseRoom::getName));
        chooseRoomOptions.add(0, noRoom);
        chooseRoomOptions.add(1, newRoom);
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

        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = courseName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            wrongField(focus, courseName);
            focus = true;
            errorMessageL.add("Name fehlt");
        } else
            courseName.setStyle(styleRight);

        CourseRoom room = courseRoom.getValue();
        if (room == null) {
            wrongField(focus, courseRoom);
            focus = true;
            errorMessageL.add("Kursraum nicht ausgewählt");
        } else if (room.equals(newRoom)) {
            text = insertRoom.getText().trim();
            if (text.isEmpty()) {
                wrongField(focus, insertRoom);
                focus = true;
                errorMessageL.add("Kursraum fehlt");
            } else if (!Check.validateRoom(text)) {
                wrongField(focus, insertRoom);
                focus = true;
                errorMessageL.add("Kursraum ist falsch");
            } else {
                insertRoom.setStyle(styleRight);
                courseRoom.setStyle(styleRight);
            }
        } else
            courseRoom.setStyle(styleRight);

        Docent docent = courseDirector.getValue();
        if (docent == null) {
            wrongField(focus, courseDirector);
            focus = true;
            errorMessageL.add("Kursdirektor nicht ausgewählt");
        } else
            courseDirector.setStyle(styleRight);

        if (focus) {
            StringBuilder sb = new StringBuilder(errorMessageL.remove(0));
            for (String s : errorMessageL)
                sb.append(", ").append(s);
            for (int f = 190; f < sb.length(); f += 190)
                sb.insert(f, "\n");
            errorMessage.setText(sb.toString());
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            room = courseRoom.getValue();
            if (room.equals(newRoom) && !courseRoom.getEditor().getText().isEmpty())
                room = new CourseRoom(insertRoom.getText());
            else if (room.equals(noRoom) || room.equals(newRoom))
                room = null;

            Docent director = courseDirector.getValue();
            if (courseDirector.getValue().equals(noDocent))
                director = null;
            University.updateCourse(new Course(
                    courseName.getText(),
                    course_old.getStudyCourse(),
                    director,
                    course_old.getRegistrationDate(),
                    room
            ), course_old);
            backToOverview();
        }
    }

    /**
     * visualize the wrong fields
     * @param focus has any field requested focus
     * @param control control to mark visualized
     */
    private void wrongField(boolean focus, Control control) {
        final String styleWrong = "-fx-text-fill: darkred; -fx-border-color: darkred";
        control.setStyle(styleWrong);
        if (!focus) {
            control.requestFocus();
        }
    }
}
