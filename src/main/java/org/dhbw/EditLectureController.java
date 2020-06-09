package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) { return new java.sql.Date(dateToConvert.getTime()).toLocalDate(); }

    /**
     * visualizing all information about the lecture in the textfields
     * @param lecture student which gets changed
     */
    public void initVariables(Docent lecture) {
        this.lecture = lecture;
        if (!lecture.getForename().isEmpty()) lectureFirstName.setText(lecture.getForename());
        if (!lecture.getName().isEmpty())lectureLastName.setText(lecture.getName());
        if (lecture.getBirthday() != null)lectureBirthday.setValue(convertToLocalDateViaSqlDate(lecture.getBirthday()));
        if (!lecture.getEmail().isEmpty())lectureEmail.setText(lecture.getEmail());
        if (lecture.getAddress() != null) {
            lectureStreet.setText(lecture.getAddress().getStreet());
            lectureHomeNumber.setText(lecture.getAddress().getNumber());
            lecturePostalCode.setText(lecture.getAddress().getPostcode());
            lectureCity.setText(lecture.getAddress().getCity());
            lectureCountry.setText(lecture.getAddress().getCountry());
        }
        if (lecture.getDocentNumber() != 0)lectureNumberField.setText("" + lecture.getDocentNumber());
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

        try {
            boolean allRight;

            if (lectureFirstName.getText().trim().isEmpty() || lectureLastName.getText().trim().isEmpty() || lectureBirthday.getValue() == null || lectureEmail.getText().trim().isEmpty() || lectureStreet.getText().trim().isEmpty() || lectureHomeNumber.getText().trim().isEmpty() || lecturePostalCode.getText().trim().isEmpty() || lectureCity.getText().trim().isEmpty() || lectureCountry.getText().trim().isEmpty() || lectureNumberField.getText().trim().isEmpty()) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                LocalDate localDateLectureBirth = lectureBirthday.getValue();
                Instant instantLectureBirth = Instant.from(localDateLectureBirth.atStartOfDay(ZoneId.systemDefault()));
                Date lectureBirthday = Date.from(instantLectureBirth);
                int focusStage = 0;
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");

                if (!Check.validateEmail(lectureEmail.getText())) {
                    lectureEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    focusStage = 1;
                    errorMessage.setText(errorMessage.getText() + " E-mail-Adresse ");

                } else lectureEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(43, 56, 112, 0.9) rgba(0,0,0,0)");
                if (!Check.validatePostalCode(lecturePostalCode.getText())) {
                    lecturePostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 1) focusStage = 2;
                    errorMessage.setText(errorMessage.getText() + " Postleitzahl ");
                } else lecturePostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(43, 56, 112, 0.9) rgba(0,0,0,0)");

                if (focusStage == 1) {
                    lectureEmail.requestFocus();
                    errorMessage.setVisible(true);
                    allRight = false;
                } else if (focusStage == 2) {
                    lecturePostalCode.requestFocus();
                    errorMessage.setVisible(true);
                    allRight = false;
                } else {
                    errorMessage.setVisible(false);
                    allRight = true;
                }

                if(allRight) {
                    Docent docent = new Docent(
                            lectureLastName.getText(),
                            lectureFirstName.getText(),
                            lectureBirthday,
                            new Address(lectureStreet.getText(), lectureHomeNumber.getText(), lecturePostalCode.getText(), lectureCity.getText(), lectureCountry.getText()),
                            lectureEmail.getText(),
                            Integer.parseInt(lectureNumberField.getText().substring(1))
                    );
                    University.updateDocent(docent, lecture);
                    backToOverview();
                }
            }
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE2 found");
        }

    }

}
