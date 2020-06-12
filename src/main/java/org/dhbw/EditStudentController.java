package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditStudentController {

    private final ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
    );

    private final ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    private final Company newCompany = new Company("neues Unternehmen", new Address("", "", "", "", ""), new Person("", "", ""));
    private final Course noCourse = new Course("kein Kurs", null, null, null, null);

    private DualStudent student_old;

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

    /**
     * converting a Date to a LocalDate
     *
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return dateToConvert == null ? null : new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    /**
     * converting a LocalDate to a Date
     *
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return dateToConvert == null ? null : java.sql.Date.valueOf(dateToConvert);
    }

    /**
     * visualizing all information about the student in the textfields
     *
     * @param student student which gets changed
     */
    public void initVariables(DualStudent student) {
        this.student_old = student;
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
        if (student.getMatriculationNumber() != 0)
            matriculationNumberField.setText("" + student.getMatriculationNumber());
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
        if (student.getCourse() != null && student.getCourse().getRegistrationDate() != null) {
            courseName.setValue(student.getCourse());
            Course course = student.getCourse();
            courseType.setText("" + course.getStudyCourse());
            courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
            courseRoom.setText("" + course.getRoom());
        }
        if (student.getJavaKnowledge() != 0) {
            javaKnowledgeLabel.setText("" + student.getJavaKnowledge());
            javaKnowledgeSlider.setValue(student.getJavaKnowledge());
        }
    }

    /**
     * initializing comboBoxes with object list
     */
    @FXML
    private void initialize() {
        chooseCompanyOptions.add(0, newCompany);
        companyChoose.setItems(chooseCompanyOptions);
        chooseCourseOptions.add(0, noCourse);
        courseName.setItems(chooseCourseOptions);
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
     * visualizing the value of the javaKnowledgeSlider in the javaKnowledgeLabel
     */
    @FXML
    private void showSlider() {
        javaKnowledgeLabel.setText("" + (int) javaKnowledgeSlider.getValue());
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
     * set value from text box to java knowledge slider
     */
    @FXML
    public void javaKnowledgeLabelKeyReleased() {
        String text = javaKnowledgeLabel.getText();
        try {
            int number = Integer.parseInt(text);
            if (number > 0 && number < 100)
                javaKnowledgeSlider.setValue(number);
            else if (number <= 0)
                javaKnowledgeSlider.setValue(0);
            else javaKnowledgeSlider.setValue(100);
        } catch (NumberFormatException ignored) {
        }
    }

    /**
     * reading the textfields
     * checking validation of emails, postal code and date
     * generating a new student with the entered information and adding the new student to the database
     * catching NullPointerException to give a visual feedback to the user
     */
    @FXML
    private void submit() {
        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = studentFirstName.getText().trim();
        boolean focus = false;
        if (text.isEmpty()) {
            wrongField(focus, studentFirstName, 0);
            focus = true;
            errorMessageL.add("Vorname fehlt");
        } else
            studentFirstName.setStyle(styleRight);

        text = studentLastName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentLastName, 0);
            focus = true;
            errorMessageL.add("Nachname fehlt");
        } else
            studentLastName.setStyle(styleRight);

        text = studentEmail.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentEmail, 0);
            focus = true;
            errorMessageL.add("E-Mail fehlt");
        } else if (!Check.validateEmail(text)) {
            wrongField(focus, studentEmail, 0);
            focus = true;
            errorMessageL.add("E-Mail ist falsch");
        } else
            studentEmail.setStyle(styleRight);

        text = studentStreet.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentStreet, 0);
            focus = true;
            errorMessageL.add("Straße fehlt");
        } else
            studentStreet.setStyle(styleRight);

        text = studentHomeNumber.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentHomeNumber, 0);
            focus = true;
            errorMessageL.add("Hausnummer fehlt");
        } else
            studentHomeNumber.setStyle(styleRight);

        text = studentPostalCode.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentPostalCode, 0);
            focus = true;
            errorMessageL.add("Postleitzahl fehlt");
        } else if (!Check.validatePostalCode(text)) {
            wrongField(focus, studentPostalCode, 0);
            focus = true;
            errorMessageL.add("Postleitzahl ist falsch");
        } else
            studentPostalCode.setStyle(styleRight);

        text = studentCity.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentCity, 0);
            focus = true;
            errorMessageL.add("Stadt fehlt");
        } else
            studentCity.setStyle(styleRight);

        text = studentCountry.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, studentCountry, 0);
            focus = true;
            errorMessageL.add("Land fehlt");
        } else
            studentCountry.setStyle(styleRight);

        Course course = courseName.getValue();
        if (course == null) {
            wrongField(focus, courseName, 100);
            focus = true;
            errorMessageL.add("Kurs nicht ausgewählt");
        } else
            courseName.setStyle(styleRight);

        text = javaKnowledgeLabel.getText().trim();
        try {
            int number = Integer.parseInt(text);
            if (number < 0 || number > 100) {
                wrongField(focus, javaKnowledgeLabel, 100);
                focus = true;
                errorMessageL.add("Java-Wissen Index ist falsch");
            }
        } catch (NumberFormatException ignored) {
            wrongField(focus, javaKnowledgeLabel, 100);
            focus = true;
            errorMessageL.add("Java-Wissen Index ist falsch");
        }

        if (focus) {
            StringBuilder sb = new StringBuilder(errorMessageL.remove(0));
            for (String s : errorMessageL)
                sb.append(", ").append(s);
            for (int f = 190; f < sb.length(); f += 190)
                sb.insert(f, "\n");
            errorMessage.setText(sb.toString());
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            course = courseName.getValue();
            if (course.equals(noCourse))
                course = new Course(null, null, null, null, null);
            University.updateStudent(new DualStudent(
                    Integer.parseInt(matriculationNumberField.getText()),
                    Integer.parseInt(studentNumberField.getText().substring(1)),
                    studentLastName.getText(),
                    studentFirstName.getText(),
                    convertToDateViaSqlDate(studentBirth.getValue()),
                    new Address(studentStreet.getText(), studentHomeNumber.getText(), studentPostalCode.getText(), studentCity.getText(), studentCountry.getText()),
                    studentEmail.getText(),
                    course,
                    Integer.parseInt(javaKnowledgeLabel.getText()),
                    new Company(companyName.getText(), new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()), new Person(companyPersonLastName.getText(), companyPersonFirstName.getText(), companyPersonEmail.getText()))
            ), student_old);
            backToOverview();
        }

    }


    /**
     * visualize the wrong fields
     *
     * @param focus   has any field requested focus
     * @param control control to mark visualized
     * @param scroll  scroll procent to show control
     */
    private void wrongField(boolean focus, Control control, int scroll) {
        final String styleWrong = "-fx-text-fill: darkred; -fx-border-color: darkred";
        control.setStyle(styleWrong);
        if (!focus) {
            control.requestFocus();
            scrollPane.setVvalue(scroll);
        }
    }
}
