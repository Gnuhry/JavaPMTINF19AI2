package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dhbw.classes.*;

public class EditCompanyController {

    private Company company_old;

    @FXML
    private Button cancelButton;
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
     * visualizing all information about the company in the textfields
     * @param company student which gets changed
     */
    public void initVariables(Company company) {
        this.company_old = company;
        if (!company.getName().isEmpty()) companyName.setText(company.getName());
        if (company.getAddress() != null) {
            companyStreet.setText(company.getAddress().getStreet());
            companyHomeNumber.setText(company.getAddress().getNumber());
            companyPostalCode.setText(company.getAddress().getPostcode());
            companyCity.setText(company.getAddress().getCity());
            companyCountry.setText(company.getAddress().getCountry());
        }
        if (company.getContactPerson() != null) {
            companyContactPersonFirstName.setText(company.getContactPerson().getForename());
            companyContactPersonLastName.setText(company.getContactPerson().getName());
            companyContactPersonEmail.setText(company.getContactPerson().getEmail());
        }
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
     * checking validation of emails and postal code
     * generating a new company with the entered information and adding the new company to the database
     * catching NullPointerException to give a visual feedback to the user
     */
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

                if(allRight) {
                    Company company = new Company(
                            companyName.getText(),
                            new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                            contactPerson
                    );
                    University.updateCompany(company, company_old);
                    backToOverview();
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("Fehler");
        }
    }

}
