package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.dhbw.classes.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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


    /**
     * converting a LocalDate to a Date
     *
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return dateToConvert == null ? null : java.sql.Date.valueOf(dateToConvert);
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
        generateLN();
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
        int maxiteration = 100000;
        Random random = new Random();
        Check check = new Check();
        while (--maxiteration > 0) {
            int docentid = random.nextInt(900000) + 100000;
            if (check.checkDNContains(docentid)) {
                lectureNumberField.setText("d" + docentid);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (check.checkDNContains(number++)) {
                lectureNumberField.setText("" + number);
                break;
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

        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = lectureFirstName.getText().trim();
        Date date;
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

        date = convertToDateViaSqlDate(lectureBirth.getValue());
        if (date == null) {
            wrongField(focus, lectureBirth);
            focus = true;
            errorMessageL.add("Geburtstag fehlt");
        } else if (!Check.validateBirthdate(date)) {
            wrongField(focus, lectureBirth);
            focus = true;
            errorMessageL.add("Geburtstag ist falsch");
        } else
            lectureBirth.setStyle(styleRight);

        text = lectureEmail.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, lectureEmail);
            focus = true;
            errorMessageL.add("E-Mail fehlt");
        } else if (!Check.validateEmail(text)) {
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
            University.addDocent(new Docent(
                    lectureLastName.getText(),
                    lectureFirstName.getText(),
                    convertToDateViaSqlDate(lectureBirth.getValue()),
                    new Address(lectureStreet.getText(), lectureHomeNumber.getText(), lecturePostalCode.getText(), lectureCity.getText(), lectureCountry.getText()),
                    lectureEmail.getText(),
                    Integer.parseInt(lectureNumberField.getText())
            ));
            backToOverview();
        }

        /*try {

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
        }*/

    }

    /**
     * visualize the wrong fields
     *
     * @param focus   has any field requested focus
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
