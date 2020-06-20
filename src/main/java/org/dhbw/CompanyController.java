package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyController {

    private Company company_old;

    @FXML
    private Label errorMessage, title;
    @FXML
    private TextField companyName, companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry, companyContactPersonFirstName, companyContactPersonLastName, companyContactPersonEmail;
    @FXML
    private Button buttonDone, buttonCancel;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        if (company_old != null) {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            App.setRoot("primary");
        }
    }

    /**
     * setting the textfield if there is a company to edit
     *
     * @param company edit company or null
     */
    public void initVariables(Company company) {
        company_old = company;
        if (company != null) {
            title.setText(Help.getRessourceBundle().getString("title_company_edit"));
            buttonDone.setText(Help.getRessourceBundle().getString("save"));
            companyName.setText(company.getName());
            Help.fillAddressesToTextField(company.getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
            Help.fillPersonToTextField(company.getContactPerson(), companyContactPersonLastName, companyContactPersonFirstName, companyContactPersonEmail);
        }
    }

    /**
     * reading the textfield
     * checking validation of emails and postal code
     * generating a new company with the entered information and adding the new company to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = companyName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, companyName);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_name"));
        } else
            companyName.setStyle(Help.styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyStreet);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_street"));
        } else
            companyStreet.setStyle(Help.styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyHomeNumber);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_number"));
        } else
            companyHomeNumber.setStyle(Help.styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_postcode"));
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_postcode2"));
        } else
            companyPostalCode.setStyle(Help.styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCity);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_city"));
        } else
            companyCity.setStyle(Help.styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCountry);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_country"));
        } else
            companyCountry.setStyle(Help.styleRight);

        text = companyContactPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonFirstName);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("forename"));
        } else
            companyContactPersonFirstName.setStyle(Help.styleRight);

        text = companyContactPersonLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonLastName);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("last_name"));
        } else
            companyContactPersonLastName.setStyle(Help.styleRight);

        text = companyContactPersonEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("email"));
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("email2"));
        } else
            companyContactPersonEmail.setStyle(Help.styleRight);

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            Company new_company = new Company(
                    companyName.getText(),
                    new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()),
                    new Person(companyContactPersonLastName.getText(), companyContactPersonFirstName.getText(), companyContactPersonEmail.getText())
            );
            if (company_old != null)
                University.updateCompany(new_company, company_old);
            else
                University.addCompany(new_company);
            backToOverview();
        }
    }


}
