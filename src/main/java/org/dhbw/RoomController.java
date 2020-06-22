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
    private CheckBox roomBeamer, roomDocumentCamera, roomLaboratory;
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
     * filling the textfield with company attributes if company is not null
     *
     * @param room company to fill in or null
     */
    public void initVariables(CourseRoom room) {
        room_old = room;
        if (room != null) {
            title.setText(Help.getRessourceBundle().getString("title_company_edit"));
            buttonDone.setText(Help.getRessourceBundle().getString("save"));
            roomName.setText(room.getName());
        }
    }

    /**
     * initializing comboBoxes and datepicker
     */
    @FXML
    private void initialize() {
        roomCampus.getItems().setAll(chooseTypeOptions);
    }

    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the company to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = roomName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, roomName);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_name"));
        } else
            roomName.setStyle(Help.styleRight);
        Campus campus = roomCampus.getValue();
        if (campus == null) {
            Help.markWrongField(focus, roomCampus);
            focus = true;
            //errorMessageL.add(Help.getRessourceBundleError().getString("campus"));
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
        } else
            roomSeats.setStyle(Help.styleRight);

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            CourseRoom room_new = new CourseRoom(roomName.getText(), roomCampus.getValue(), roomBuilding.getText(), roomFloor.getText(), Integer.parseInt(roomSeats.getText()), roomBeamer.isSelected(), roomDocumentCamera.isSelected(), roomLaboratory.isSelected());
            if (room_old != null) {
                System.out.println(room_new);
            }
                //University.updateRoom(room_new, room_old);
            else
                System.out.println(room_new);
                //University.addRoom(room_new);
            backToOverview();
        }
    }


}
