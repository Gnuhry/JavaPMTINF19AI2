package org.dhbw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.dhbw.Database;
import org.dhbw.classes.*;
import org.dhbw.help.DateConverter;
import org.dhbw.help.GuiHelp;
import org.dhbw.help.Language;
import org.dhbw.help.Validation;

import java.io.IOException;
import java.util.*;

public class StudentController {

    private final ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
    );

    private final ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    private final Company newCompany = new Company(Language.getResourcedBundle().getString("new_company"), null, null);
    private final Course noCourse = new Course(Language.getResourcedBundle().getString("no_course"), null, null, null, null);
    private DualStudent student_old;

    @FXML
    private Label errorMessage, title;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField studentFirstName, studentLastName, companyName, companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry, companyPersonFirstName, companyPersonLastName, companyPersonEmail, studentEmail, studentStreet, studentHomeNumber, studentPostalCode, studentCity, studentCountry, studentNumberField, matriculationNumberField, javaKnowledgeLabel, courseType, courseRoom, courseDate;
    @FXML
    private DatePicker studentBirth;
    @FXML
    private ComboBox<Company> companyChoose;
    @FXML
    private ComboBox<Course> courseName;
    @FXML
    private Slider javaKnowledgeSlider;
    @FXML
    private Button buttonMN, buttonSN, buttonDone, buttonCancel;
    @FXML
    private ImageView image;

    /**
     * changing the scene root in App to "primary.fxml" or closing pop up window
     */
    @FXML
    private void backToOverview() throws IOException {
        if (student_old != null) {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            App.setRoot("primary");
        }
    }

    /**
     * filling the textfield with student attributes if student is not null
     *
     * @param student student to fill in or null
     */
    public void initVariables(DualStudent student) {
        this.student_old = student;
        if (student_old != null) {
            image.setImage(new Image(getClass().getResourceAsStream("images/addStudentHat.png")));
            title.setText(Language.getResourcedBundle().getString("title_student_edit"));
            buttonDone.setText(Language.getResourcedBundle().getString("save"));
            GuiHelp.fillPersonToTextField(student, studentLastName, studentFirstName, studentBirth, studentEmail);
            GuiHelp.fillAddressesToTextField(student.getAddress(), studentStreet, studentHomeNumber, studentPostalCode, studentCity, studentCountry);
            studentNumberField.setText("s" + student_old.getStudentNumber());
            matriculationNumberField.setText(String.valueOf(student_old.getMatriculationNumber()));
            if (student_old.getCompany() != null) {
                companyChoose.setValue(student_old.getCompany());
                companyName.setText(student_old.getCompany().getName());
                GuiHelp.fillAddressesToTextField(student_old.getCompany().getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
                GuiHelp.fillPersonToTextField(student_old.getCompany().getContactPerson(), companyPersonLastName, companyPersonFirstName, companyPersonEmail);
            }
            if (student_old.getCourse() != null && student_old.getCourse().getRegistrationDate() != null) {
                courseName.setValue(student_old.getCourse());
                Course course = student_old.getCourse();
                courseType.setText(course.getStudyCourse().toString());
                courseDate.setText(GuiHelp.format.format(new Date(course.getRegistrationDate().getTime())));
                courseRoom.setText(course.getRoom().getName());
            }
            javaKnowledgeLabel.setText(String.valueOf(student_old.getJavaKnowledge()));
            javaKnowledgeSlider.setValue(student_old.getJavaKnowledge());

            studentBirth.setDisable(true);
            buttonMN.setDisable(true);
            buttonSN.setDisable(true);
            companyChoose.setDisable(true);
            companyName.setDisable(true);
            companyStreet.setDisable(true);
            companyHomeNumber.setDisable(true);
            companyPostalCode.setDisable(true);
            companyCity.setDisable(true);
            companyCountry.setDisable(true);
            companyPersonFirstName.setDisable(true);
            companyPersonLastName.setDisable(true);
            companyPersonEmail.setDisable(true);
        }
    }

    /**
     * initializing combo boxes, datepicker and matriculation/student number
     */
    @FXML
    private void initialize() {
        chooseCompanyOptions.add(0, newCompany);
        companyChoose.setItems(chooseCompanyOptions);
        chooseCourseOptions.add(0, noCourse);
        courseName.setItems(chooseCourseOptions);
        GuiHelp.addKeyEventDatePicker(studentBirth);
        generateSN();
        generateMN();
    }

    /**
     * generating a random number and adding it as the student number if it is not taken yet
     */
    @FXML
    private void generateSN() {
        int max_iteration = 100000;
        Random random = new Random();
        Validation val = new Validation();
        while (--max_iteration > 0) {
            int studentNumber = random.nextInt(900000) + 100000;
            if (val.validateStudentNumber(studentNumber)) {
                studentNumberField.setText("s" + studentNumber);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (val.validateStudentNumber(number++)) {
                studentNumberField.setText("s" + number);
                break;
            }
    }

    /**
     * generating a random number and adding it as the matriculation number if it is not taken yet
     */
    @FXML
    private void generateMN() {
        int max_iteration = 100000;
        Random random = new Random();
        Validation val = new Validation();
        while (--max_iteration > 0) {
            int matriculation_number = random.nextInt(9000000) + 1000000;
            if (val.validateMatriculationNumber(matriculation_number)) {
                matriculationNumberField.setText("" + matriculation_number);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (val.validateMatriculationNumber(number++)) {
                matriculationNumberField.setText("" + number);
                break;
            }
    }

    /**
     * visualizing the value of the javaKnowledgeSlider in the javaKnowledgeLabel
     */
    @FXML
    private void showSlider() {
        javaKnowledgeLabel.setText(String.valueOf((int) javaKnowledgeSlider.getValue()));
    }

    /**
     * visualizing information about the chosen course
     */
    @FXML
    private void showCourse() {
        Course course = courseName.getValue();
        if (course.equals(noCourse) || course.getStudyCourse() == null || course.getRoom() == null) {
            courseDate.setText("");
            courseType.setText("");
            courseRoom.setText("");
        } else {
            courseDate.setText(GuiHelp.format.format(new Date(course.getRegistrationDate().getTime())));
            courseType.setText(course.getStudyCourse().toString());
            courseRoom.setText(course.getRoom().getName());
        }
    }

    /**
     * visualizing information about the chosen company
     */
    @FXML
    private void showCompany() {
        Company company = companyChoose.getValue();
        if (company.equals(newCompany)) {
            companyName.setText("");
            GuiHelp.fillAddressesToTextField(new Address("", "", "", "", ""), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
            GuiHelp.fillPersonToTextField(new Person("", "", ""), companyPersonLastName, companyPersonFirstName, companyPersonEmail);
            companyName.setDisable(false);
            companyStreet.setDisable(false);
            companyHomeNumber.setDisable(false);
            companyCity.setDisable(false);
            companyCountry.setDisable(false);
            companyPostalCode.setDisable(false);
            companyPersonEmail.setDisable(false);
            companyPersonFirstName.setDisable(false);
            companyPersonLastName.setDisable(false);
        } else {
            companyName.setText(company.getName());
            GuiHelp.fillAddressesToTextField(company.getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
            GuiHelp.fillPersonToTextField(company.getContactPerson(), companyPersonLastName, companyPersonFirstName, companyPersonEmail);
            companyName.setDisable(true);
            companyStreet.setDisable(true);
            companyHomeNumber.setDisable(true);
            companyCity.setDisable(true);
            companyCountry.setDisable(true);
            companyPostalCode.setDisable(true);
            companyPersonEmail.setDisable(true);
            companyPersonFirstName.setDisable(true);
            companyPersonLastName.setDisable(true);
        }
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
     * set email student to standard
     */
    @FXML
    private void setStudentEmail() {
        studentEmail.setText(GuiHelp.getStudentUniversityEmail(Objects.requireNonNullElseGet(student_old, () -> new DualStudent(Integer.parseInt(matriculationNumberField.getText()), Integer.parseInt(studentNumberField.getText().substring(1)), null, null, null, null, null, null, 0, null))));
    }

    /**
     * reading the textfield
     * checking validation of textfield and mark wrong entries
     * adding or editing the student to the database
     */
    @FXML
    private void submit() throws IOException {
        List<String> errorMessageL = new ArrayList<>();
        String text = studentFirstName.getText().trim();
        Date date;
        boolean focus = false;
        if (text.isEmpty()) {
            GuiHelp.markWrongField(false, studentFirstName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("forename"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentFirstName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentFirstName.setStyle(GuiHelp.styleRight);

        text = studentLastName.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentLastName);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("last_name"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentLastName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentLastName.setStyle(GuiHelp.styleRight);

        date = DateConverter.convertLocalDateDate(studentBirth.getValue());
        if (date == null) {
            GuiHelp.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("birthday"));
        } else if (!Validation.validateBirthday(date)) {
            GuiHelp.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("birthday2"));
        } else
            studentBirth.setStyle(GuiHelp.styleRight);

        text = studentEmail.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("email"));
        } else if (!Validation.validateEmail(text)) {
            GuiHelp.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("email2"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentEmail);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentEmail.setStyle(GuiHelp.styleRight);

        text = studentStreet.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentStreet);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("street"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentStreet);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentStreet.setStyle(GuiHelp.styleRight);

        text = studentHomeNumber.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentHomeNumber);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("number"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentHomeNumber);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentHomeNumber.setStyle(GuiHelp.styleRight);

        text = studentPostalCode.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("postcode"));
        } else if (!Validation.validatePostalCode(text)) {
            GuiHelp.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("postcode2"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentPostalCode);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentPostalCode.setStyle(GuiHelp.styleRight);

        text = studentCity.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentCity);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("city"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentCity);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentCity.setStyle(GuiHelp.styleRight);

        text = studentCountry.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, studentCountry);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("country"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, studentCountry);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            studentCountry.setStyle(GuiHelp.styleRight);

        text = companyName.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_name"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyName.setStyle(GuiHelp.styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyStreet);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_street"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyStreet);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyStreet.setStyle(GuiHelp.styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyHomeNumber);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_number"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyHomeNumber);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyHomeNumber.setStyle(GuiHelp.styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyPostalCode);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_postcode"));
        } else if (!Validation.validatePostalCode(text)) {
            GuiHelp.markWrongField(focus, companyPostalCode);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_postcode2"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyPostalCode);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyPostalCode.setStyle(GuiHelp.styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyCity);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_city"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyCity);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyCity.setStyle(GuiHelp.styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyCountry);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("company_country"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyCountry);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyCountry.setStyle(GuiHelp.styleRight);

        text = companyPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyPersonFirstName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("contact_person_forename"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyPersonFirstName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyPersonFirstName.setStyle(GuiHelp.styleRight);

        text = companyPersonLastName.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyPersonLastName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("contact_person_last_name"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyPersonLastName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyPersonLastName.setStyle(GuiHelp.styleRight);

        text = companyPersonEmail.getText().trim();
        if (text.isEmpty()) {
            GuiHelp.markWrongField(focus, companyPersonEmail);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("contact_person_email"));
        } else if (!Validation.validateEmail(text)) {
            GuiHelp.markWrongField(focus, companyPersonEmail);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("contact_person_email2"));
        } else if (text.length() >= Database.maxString) {
            GuiHelp.markWrongField(false, companyPersonEmail);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("string_to_long"));
        } else
            companyPersonEmail.setStyle(GuiHelp.styleRight);

        Course course = courseName.getValue();
        if (course == null) {
            GuiHelp.markWrongField(focus, courseName);
            if (!focus)
                scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("course"));
        } else
            courseName.setStyle(GuiHelp.styleRight);

        text = javaKnowledgeLabel.getText().trim();
        try {
            int number = Integer.parseInt(text);
            if (number < 0 || number > 100) {
                GuiHelp.markWrongField(focus, javaKnowledgeLabel);
                if (!focus)
                    scrollPane.setVvalue(100);
                focus = true;
                errorMessageL.add(Language.getResourcedBundleError().getString("java"));
            }
        } catch (NumberFormatException ignored) {
            GuiHelp.markWrongField(focus, javaKnowledgeLabel);
            if (!focus)
                scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add(Language.getResourcedBundleError().getString("java"));
        }

        if (focus)
            GuiHelp.setErrorMessage(errorMessageL, errorMessage);
        else {
            errorMessage.setVisible(false);
            course = courseName.getValue();
            if (course.equals(noCourse))
                course = new Course(null, null, null, null, null);
            DualStudent new_student = new DualStudent(
                    Integer.parseInt(matriculationNumberField.getText()),
                    Integer.parseInt(studentNumberField.getText().substring(1)),
                    studentLastName.getText(),
                    studentFirstName.getText(),
                    DateConverter.convertLocalDateDate(studentBirth.getValue()),
                    new Address(studentStreet.getText(), studentHomeNumber.getText(), studentPostalCode.getText(), studentCity.getText(), studentCountry.getText()),
                    studentEmail.getText(),
                    course,
                    Integer.parseInt(javaKnowledgeLabel.getText()),
                    new Company(companyName.getText(), new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText()), new Person(companyPersonLastName.getText(), companyPersonFirstName.getText(), companyPersonEmail.getText()))
            );
            if (student_old != null)
                University.updateStudent(new_student, student_old);
            else
                University.addStudent(new_student);
            backToOverview();
        }
    }

}
