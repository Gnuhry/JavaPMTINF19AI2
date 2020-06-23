package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StudentController {

    private final ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
    );

    private final ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    private final Company newCompany = new Company(Help.getRessourceBundle().getString("new_company"), null, null);
    private final Course noCourse = new Course(Help.getRessourceBundle().getString("no_course"), null, null, null, null);
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
            title.setText(Help.getRessourceBundle().getString("title_student_edit"));
            buttonDone.setText(Help.getRessourceBundle().getString("save"));
            Help.fillPersonToTextField(student, studentLastName, studentFirstName, studentBirth, studentEmail);
            Help.fillAddressesToTextField(student.getAddress(), studentStreet, studentHomeNumber, studentPostalCode, studentCity, studentCountry);
            studentNumberField.setText("s" + student_old.getStudentNumber());
            matriculationNumberField.setText(String.valueOf(student_old.getMatriculationNumber()));
            if (student_old.getCompany() != null) {
                companyChoose.setValue(student_old.getCompany());
                companyName.setText(student_old.getCompany().getName());
                Help.fillAddressesToTextField(student_old.getCompany().getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
                Help.fillPersonToTextField(student_old.getCompany().getContactPerson(), companyPersonLastName, companyPersonFirstName, companyPersonEmail);
            }
            if (student_old.getCourse() != null && student_old.getCourse().getRegistrationDate() != null) {
                courseName.setValue(student_old.getCourse());
                Course course = student_old.getCourse();
                courseType.setText(course.getStudyCourse().toString());
                courseDate.setText(Help.format.format(new Date(course.getRegistrationDate().getTime())));
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
        Help.addKeyEventDatePicker(studentBirth);
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
        Help help = new Help();
        while (--max_iteration > 0) {
            int studentNumber = random.nextInt(900000) + 100000;
            if (help.checkSNContains(studentNumber)) {
                studentNumberField.setText("s" + studentNumber);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (help.checkSNContains(number++)) {
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
        Help help = new Help();
        while (--max_iteration > 0) {
            int matriculation_number = random.nextInt(9000000) + 1000000;
            if (help.checkMNContains(matriculation_number)) {
                matriculationNumberField.setText("" + matriculation_number);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (help.checkMNContains(number++)) {
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
            courseDate.setText(Help.format.format(new Date(course.getRegistrationDate().getTime())));
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
        if (company.equals(newCompany))
            companyName.setText("");
        else
            companyName.setText(company.getName());
        Help.fillAddressesToTextField(company.getAddress(), companyStreet, companyHomeNumber, companyPostalCode, companyCity, companyCountry);
        Help.fillPersonToTextField(company.getContactPerson(), companyPersonLastName, companyPersonFirstName, companyPersonEmail);

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
            Help.markWrongField(false, studentFirstName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("forename"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentFirstName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentFirstName.setStyle(Help.styleRight);

        text = studentLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentLastName);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("last_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentLastName);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentLastName.setStyle(Help.styleRight);

        date = Help.convertLocalDateDate(studentBirth.getValue());
        if (date == null) {
            Help.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("birthday"));
        } else if (!Help.validateBirthdate(date)) {
            Help.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("birthday2"));
        } else
            studentBirth.setStyle(Help.styleRight);

        text = studentEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("email"));
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("email2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentEmail);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentEmail.setStyle(Help.styleRight);

        text = studentStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentStreet);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("street"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentStreet);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentStreet.setStyle(Help.styleRight);

        text = studentHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentHomeNumber);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("number"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentHomeNumber);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentHomeNumber.setStyle(Help.styleRight);

        text = studentPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("postcode"));
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("postcode2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentPostalCode);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentPostalCode.setStyle(Help.styleRight);

        text = studentCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentCity);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("city"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentCity);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentCity.setStyle(Help.styleRight);

        text = studentCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentCountry);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("country"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, studentCountry);
            scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            studentCountry.setStyle(Help.styleRight);

        text = companyName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyName.setStyle(Help.styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyStreet);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_street"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyStreet);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyStreet.setStyle(Help.styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyHomeNumber);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_number"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyHomeNumber);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyHomeNumber.setStyle(Help.styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPostalCode);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_postcode"));
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, companyPostalCode);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_postcode2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyPostalCode);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyPostalCode.setStyle(Help.styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCity);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_city"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyCity);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyCity.setStyle(Help.styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCountry);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("company_country"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyCountry);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyCountry.setStyle(Help.styleRight);

        text = companyPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonFirstName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("contact_person_forename"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyPersonFirstName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyPersonFirstName.setStyle(Help.styleRight);

        text = companyPersonLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonLastName);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("contact_person_last_name"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyPersonLastName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyPersonLastName.setStyle(Help.styleRight);

        text = companyPersonEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonEmail);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("contact_person_email"));
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, companyPersonEmail);
            if (!focus)
                scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("contact_person_email2"));
        } else if (text.length() >= Database.maxString) {
            Help.markWrongField(false, companyPersonEmail);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("string_to_long"));
        } else
            companyPersonEmail.setStyle(Help.styleRight);

        Course course = courseName.getValue();
        if (course == null) {
            Help.markWrongField(focus, courseName);
            if (!focus)
                scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("course"));
        } else
            courseName.setStyle(Help.styleRight);

        text = javaKnowledgeLabel.getText().trim();
        try {
            int number = Integer.parseInt(text);
            if (number < 0 || number > 100) {
                Help.markWrongField(focus, javaKnowledgeLabel);
                if (!focus)
                    scrollPane.setVvalue(100);
                focus = true;
                errorMessageL.add(Help.getRessourceBundleError().getString("java"));
            }
        } catch (NumberFormatException ignored) {
            Help.markWrongField(focus, javaKnowledgeLabel);
            if (!focus)
                scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add(Help.getRessourceBundleError().getString("java"));
        }

        if (focus)
            Help.setErrorMessage(errorMessageL, errorMessage);
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
                    Help.convertLocalDateDate(studentBirth.getValue()),
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
