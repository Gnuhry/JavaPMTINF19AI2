package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dhbw.classes.*;

import java.io.IOException;

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

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void submit() {
        try {
            boolean allRight = true;

            if (companyName.getText().trim().isEmpty() || companyStreet.getText().trim().isEmpty() || companyHomeNumber.getText().trim().isEmpty() || companyPostalCode.getText().trim().isEmpty() || companyCity.getText().trim().isEmpty() || companyCountry.getText().trim().isEmpty() || companyContactPersonFirstName.getText().trim().isEmpty() || companyContactPersonLastName.getText().trim().isEmpty()) {
                System.out.println("Fehler");
            } else {
                Person contactPerson = new Person(companyContactPersonLastName.getText(), companyContactPersonFirstName.getText(), companyContactPersonEmail.getText());
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
                if (!Check.validatePostalCode(companyPostalCode.getText())) {
                    companyPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    companyPostalCode.requestFocus();
                    errorMessage.setText(errorMessage.getText() + " Student-Postleitzahl ");
                    errorMessage.setVisible(true);
                    allRight = false;
                } else companyPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(0, 0, 0, 0.1) rgba(0,0,0,0)");
                if (!Check.validateEmail(companyContactPersonEmail.getText())) {
                    companyContactPersonEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    companyContactPersonEmail.requestFocus();
                    errorMessage.setText(errorMessage.getText() + " E-Mail-Adresse ");
                    allRight = false;
                } else companyContactPersonEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");

                if (allRight) {
                    Company company = new Company(
                            companyName.getText(),
                            new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                            contactPerson
                    );
                    University.addCompany(company);
                    backToOverview();
                }
            }
        } catch (NullPointerException | IOException npe) {
            System.out.println("Fehler");
        }
    }

}
