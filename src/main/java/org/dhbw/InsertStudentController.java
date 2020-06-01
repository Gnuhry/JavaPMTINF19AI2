package org.dhbw;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dhbw.classes.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertStudentController {

    ObservableList<String> test = FXCollections.observableArrayList(
            "1",
            "2",
            "3"
    );
    ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Janina", "Hofmann"))
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
    private DatePicker studentBirth = new DatePicker();
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
    private ComboBox<String> companyChoose;
    @FXML
    private VBox cCompany;
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
    private ComboBox companyPersonChoose;
    @FXML
    private TextField companyPersonFirstName;
    @FXML
    private TextField companyPersonLastName;
    @FXML
    private ComboBox courseName;
    @FXML
    private TextField courseType;
    @FXML
    private DatePicker courseDate;
    @FXML
    private Label javaKnowledgeLabel;
    @FXML
    private Slider javaKnowledgeSlider;
    @FXML
    private DialogPane showNullPointer;


    private void initialize() {
        companyChoose.setValue("1");
        companyChoose.setItems(test);
    }


    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void generateSN() throws IOException {
        String studentNumber = "s" + (100000+(int)(Math.random()*900000));
        studentNumberField.setText(studentNumber);
    }

    @FXML
    private void generateMN() throws IOException {
        String matriculationNumber = "" + (1000000+(int)(Math.random()*9000000));
        matriculationNumberField.setText(matriculationNumber);
    }

    @FXML
    private void showSlider() throws IOException {
        javaKnowledgeLabel.setText("" + (int)javaKnowledgeSlider.getValue());
    }

    @FXML
    private void createCompany() throws IOException {
        cCompany.setVisible(true);
    }

    @FXML
    private void createCompanyPerson() throws IOException {
        companyPerson.setVisible(true);
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    private void showCourse() throws IOException {
        Course course = (Course)courseName.getValue();
        courseType.setText("" + course.getStudyCourse());
        courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
    }

    @FXML
    private void acceptTab(KeyEvent keyEvent) {
            System.out.println("test");
            if (keyEvent.getCode() == KeyCode.TAB) {
                System.out.println("Hallo" + studentBirth.getEditor().getText());
            }
    }



    @FXML
    private void submit() throws IOException {
        try {
            Company company;
            Person contactPerson = new Person(companyPersonFirstName.getText(), companyPersonLastName.getText());
            Address companyAddress = new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText());
            LocalDate localDateStudentBirth = studentBirth.getValue();
            Instant instantStudentBirth = Instant.from(localDateStudentBirth.atStartOfDay(ZoneId.systemDefault()));
            Date studentBirthday = Date.from(instantStudentBirth);
            int focusStage = 0;
            errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
            if (companyChoose.getAccessibleText() != null) {
                company = new Company(companyChoose.getAccessibleText(), companyAddress, contactPerson);
            } else {
                if (companyPersonChoose.getAccessibleText() != null) {
                    company = new Company(companyName.getText(), companyAddress, contactPerson); //eigentlich muss hier die companyPersonChoose in eine Person umgewandelt werden
                } else {
                    company = new Company(companyName.getText(), companyAddress, contactPerson);
                }
            }
            if (!Check.validateEmail(studentEmail.getText())) {
                studentEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                focusStage = 1;
                errorMessage.setText(errorMessage.getText() + " E-Mail-Adresse ");
            } else studentEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
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

            if (focusStage == 1) {
                studentEmail.requestFocus();
                scrollPane.setVvalue(0);
                errorMessage.setVisible(true);
            } else if (focusStage == 2) {
                studentPostalCode.requestFocus();
                scrollPane.setVvalue(0);
                errorMessage.setVisible(true);
            } else if (focusStage == 3) {
                companyPostalCode.requestFocus();
                scrollPane.setVvalue(100);
                errorMessage.setVisible(true);
            } else {
                errorMessage.setVisible(false);
            }



            DualStudent dualStudent = new DualStudent(
                    Integer.parseInt(matriculationNumberField.getText()),
                    Integer.parseInt(studentNumberField.getText().substring(1)),
                    studentLastName.getText(),
                    studentFirstName.getText(),
                    studentBirthday,
                    new Address(studentStreet.getText(), studentHomeNumber.getText(), studentPostalCode.getText(), studentCity.getText(), studentCountry.getText()),
                    studentEmail.getText(),
                    (Course)courseName.getValue(),
                    Integer.parseInt(javaKnowledgeLabel.getText()),
                    company
            );
            System.out.println(dualStudent);
        } catch (NullPointerException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");    // LOG Datei?
        }

    }
}
