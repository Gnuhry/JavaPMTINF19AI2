package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.Address;
import org.dhbw.classes.Check;
import org.dhbw.classes.Docent;
import org.dhbw.classes.University;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditLectureController {

    private Docent lecture;

    @FXML
    private Label errorMessage;
    @FXML
    private TextField lectureFirstName;
    @FXML
    private TextField lectureLastName;
    @FXML
    private DatePicker lectureBirthday;
    @FXML
    private TextField lectureEmail;
    @FXML
    private TextField lectureStreet;
    @FXML
    private TextField lectureHomeNumber;
    @FXML
    private TextField lecturePostalCode;
    @FXML
    private TextField lectureCity;
    @FXML
    private TextField lectureCountry;
    @FXML
    private TextField lectureNumberField;
    @FXML
    private DialogPane showNullPointer;
    @FXML
    private Button cancelButton;

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
     * visualizing all information about the lecture in the textfields
     *
     * @param lecture student which gets changed
     */
    public void initVariables(Docent lecture) {
        this.lecture = lecture;
        if (!lecture.getForename().isEmpty()) lectureFirstName.setText(lecture.getForename());
        if (!lecture.getName().isEmpty()) lectureLastName.setText(lecture.getName());
        if (lecture.getBirthday() != null)
            lectureBirthday.setValue(convertToLocalDateViaSqlDate(lecture.getBirthday()));
        if (!lecture.getEmail().isEmpty()) lectureEmail.setText(lecture.getEmail());
        if (lecture.getAddress() != null) {
            lectureStreet.setText(lecture.getAddress().getStreet());
            lectureHomeNumber.setText(lecture.getAddress().getNumber());
            lecturePostalCode.setText(lecture.getAddress().getPostcode());
            lectureCity.setText(lecture.getAddress().getCity());
            lectureCountry.setText(lecture.getAddress().getCountry());
        }
        if (lecture.getDocentNumber() != 0) lectureNumberField.setText("" + lecture.getDocentNumber());
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
     * checking validation of emails, postal code and date
     * generating a new lecture with the entered information and adding the new lecture to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() {

        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = lectureFirstName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            wrongField(focus, lectureFirstName);
            focus = true;
            errorMessageL.add("Vorname fehlt");
        } else
            lectureFirstName.setStyle(styleRight);

        text = lectureLastName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureLastName);
            focus = true;
            errorMessageL.add("Nachname fehlt");
        } else
            lectureLastName.setStyle(styleRight);

        text = lectureEmail.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureEmail);
            focus = true;
            errorMessageL.add("E-Mail fehlt");
        } else if (Check.validateEmail(text)) {
            wrongField(focus, lectureEmail);
            focus = true;
            errorMessageL.add("E-Mail ist falsch");
        } else
            lectureEmail.setStyle(styleRight);

        text = lectureStreet.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureStreet);
            focus = true;
            errorMessageL.add("Straße fehlt");
        } else
            lectureStreet.setStyle(styleRight);

        text = lectureHomeNumber.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureHomeNumber);
            focus = true;
            errorMessageL.add("Hausnummer fehlt");
        } else
            lectureHomeNumber.setStyle(styleRight);

        text = lecturePostalCode.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lecturePostalCode);
            focus = true;
            errorMessageL.add("Postleitzahl fehlt");
        } else if (!Check.validatePostalCode(text)) {
            wrongField(focus, lecturePostalCode);
            focus = true;
            errorMessageL.add("Postleitzahl ist falsch");
        } else
            lecturePostalCode.setStyle(styleRight);

        text = lectureCity.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureCity);
            focus = true;
            errorMessageL.add("Stadt fehlt");
        } else
            lectureCity.setStyle(styleRight);

        text = lectureCountry.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureCountry);
            focus = true;
            errorMessageL.add("Land fehlt");
        } else
            lectureCountry.setStyle(styleRight);

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
            University.updateDocent(new Docent(
                    lectureLastName.getText(),
                    lectureFirstName.getText(),
                    lecture.getBirthday(),
                    new Address(lectureStreet.getText(), lectureHomeNumber.getText(), lecturePostalCode.getText(), lectureCity.getText(), lectureCountry.getText()),
                    lectureEmail.getText(),
                    lecture.getDocentNumber()
            ), lecture);
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
