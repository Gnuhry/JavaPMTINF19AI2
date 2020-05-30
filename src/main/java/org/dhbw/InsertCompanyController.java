package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dhbw.classes.Address;
import org.dhbw.classes.Company;
import org.dhbw.classes.Person;

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
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void submit() {
        Person contactPerson = new Person(companyContactPersonLastName.getText(), companyContactPersonFirstName.getText());
        errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
        if (!Person.validatePostalCode(companyPostalCode.getText())) {
            companyPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
            companyPostalCode.requestFocus();
            errorMessage.setText(errorMessage.getText() + " Student-Postleitzahl ");
            errorMessage.setVisible(true);
        } else companyPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgba(0, 0, 0, 0.1) rgba(0,0,0,0)");

        Company company = new Company(
                companyName.getText(),
                new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                contactPerson
        );

        System.out.println(company);
    }

}