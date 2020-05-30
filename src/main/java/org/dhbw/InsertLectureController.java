package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dhbw.classes.Address;
import org.dhbw.classes.Check;
import org.dhbw.classes.Docent;
import org.dhbw.classes.Person;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertLectureController {

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


    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void generateLN() throws IOException {
        String lectureNumber = "d" + (100000+(int)(Math.random()*999999));
        lectureNumberField.setText(lectureNumber);
    }

    @FXML
    private void submit() throws IOException {

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
        } else if (focusStage == 2) {
            lecturePostalCode.requestFocus();
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
        }

        Docent docent = new Docent(
                lectureLastName.getText(),
                lectureFirstName.getText(),
                lectureBirthday,
                new Address(lectureStreet.getText(), lectureHomeNumber.getText(), lecturePostalCode.getText(), lectureCity.getText(), lectureCountry.getText()),
                lectureEmail.getText(),
                lectureNumberField.getText()
                );
        System.out.println(docent);
    }

}
