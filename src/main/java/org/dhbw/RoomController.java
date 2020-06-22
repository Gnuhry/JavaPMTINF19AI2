package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomController {

    private CourseRoom room_old;

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

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            CourseRoom room_new = new CourseRoom(roomName.getText());
            if (room_old != null) {
                System.out.println(room_new);
                System.out.println(roomCampus.getEditor().getText());
                System.out.println(roomBuilding.getText());
                System.out.println(roomFloor.getText());
                System.out.println(roomSeats.getText());
                System.out.println(roomBeamer);
                System.out.println(roomDocumentCamera);
                System.out.println(roomLaboratory);
            }
                //University.updateRoom(room_new, room_old);
            else
                //University.addRoom(room_new);
            backToOverview();
        }
    }


}
