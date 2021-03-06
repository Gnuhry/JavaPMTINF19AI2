package org.dhbw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.dhbw.Database;
import org.dhbw.classes.*;
import org.dhbw.help.GuiHelp;
import org.dhbw.help.Language;

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
            title.setText(Language.getResourcedBundle().getString("title_room_edit"));
            buttonDone.setText(Language.getResourcedBundle().getString("save"));
            roomName.setText(room.getName());
            roomBuilding.setText(room.getBuilding());
            roomCampus.setValue(room.getCampus());
            roomFloor.setText(room.getFloor());
            roomSeats.setText(String.valueOf(room.getSeats()));
            roomProjector.setSelected(room.getProjector());
            roomDocumentCamera.setSelected(room.getCamera());
            roomLaboratory.setSelected(room.getLaboratory());

            roomName.setDisable(true);
            roomBuilding.setDisable(true);
            roomCampus.setDisable(true);
            roomFloor.setDisable(true);
        }
    }

    /**
     * initializing comboBoxes
     */
    @FXML
    private void initialize() {
        roomCampus.getItems().setAll(chooseTypeOptions);
        roomSeats.addEventFilter(KeyEvent.ANY, new EventHandler<>() {
            private boolean willConsume = false;

            @Override
            public void handle(KeyEvent event) {
                if (willConsume)
                    event.consume();

                if (event.isControlDown()) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED)
                        if (event.getCode() == KeyCode.V)
                            try {
                                Integer.parseInt(Clipboard.getSystemClipboard().getString());
                            } catch (NumberFormatException ex) {
                                event.consume();
                            }
                } else if (!event.getCode().isNavigationKey() && !event.getCode().isDigitKey() && !(event.getCode() == KeyCode.TAB) && !(event.getCode() == KeyCode.DELETE) && !(event.getCode() == KeyCode.BACK_SPACE))
                    if (event.getEventType() == KeyEvent.KEY_PRESSED)
                        willConsume = true;
                    else if (event.getEventType() == KeyEvent.KEY_RELEASED)
                        willConsume = false;
            }
        });
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
            GuiHelp.markWrongField(false, roomName);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("name"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, roomName);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            roomName.setStyle(GuiHelp.styleRight);
        Campus campus = roomCampus.getValue();
        if (campus == null) {
            GuiHelp.markWrongField(focus, roomCampus);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("campus"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, roomCampus);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            roomCampus.setStyle(GuiHelp.styleRight);

        text = roomBuilding.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, roomBuilding);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("building"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, roomBuilding);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            roomBuilding.setStyle(GuiHelp.styleRight);

        text = roomFloor.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, roomFloor);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("floor"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, roomFloor);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            roomFloor.setStyle(GuiHelp.styleRight);

        text = roomSeats.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, roomSeats);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("seats"));
        } else {
            try {
                int x = Integer.parseInt(text);
                if (x <= 0) {
                    GuiHelp.markWrongField(focus, roomSeats);
                    focus = true;
                    errorMessageL.add(Language.getResourcedBundleError().getString("seats3"));
                }
                roomSeats.setStyle(GuiHelp.styleRight);
            } catch (NumberFormatException ex) {
                GuiHelp.markWrongField(focus, roomSeats);
                focus = true;
                errorMessageL.add(Language.getResourcedBundleError().getString("seats2"));
            }
        }

        if (focus)
            GuiHelp.setErrorMessage(errorMessageL, errorMessage);
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
