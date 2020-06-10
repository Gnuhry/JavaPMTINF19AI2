package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.dhbw.classes.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class InsertCourseController {

    private final ObservableList<Docent> chooseCourseDirectorOptions = FXCollections.observableArrayList(
            University.getDocents()
    );
    private final ObservableList<CourseRoom> chooseRoomOptions = FXCollections.observableArrayList(
            University.getRooms()
    );
    private final ObservableList<StudyCourse> chooseTypeOptions = FXCollections.observableArrayList(
            StudyCourse.values()
    );

    private final CourseRoom noRoom = new CourseRoom("kein Raum");
    private final CourseRoom newRoom = new CourseRoom("neuer Raum");
    private final Docent noDocent = new Docent("kein Dozent", "", null, null, "", 0);
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @FXML
    private Label errorMessage;
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


    /**
     * initializing comboBoxes with object lists
     */
    @FXML
    private void initialize() {
        courseDate.setOnKeyReleased(keyEvent -> {
            String text = courseDate.getEditor().getText();
            if (text.length() < 10 || text.split("\\.").length != 3) return;
            try {
                Date date = format.parse(text);
                courseDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException ignored) {
            }
        });
        courseType.getItems().setAll(chooseTypeOptions);
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
    private void submit() throws IOException {
        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = courseName.getText().trim();
        Date date;
        boolean focus = false;
        if (text.isEmpty()) {
            wrongField(focus, courseName);
            focus = true;
            errorMessageL.add("Name fehlt");
        } else
            courseName.setStyle(styleRight);

        StudyCourse course = courseType.getValue();
        if (course == null) {
            wrongField(focus, courseType);
            focus = true;
            errorMessageL.add("Kursart nicht ausgewählt");
        } else
            courseType.setStyle(styleRight);

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

        date = convertToDateViaSqlDate(courseDate.getValue());
        if (date == null) {
            wrongField(focus, courseDate);
            focus = true;
            errorMessageL.add("Datum fehlt");
        } else
            courseDate.setStyle(styleRight);

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
            University.addCourse(new Course(
                    courseName.getText(),
                    course,
                    director,
                    date,
                    room
            ));
            backToOverview();
        }

        /*try {
            if (courseName.getText().trim().isEmpty() || courseType.getValue() == null || courseDate.getValue() == null || courseDirector.getValue() == null) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();

                CourseRoom room = courseRoom.getValue();
                if (room.equals(newRoom) && !courseRoom.getEditor().getText().isEmpty())
                    room = new CourseRoom(insertRoom.getText());
                else if (room.equals(noRoom) || room.equals(newRoom))
                    room = null;

                Docent director = courseDirector.getValue();
                if (courseDirector.getValue().equals(noDocent))
                    director = null;

                University.addCourse(new Course(
                        courseName.getText(),
                        courseType.getValue(),
                        director,
                        Date.from(Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()))),
                        room
                ));
                backToOverview();
            }
        } catch (NullPointerException | IOException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");
        }*/
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

    /**
     * converting a LocalDate to a Date
     *
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return dateToConvert == null ? null : java.sql.Date.valueOf(dateToConvert);
    }
}
