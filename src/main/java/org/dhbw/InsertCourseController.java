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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
    private final Docent noDocent = new Docent("kein Dozent", "", null, null, "", 0);
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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
        courseDate.setOnKeyReleased(keyEvent -> {
            String text=courseDate.getEditor().getText();
            if(text.length()<10||text.split("\\.").length!=3) return;
            try {
                Date date = format.parse(text);
                courseDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException ignored) {
                System.out.println("Fehler");
            }
        });
        courseType.getItems().setAll(chooseTypeOptions);
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
                System.out.println("NPE2 found");
            } else {
                LocalDate localDateCourseBirth = courseDate.getValue();

                CourseRoom room;
                if (courseRoom.getValue().equals(noRoom)) room = null;
                else if (courseRoom.getValue() != null || !courseRoom.getEditor().getText().isEmpty())
                    room = courseRoom.getValue();
                else room = new CourseRoom(insertRoom.getText());

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
        }
    }
}
