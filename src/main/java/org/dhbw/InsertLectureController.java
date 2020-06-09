package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dhbw.classes.Address;
import org.dhbw.classes.Check;
import org.dhbw.classes.Docent;
import org.dhbw.classes.University;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertLectureController {

    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @FXML
    private Label errorMessage;
    @FXML
    private TextField lectureFirstName;
    @FXML
    private TextField lectureLastName;
    @FXML
    private DatePicker lectureBirth;
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


    /**
     * converting a LocalDate to a Date
     *
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    /**
     * initializing javaKnowlage textfield and the comboboxes with object lists
     */
    @FXML
    private void initialize() {
        lectureBirth.setOnKeyReleased(keyEvent -> {
            String text = lectureBirth.getEditor().getText();
            if (text.length() < 10 || text.split("\\.").length != 3) return;
            try {
                Date date = format.parse(text);
                lectureBirth.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException ignored) {
            }
        });
    }

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    /**
     * generating a random number and adding it as the lectureNumber if it is not taken yet
     */
    @FXML
    private void generateLN() {
        while (true) {
            int lectureNumber = (100000 + (int) (Math.random() * 999999));
            if (!Check.checkDNContains(lectureNumber)) {
                lectureNumberField.setText("c" + lectureNumber);
                break;
            }
        }
    }

    /**
     * reading the textfields
     * checking validation of emails, postal code and date
     * generating a new lecture with the entered information and adding the new lecture to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() throws IOException {

        try {

            boolean allRight;

            if (lectureFirstName.getText().trim().isEmpty() || lectureLastName.getText().trim().isEmpty() || lectureBirth.getValue() == null || lectureEmail.getText().trim().isEmpty() || lectureStreet.getText().trim().isEmpty() || lectureHomeNumber.getText().trim().isEmpty() || lecturePostalCode.getText().trim().isEmpty() || lectureCity.getText().trim().isEmpty() || lectureCountry.getText().trim().isEmpty() || lectureNumberField.getText().trim().isEmpty()) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                LocalDate localDateLectureBirth = lectureBirth.getValue();
                Instant instantLectureBirth = Instant.from(localDateLectureBirth.atStartOfDay(ZoneId.systemDefault()));
                Date lectureBirthday = Date.from(instantLectureBirth);
                int focusStage = 0;
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");

                if (!Check.validateEmail(lectureEmail.getText())) {
                    lectureEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    focusStage = 1;
                    errorMessage.setText(errorMessage.getText() + " E-mail-Adresse ");
                } else
                    lectureEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(43, 56, 112, 0.9) rgba(0,0,0,0)");

                if (!Check.validatePostalCode(lecturePostalCode.getText())) {
                    lecturePostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 1) focusStage = 2;
                    errorMessage.setText(errorMessage.getText() + " Postleitzahl ");
                } else
                    lecturePostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(43, 56, 112, 0.9) rgba(0,0,0,0)");
                if (!Check.validateBirthdate(convertToDateViaSqlDate(lectureBirth.getValue()))) {
                    lectureBirth.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (!(focusStage == 1 || focusStage == 2)) focusStage = 3;
                    errorMessage.setText(errorMessage.getText() + " Geburtstag ");
                } else
                    lectureBirth.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");

                if (focusStage == 1) {
                    lectureEmail.requestFocus();
                    errorMessage.setVisible(true);
                    allRight = false;
                } else if (focusStage == 2) {
                    lecturePostalCode.requestFocus();
                    errorMessage.setVisible(true);
                    allRight = false;
                } else if (focusStage == 3) {
                    lectureBirth.requestFocus();
                    errorMessage.setVisible(true);
                    allRight = false;
                }
                else {
                    errorMessage.setVisible(false);
                    allRight = true;
                }

                if (allRight) {
                    University.addDocent(new Docent(
                            lectureLastName.getText(),
                            lectureFirstName.getText(),
                            lectureBirthday,
                            new Address(lectureStreet.getText(), lectureHomeNumber.getText(), lecturePostalCode.getText(), lectureCity.getText(), lectureCountry.getText()),
                            lectureEmail.getText(),
                            Integer.parseInt(lectureNumberField.getText().substring(1))
                    ));
                    backToOverview();
                }
            }
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");
        }

    }

}
