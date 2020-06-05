package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditStudentController {

    ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
    );

    ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    private DualStudent student;

    @FXML
    private Button backToOverview;
    @FXML
    private Button cancelButton;
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
    private Label javaKnowledgeLabel;
    @FXML
    private Slider javaKnowledgeSlider;
    @FXML
    private DialogPane showNullPointer;

    public void initVariables(DualStudent student) {
        if (!student.getForename().isEmpty()) studentFirstName.setText(student.getForename());
        if (!student.getName().isEmpty()) studentLastName.setText(student.getName());
        if (student.getBirthday() != null) studentBirth.setValue(convertToLocalDateViaSqlDate(student.getBirthday()));
        if (!student.getEmail().isEmpty()) studentEmail.setText(student.getEmail());
        if (student.getAddress() != null) {
            studentStreet.setText(student.getAddress().getStreet());
            studentHomeNumber.setText(student.getAddress().getNumber());
            studentPostalCode.setText(student.getAddress().getPostcode());
            studentCity.setText(student.getAddress().getCity());
            studentCountry.setText(student.getAddress().getCountry());
        }
        if (student.getStudentNumber() != 0) studentNumberField.setText("s" + student.getStudentNumber());
        if (student.getMatriculationNumber() != 0) matriculationNumberField.setText("" + student.getMatriculationNumber());
        if (student.getCompany() != null) {
            companyChoose.setValue(student.getCompany());
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
        if (student.getCourse() != null) {
            courseName.setValue(student.getCourse());
            Course course = courseName.getValue();
            courseType.setText("" + course.getStudyCourse());
            courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
            courseRoom.setText("" + course.getRoom());
        }
        if (student.getJavaKnowledge() != 0) {
            javaKnowledgeLabel.setText("" + student.getJavaKnowledge());
            javaKnowledgeSlider.setValue(student.getJavaKnowledge());
        }
    }

    @FXML
    private void initialize() {
        companyChoose.setItems(chooseCompanyOptions);
        courseName.setItems(chooseCourseOptions);
    }

    @FXML
    private void backToOverview() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
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

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    @FXML
    private void showCourse() throws IOException {
        Course course = courseName.getValue();
        courseType.setText("" + course.getStudyCourse());
        courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        courseRoom.setText("" + course.getRoom());
    }

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
            if (studentFirstName.getText().trim().isEmpty() || studentLastName.getText().trim().isEmpty() || studentBirth.getValue() == null || studentEmail.getText().trim().isEmpty() || studentStreet.getText().trim().isEmpty() || studentHomeNumber.getText().trim().isEmpty() || studentPostalCode.getText().trim().isEmpty() || studentCity.getText().trim().isEmpty() || studentCountry.getText().trim().isEmpty() || studentNumberField.getText().trim().isEmpty() || matriculationNumberField.getText().trim().isEmpty() || companyName.getText().trim().isEmpty() || companyStreet.getText().trim().isEmpty() || companyHomeNumber.getText().trim().isEmpty() || companyPostalCode.getText().trim().isEmpty() || companyCity.getText().trim().isEmpty() || companyCountry.getText().trim().isEmpty() || companyPersonFirstName.getText().trim().isEmpty() || companyPersonLastName.getText().trim().isEmpty() || courseName.getEditor().getText().equals("Kurs auswählen") || javaKnowledgeLabel.getText().trim().isEmpty()){
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");    // LOG Datei?
            } else {
                Company company;
                Person contactPerson = new Person(companyPersonLastName.getText(), companyPersonFirstName.getText(), companyPersonEmail.getText());
                Address companyAddress = new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText());

                LocalDate localDateStudentBirth = studentBirth.getValue();
                Instant instantStudentBirth = Instant.from(localDateStudentBirth.atStartOfDay(ZoneId.systemDefault()));
                Date studentBirthday = Date.from(instantStudentBirth);

                int focusStage = 0;
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern");
                company = new Company(companyName.getText(), companyAddress, contactPerson);

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
                if (!Check.validateBirthdate(convertToDateViaSqlDate(studentBirth.getValue()))) {
                    studentPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage == 1 || focusStage == 2 || focusStage == 3) focusStage = 4;
                    errorMessage.setText(errorMessage.getText() + " Student-Geburtstag ");
                }

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
                        courseName.getValue(),
                        Integer.parseInt(javaKnowledgeLabel.getText()),
                        company
                );
                University.updateStudent(dualStudent);
                backToOverview();
            }
        } catch (NumberFormatException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");    // LOG Datei?
        }
    }
}
