package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertCompanyController {


    @FXML
    private Label errorMessage;
    @FXML
    private TextField companyName;
    @FXML
    private TextField companyStreet;
    @FXML
    private TextField companyHomeNumber;
    @FXML
    private TextField companyPostalCode;
    @FXML
    private TextField companyCity;
    @FXML
    private TextField companyCountry;
    @FXML
    private TextField companyContactPersonFirstName;
    @FXML
    private TextField companyContactPersonLastName;
    @FXML
    private TextField companyContactPersonEmail;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    /**
     * reading the textfields
     * checking validation of emails and postal code
     * generating a new company with the entered information and adding the new company to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() throws IOException {
        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = companyName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            wrongField(focus, companyName);
            focus = true;
            errorMessageL.add("Unternehmensname fehlt");
        } else
            companyName.setStyle(styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyStreet);
            focus = true;
            errorMessageL.add("Unternehmensstraße fehlt");
        } else
            companyStreet.setStyle(styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyHomeNumber);
            focus = true;
            errorMessageL.add("Unternehmenshausnummer fehlt");
        } else
            companyHomeNumber.setStyle(styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl fehlt");
        } else if (!Check.validatePostalCode(text)) {
            wrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl ist falsch");
        } else
            companyPostalCode.setStyle(styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyCity);
            focus = true;
            errorMessageL.add("Unternehmensstadt fehlt");
        } else
            companyCity.setStyle(styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyCountry);
            focus = true;
            errorMessageL.add("Unternehmensland fehlt");
        } else
            companyCountry.setStyle(styleRight);

        text = companyContactPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyContactPersonFirstName);
            focus = true;
            errorMessageL.add("Vorname fehlt");
        } else
            companyContactPersonFirstName.setStyle(styleRight);

        text = companyContactPersonLastName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyContactPersonLastName);
            focus = true;
            errorMessageL.add("Name fehlt");
        } else
            companyContactPersonLastName.setStyle(styleRight);

        text = companyContactPersonEmail.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add("Email fehlt");
        } else if (!Check.validateEmail(text)) {
            wrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add("E-Mail ist falsch");
        } else
            companyContactPersonEmail.setStyle(styleRight);

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
            University.addCompany(new Company(
                    companyName.getText(),
                    new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                    new Person(companyContactPersonLastName.getText(), companyContactPersonFirstName.getText(), companyContactPersonEmail.getText())
            ));
            backToOverview();
        }
       /* try {
            boolean allRight = true;

            if (companyName.getText().trim().isEmpty() || companyStreet.getText().trim().isEmpty() || companyHomeNumber.getText().trim().isEmpty() || companyPostalCode.getText().trim().isEmpty() || companyCity.getText().trim().isEmpty() || companyCountry.getText().trim().isEmpty() || companyContactPersonFirstName.getText().trim().isEmpty() || companyContactPersonLastName.getText().trim().isEmpty()) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                Person contactPerson = new Person(companyContactPersonLastName.getText(), companyContactPersonFirstName.getText(), companyContactPersonEmail.getText());
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
                if (!Check.validatePostalCode(companyPostalCode.getText())) {
                    companyPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    companyPostalCode.requestFocus();
                    errorMessage.setText(errorMessage.getText() + " Student-Postleitzahl ");
                    errorMessage.setVisible(true);
                    allRight = false;
                } else
                    companyPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(0, 0, 0, 0.1) rgba(0,0,0,0)");
                if (!Check.validateEmail(companyContactPersonEmail.getText())) {
                    companyContactPersonEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    companyContactPersonEmail.requestFocus();
                    errorMessage.setText(errorMessage.getText() + " E-Mail-Adresse ");
                    allRight = false;
                } else
                    companyContactPersonEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");

                if (allRight) {
                    University.addCompany(new Company(
                            companyName.getText(),
                            new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                            contactPerson
                    ));
                    backToOverview();
                }
            }
        } catch (NullPointerException | IOException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");
        }*/
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
