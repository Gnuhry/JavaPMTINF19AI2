package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
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
    private DialogPane showNullPointer;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {App.setRoot("primary");}

    /**
     * reading the textfields
     * checking validation of emails and postal code
     * generating a new company with the entered information and adding the new company to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() {
        try {
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
            showNullPointer.setVisible(true);
            System.out.println("NPE found");
        }
    }

}
