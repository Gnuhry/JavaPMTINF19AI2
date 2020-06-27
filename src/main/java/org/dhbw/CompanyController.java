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
     * changing the scene root in App to "primary.fxml" or closing pop up window
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
     * filling the textfield with company attributes if company is not null
     *
     * @param company company to fill in or null
     */
    public void initVariables(Company company) {
        company_old = company;
        if (company != null) {
            title.setText(Help.getResourcedBundle().getString("title_company_edit"));
            buttonDone.setText(Help.getResourcedBundle().getString("save"));
            companyName.setText(company.getName());
            Help.fillAddressesToTextField(company.getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
            Help.fillPersonToTextField(company.getContactPerson(), companyContactPersonLastName, companyContactPersonFirstName, companyContactPersonEmail);
        }
    }

    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the company to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = companyName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, companyName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyName.setStyle(Help.styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyStreet);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_street"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyStreet);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyStreet.setStyle(Help.styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyHomeNumber);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_number"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyHomeNumber);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyHomeNumber.setStyle(Help.styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_postcode"));
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, companyPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_postcode2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyPostalCode.setStyle(Help.styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCity);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_city"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyCity);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyCity.setStyle(Help.styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCountry);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("company_country"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyCountry);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyCountry.setStyle(Help.styleRight);

        text = companyContactPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonFirstName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("forename"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyContactPersonFirstName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyContactPersonFirstName.setStyle(Help.styleRight);

        text = companyContactPersonLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonLastName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("last_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyContactPersonLastName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            companyContactPersonLastName.setStyle(Help.styleRight);

        text = companyContactPersonEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("email"));
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, companyContactPersonEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("email2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyContactPersonEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
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
