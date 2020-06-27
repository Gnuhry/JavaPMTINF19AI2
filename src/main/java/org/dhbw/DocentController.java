package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.*;

public class DocentController {

    private Docent docent_old;

    @FXML
    private Label errorMessage, title;
    @FXML
    private TextField docentFirstName, docentLastName, docentEmail, docentStreet, docentHomeNumber, docentPostalCode, docentCity, docentCountry, docentNumberField;
    @FXML
    private DatePicker docentBirth;
    @FXML
    private Button buttonLN, buttonDone, buttonCancel;


    /**
     * changing the scene root in App to "primary.fxml" or closing pop up window
     */
    @FXML
    private void backToOverview() throws IOException {
        if (docent_old != null) {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            App.setRoot("primary");
        }
    }

    /**
     * filling the textfield with docent attributes if docent is not null
     *
     * @param docent docent to fill in or null
     */
    public void initVariables(Docent docent) {
        docent_old = docent;
        if (docent != null) {
            Help.fillPersonToTextField(docent, docentLastName, docentFirstName, docentBirth, docentEmail);
            Help.fillAddressesToTextField(docent.getAddress(), docentStreet, docentHomeNumber, docentPostalCode, docentCity, docentCountry);
            docentNumberField.setText("d" + docent.getDocentNumber());
            buttonLN.setDisable(true);
            docentBirth.setDisable(true);
            title.setText(Help.getResourcedBundle().getString("title_docent_edit"));
            buttonDone.setText(Help.getResourcedBundle().getString("save"));
        }
    }

    /**
     * initializing datepicker and docent number
     */
    @FXML
    private void initialize() {
        Help.addKeyEventDatePicker(docentBirth);
        generateLN();
    }

    /**
     * generating a random number and adding it as the docent number if it is not taken yet
     */
    @FXML
    private void generateLN() {
        int max_iteration = 100000;
        Random random = new Random();
        Help help = new Help();
        while (--max_iteration > 0) {
            int docent_id = random.nextInt(900000) + 100000;
            if (help.checkDNContains(docent_id)) {
                docentNumberField.setText("d" + docent_id);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (help.checkDNContains(number++)) {
                docentNumberField.setText("d" + number);
                break;
            }
    }

    /**
     * set email docent to standard
     */
    @FXML
    private void setDocentEmail() {
        docentEmail.setText(Help.getDocentUniversityEmail(Objects.requireNonNullElseGet(docent_old, () -> new Docent(null, null, null, null, null, Integer.parseInt(docentNumberField.getText().substring(1))))));
    }

    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the docent to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = docentFirstName.getText().trim();
        Date date;
        boolean focus = false;
        if (text.isEmpty()) {
            Help.markWrongField(false, docentFirstName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("forename"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentFirstName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentFirstName.setStyle(Help.styleRight);

        text = docentLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentLastName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("last_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentLastName);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentLastName.setStyle(Help.styleRight);

        date = Help.convertLocalDateDate(docentBirth.getValue());
        if (date == null) {
            Help.markWrongField(focus, docentBirth);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("birthday"));
        } else if (!Help.validateBirthday(date)) {
            Help.markWrongField(focus, docentBirth);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("birthday2"));
        } else
            docentBirth.setStyle(Help.styleRight);

        text = docentEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("email"));
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, docentEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("email2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentEmail);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentEmail.setStyle(Help.styleRight);

        text = docentStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentStreet);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("street"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentStreet);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentStreet.setStyle(Help.styleRight);

        text = docentHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentHomeNumber);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("number"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentHomeNumber);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentHomeNumber.setStyle(Help.styleRight);

        text = docentPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("postcode"));
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, docentPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("postcode2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentPostalCode);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentPostalCode.setStyle(Help.styleRight);

        text = docentCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentCity);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("city"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentCity);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentCity.setStyle(Help.styleRight);

        text = docentCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, docentCountry);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("country"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, docentCountry);
            focus = true;
            errorMessageL.add(Help.getResourcedBundleError().getString("string_to_long"));
        } else
            docentCountry.setStyle(Help.styleRight);

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            Docent new_docent = new Docent(
                    docentLastName.getText(),
                    docentFirstName.getText(),
                    Help.convertLocalDateDate(docentBirth.getValue()),
                    new Address(docentStreet.getText(), docentHomeNumber.getText(), docentPostalCode.getText(), docentCity.getText(), docentCountry.getText()),
                    docentEmail.getText(),
                    Integer.parseInt(docentNumberField.getText().substring(1))
            );
            if (docent_old != null)
                University.updateDocent(new_docent, docent_old);
            else
                University.addDocent(new_docent);
            backToOverview();
        }
    }

}
