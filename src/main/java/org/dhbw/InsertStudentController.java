package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.dhbw.classes.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class InsertStudentController {

    ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
            //new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Janina", "Hofmann", ""))
    );

    ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    @FXML
    private Label errorMessage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField studentFirstName;
    @FXML
    private TextField studentLastName;
    @FXML
    private DatePicker studentBirth;
    @FXML
    private TextField studentEmail;
    @FXML
    private TextField studentStreet;
    @FXML
    private TextField studentHomeNumber;
    @FXML
    private TextField studentPostalCode;
    @FXML
    private TextField studentCity;
    @FXML
    private TextField studentCountry;
    @FXML
    private TextField studentNumberField;
    @FXML
    private TextField matriculationNumberField;
    @FXML
    private ComboBox<Company> companyChoose;
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
    private HBox companyPerson;
    @FXML
    private TextField companyPersonFirstName;
    @FXML
    private TextField companyPersonLastName;
    @FXML
    private TextField companyPersonEmail;
    @FXML
    private ComboBox<Course> courseName;
    @FXML
    private TextField courseType;
    @FXML
    private DatePicker courseDate;
    @FXML
    private TextField courseRoom;
    @FXML
    private TextField javaKnowledgeLabel;
    @FXML
    private Slider javaKnowledgeSlider;
    @FXML
    private DialogPane showNullPointer;

    /**
     * converting a Date to a LocalDate
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) { return new java.sql.Date(dateToConvert.getTime()).toLocalDate(); }

    /**
     * converting a LocalDate to a Date
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    /**
     * initializing javaKnowlage textfield and the comboboxes with object lists
     */
    @FXML
    private void initialize() {
        companyChoose.setItems(chooseCompanyOptions);
        courseName.setItems(chooseCourseOptions);
        javaKnowledgeLabel.setText("0");
    }

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    /**
     * generating a random number and adding it as the studentNumber if it is not taken yet
     */
    @FXML
    private void generateSN() {
        while (true) {
            int studentNumber = (100000+(int)(Math.random()*900000));
            if (!Check.checkSNContains(studentNumber)) {
                studentNumberField.setText("s" + studentNumber);
                break;
            }
        }
    }

    /**
     * generating a random number and adding it as the matriculationNumber if it is not taken yet
     */
    @FXML
    private void generateMN() {
        while(true) {
            int matriculationNumber = (1000000+(int)(Math.random()*9000000));
            if (!Check.checkMNContains(matriculationNumber)) {
                matriculationNumberField.setText("" + matriculationNumber);
                break;
            }
        }
    }

    /**
     * visualizing the value of the javaKnowledgeSlider in the javaKnowledgeLabel
     */
    @FXML
    private void showSlider() {
        javaKnowledgeLabel.setText("" + (int)javaKnowledgeSlider.getValue());
    }

    /**
     * visualizing information about the chosen course
     */
    @FXML
    private void showCourse() {
        Course course = courseName.getValue();
        courseType.setText("" + course.getStudyCourse());
        courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        courseRoom.setText("" + course.getRoom());
    }

    /**
     * visualizing information about the chosen company
     */
    @FXML
    private void showCompany() {
        Company company = companyChoose.getValue();
        companyName.setText(company.getName());
        companyStreet.setText(company.getAddress().getStreet());
        companyHomeNumber.setText(company.getAddress().getNumber());
        companyPostalCode.setText(company.getAddress().getPostcode());
        companyCity.setText(company.getAddress().getCity());
        companyCountry.setText(company.getAddress().getCountry());
        companyPersonFirstName.setText(company.getContactPerson().getForename());
        companyPersonLastName.setText(company.getContactPerson().getName());
        companyPersonEmail.setText(company.getContactPerson().getEmail());
    }

    /**
     * reading the textfields
     * checking validation of emails, postal code and date
     * generating a new student with the entered information and adding the new student to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() throws IOException {
        try {

            boolean allRight;

            if (studentFirstName.getText().trim().isEmpty() || studentLastName.getText().trim().isEmpty() || studentBirth.getValue() == null || studentEmail.getText().trim().isEmpty() || studentStreet.getText().trim().isEmpty() || studentHomeNumber.getText().trim().isEmpty() || studentPostalCode.getText().trim().isEmpty() || studentCity.getText().trim().isEmpty() || studentCountry.getText().trim().isEmpty() || studentNumberField.getText().trim().isEmpty() || matriculationNumberField.getText().trim().isEmpty() || companyName.getText().trim().isEmpty() || companyStreet.getText().trim().isEmpty() || companyHomeNumber.getText().trim().isEmpty() || companyPostalCode.getText().trim().isEmpty() || companyCity.getText().trim().isEmpty() || companyCountry.getText().trim().isEmpty() || companyPersonFirstName.getText().trim().isEmpty() || companyPersonLastName.getText().trim().isEmpty() || courseName.getEditor().getText().equals("Kurs auswählen") || javaKnowledgeLabel.getText().trim().isEmpty()){
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                Company company;
                Person contactPerson = new Person(companyPersonLastName.getText(), companyPersonFirstName.getText(), companyPersonEmail.getText());
                Address companyAddress = new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText());

                int focusStage = 0;
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
                company = new Company(companyName.getText(), companyAddress, contactPerson);

                if (!Check.validateEmail(studentEmail.getText())) {
                    studentEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    focusStage = 1;
                    errorMessage.setText(errorMessage.getText() + " E-Mail-Adresse ");
                } else {
                    studentEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                }
                if (!Check.validatePostalCode(studentPostalCode.getText())) {
                    studentPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 1) focusStage = 2;
                    errorMessage.setText(errorMessage.getText() + " Student-Postleitzahl ");
                } else studentPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validatePostalCode(companyPostalCode.getText())) {
                    companyPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (!(focusStage == 1 || focusStage == 2)) focusStage = 3;
                    errorMessage.setText(errorMessage.getText() + " Unternehmen-Postleitzahl ");
                } else companyPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validateBirthdate(convertToDateViaSqlDate(studentBirth.getValue()))) {
                    studentBirth.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (!(focusStage == 1 || focusStage == 2 || focusStage == 3)) focusStage = 4;
                    errorMessage.setText(errorMessage.getText() + " Student-Geburtstag ");
                } else studentBirth.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validateEmail(companyPersonEmail.getText())) {
                    companyPersonEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (!(focusStage == 1 || focusStage == 2 || focusStage == 3 || focusStage == 4)) focusStage = 5;
                    errorMessage.setText(errorMessage.getText() + " Ansprechperson-Email ");
                } else companyPersonEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");

                switch (focusStage) {
                    case 1: studentEmail.requestFocus(); scrollPane.setVvalue(0); errorMessage.setVisible(true); allRight = false; break;
                    case 2: studentPostalCode.requestFocus(); scrollPane.setVvalue(0); errorMessage.setVisible(true); allRight = false; break;
                    case 3: companyPostalCode.requestFocus(); scrollPane.setVvalue(100); errorMessage.setVisible(true); allRight = false; break;
                    case 4: studentBirth.requestFocus(); scrollPane.setVvalue(0); errorMessage.setVisible(true); allRight = false; break;
                    case 5: companyPersonEmail.requestFocus(); scrollPane.setVvalue(100); errorMessage.setVisible(true); allRight = false; break;
                    default: errorMessage.setVisible(false); allRight = true; break;
                }

                if (allRight) {
                    DualStudent dualStudent = new DualStudent(
                            Integer.parseInt(matriculationNumberField.getText()),
                            Integer.parseInt(studentNumberField.getText().substring(1)),
                            studentLastName.getText(),
                            studentFirstName.getText(),
                            convertToDateViaSqlDate(studentBirth.getValue()),
                            new Address(studentStreet.getText(), studentHomeNumber.getText(), studentPostalCode.getText(), studentCity.getText(), studentCountry.getText()),
                            studentEmail.getText(),
                            courseName.getValue(),
                            Integer.parseInt(javaKnowledgeLabel.getText()),
                            company
                    );
                    if (companyChoose.getValue() == null) {
                        University.addCompany(company);
                    }
                    University.addStudent(dualStudent);
                    backToOverview();
                }
            }
        } catch (NumberFormatException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");
        }
    }
}
