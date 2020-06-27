package org.dhbw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.Database;
import org.dhbw.classes.*;
import org.dhbw.help.DateConverter;
import org.dhbw.help.GuiHelp;
import org.dhbw.help.Language;

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

    private final CourseRoom noRoom = new CourseRoom(Language.getResourcedBundle().getString("no_room"), null, null, null);
    private final Docent noDocent = new Docent(Language.getResourcedBundle().getString("no_docent"), "", null, null, "", 0);

    @FXML
    private Label errorMessage, title;
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
    private Button buttonDone, buttonCancel;

    /**
     * changing the scene root in App to "primary.fxml" or closing pop up window
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
     * initializing comboBoxes and datepicker
     */
    @FXML
    private void initialize() {
        courseType.getItems().setAll(chooseTypeOptions);
        chooseRoomOptions.sort(Comparator.comparing(CourseRoom::getName));
        chooseRoomOptions.add(0, noRoom);
        courseRoom.getItems().setAll(chooseRoomOptions);
        chooseCourseDirectorOptions.add(0, noDocent);
        courseDirector.getItems().setAll(chooseCourseDirectorOptions);
        GuiHelp.addKeyEventDatePicker(courseDate);
    }

    /**
     * filling the textfield with course attributes if course is not null
     *
     * @param course course to fill in or null
     */
    public void initVariables(Course course) {
        course_old = course;
        if (course != null) {
            title.setText(Language.getResourcedBundle().getString("title_course_edit"));
            buttonDone.setText(Language.getResourcedBundle().getString("save"));
            courseName.setText(course.getName());
            if (course.getStudyCourse() != null) courseType.setValue(course.getStudyCourse());
            if (course.getRoom() != null && course.getRoom().getName() != null) courseRoom.setValue(course.getRoom());
            if (course.getRegistrationDate() != null)
                courseDate.setValue(DateConverter.convertLocalDateDate(course.getRegistrationDate()));
            if (course.getStudyDirector() != null) courseDirector.setValue(course.getStudyDirector());
            courseDate.setDisable(true);
            courseType.setDisable(true);
        }
    }


    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the course to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = courseName.getText().trim();
        Date date;
        boolean focus = false;
        if (text.isEmpty()) {
            GuiHelp.markWrongField(false, courseName);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("name"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, courseName);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            courseName.setStyle(GuiHelp.styleRight);

        StudyCourse course = courseType.getValue();
        if (course == null) {
            GuiHelp.markWrongField(focus, courseType);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("course_type"));
        } else
            courseType.setStyle(GuiHelp.styleRight);

        CourseRoom room = courseRoom.getValue();
        if (room == null) {
            GuiHelp.markWrongField(focus, courseRoom);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("room"));
        } else
            courseRoom.setStyle(GuiHelp.styleRight);

        date = DateConverter.convertLocalDateDate(courseDate.getValue());
        if (date == null) {
            GuiHelp.markWrongField(focus, courseDate);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("day"));
        } else
            courseDate.setStyle(GuiHelp.styleRight);

        Docent docent = courseDirector.getValue();
        if (docent == null) {
            GuiHelp.markWrongField(focus, courseDirector);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("director"));
        } else
            courseDirector.setStyle(GuiHelp.styleRight);

        if (focus)
            GuiHelp.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            room = courseRoom.getValue();
            if (room.equals(noRoom))
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
