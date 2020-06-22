package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.Campus;
import org.dhbw.classes.CourseRoom;
import org.dhbw.classes.Help;
import org.dhbw.classes.University;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomController {

    private CourseRoom room_old;

    private final ObservableList<Campus> chooseTypeOptions = FXCollections.observableArrayList(
            Campus.values()
    );

    @FXML
    private Label errorMessage, title;
    @FXML
    private TextField roomName, roomBuilding, roomFloor, roomSeats;
    @FXML
    private CheckBox roomProjector, roomDocumentCamera, roomLaboratory;
    @FXML
    private ComboBox<Campus> roomCampus;
    @FXML
    private Button buttonDone, buttonCancel;

    /**
     * changing the scene root in App to "primary.fxml" or closing pop up window
     */
    @FXML
    private void backToOverview() throws IOException {
        if (room_old != null) {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            App.setRoot("primary");
        }
    }

    /**
     * filling the textfield with company attributes if room is not null
     *
     * @param room room to fill in or null
     */
    public void initVariables(CourseRoom room) {
        room_old = room;
        if (room != null) {
            title.setText(Help.getRessourceBundle().getString("title_room_edit"));
            buttonDone.setText(Help.getRessourceBundle().getString("save"));
            roomName.setText(room.getName());
        }
    }

    /**
     * initializing comboBoxes
     */
    @FXML
    private void initialize() {
        roomCampus.getItems().setAll(chooseTypeOptions);
    }

    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the room to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = roomName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, roomName);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("name"));
        } else
            roomName.setStyle(Help.styleRight);
        Campus campus = roomCampus.getValue();
        if (campus == null) {
            Help.markWrongField(focus, roomCampus);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("campus"));
        } else
            roomCampus.setStyle(Help.styleRight);

        text = roomBuilding.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, roomBuilding);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("building"));
        } else
            roomBuilding.setStyle(Help.styleRight);

        text = roomFloor.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, roomFloor);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("floor"));
        } else
            roomFloor.setStyle(Help.styleRight);

        text = roomSeats.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, roomSeats);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("seats"));
        } else {
            try {
                int x = Integer.parseInt(text);
                if (x <= 0) {
                    Help.markWrongField(focus, roomSeats);
                    focus = true;
                    errorMessageL.add(Help.getRessourceBundleError().getString("seats3"));
                }
                roomSeats.setStyle(Help.styleRight);
            } catch (NumberFormatException ex) {
                Help.markWrongField(focus, roomSeats);
                focus = true;
                errorMessageL.add(Help.getRessourceBundleError().getString("seats2"));
            }
        }

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            if (room_old != null)
                University.updateRoom(new CourseRoom(roomName.getText(), roomCampus.getValue(), roomBuilding.getText(), roomFloor.getText(), Integer.parseInt(roomSeats.getText()), roomProjector.isSelected(), roomDocumentCamera.isSelected(), roomLaboratory.isSelected()), room_old);
            else
                University.addRoom(new CourseRoom(roomName.getText(), roomCampus.getValue(), roomBuilding.getText(), roomFloor.getText(), Integer.parseInt(roomSeats.getText()), roomProjector.isSelected(), roomDocumentCamera.isSelected(), roomLaboratory.isSelected()));
            backToOverview();
        }
    }


}
