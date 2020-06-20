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

    private final Company newCompany = new Company("neues Unternehmen", new Address("", "", "", "", ""), new Person("", "", ""));
    private final Course noCourse = new Course("kein Kurs", null, null, null, null);
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
     * changing the scene root in App to "primary.fxml"
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
     * setting the textfieds if there is a student to edit
     *
     * @param student edit student or null
     */
    public void initVariables(DualStudent student) {
        this.student_old = student;
        if (student_old != null) {
            image.setImage(new Image(getClass().getResourceAsStream("images/addStudentHat.png")));
            title.setText("Studierenden bearbeiten");
            buttonDone.setText("Speichern");
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
     * initializing javaKnowlage textfield and the comboboxes with object lists
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
     * generating a random number and adding it as the studentNumber if it is not taken yet
     */
    @FXML
    private void generateSN() {
        int maxiteration = 100000;
        Random random = new Random();
        Help help = new Help();
        while (--maxiteration > 0) {
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
     * generating a random number and adding it as the matriculationNumber if it is not taken yet
     */
    @FXML
    private void generateMN() {
        int maxiteration = 100000;
        Random random = new Random();
        Help help = new Help();
        while (--maxiteration > 0) {
            int matriculationnumber = random.nextInt(9000000) + 1000000;
            if (help.checkMNContains(matriculationnumber)) {
                matriculationNumberField.setText("" + matriculationnumber);
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
        javaKnowledgeLabel.setText("" + (int) javaKnowledgeSlider.getValue());
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
     * reading the textfields
     * checking validation of emails, postal code and date
     * generating a new student or edit one with the entered information and adding the new or edited student to the database
     * catching NullPointerException to give a visual feedback to the user
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
            errorMessageL.add("Vorname fehlt");
        } else
            studentFirstName.setStyle(Help.styleRight);

        text = studentLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentLastName);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Nachname fehlt");
        } else
            studentLastName.setStyle(Help.styleRight);

        date = Help.convertLocalDateDate(studentBirth.getValue());
        if (date == null) {
            Help.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Geburtstag fehlt");
        } else if (!Help.validateBirthdate(date)) {
            Help.markWrongField(focus, studentBirth);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Geburtstag ist falsch");
        } else
            studentBirth.setStyle(Help.styleRight);

        text = studentEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("E-Mail fehlt");
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, studentEmail);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("E-Mail ist falsch");
        } else
            studentEmail.setStyle(Help.styleRight);

        text = studentStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentStreet);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Straße fehlt");
        } else
            studentStreet.setStyle(Help.styleRight);

        text = studentHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentHomeNumber);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Hausnummer fehlt");
        } else
            studentHomeNumber.setStyle(Help.styleRight);

        text = studentPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Postleitzahl fehlt");
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, studentPostalCode);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Postleitzahl ist falsch");
        } else
            studentPostalCode.setStyle(Help.styleRight);

        text = studentCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentCity);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Stadt fehlt");
        } else
            studentCity.setStyle(Help.styleRight);

        text = studentCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, studentCountry);
            if (!focus)
                scrollPane.setVvalue(0);
            focus = true;
            errorMessageL.add("Land fehlt");
        } else
            studentCountry.setStyle(Help.styleRight);

        text = companyName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmensname fehlt");
        } else
            companyName.setStyle(Help.styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyStreet);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmensstraße fehlt");
        } else
            companyStreet.setStyle(Help.styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyHomeNumber);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmenshausnummer fehlt");
        } else
            companyHomeNumber.setStyle(Help.styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPostalCode);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl fehlt");
        } else if (!Help.validatePostalCode(text)) {
            Help.markWrongField(focus, companyPostalCode);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl ist falsch");
        } else
            companyPostalCode.setStyle(Help.styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCity);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmensstadt fehlt");
        } else
            companyCity.setStyle(Help.styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyCountry);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Unternehmensland fehlt");
        } else
            companyCountry.setStyle(Help.styleRight);

        text = companyPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonFirstName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Kontaktvorname fehlt");
        } else
            companyPersonFirstName.setStyle(Help.styleRight);

        text = companyPersonLastName.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonLastName);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Kontaktname fehlt");
        } else
            companyPersonLastName.setStyle(Help.styleRight);

        text = companyPersonEmail.getText().trim();
        if (text.isEmpty()) {
            Help.markWrongField(focus, companyPersonEmail);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Kontaktemail fehlt");
        } else if (!Help.validateEmail(text)) {
            Help.markWrongField(focus, companyPersonEmail);
            scrollPane.setVvalue(70);
            focus = true;
            errorMessageL.add("Kontaktemail ist falsch");
        } else
            companyPersonEmail.setStyle(Help.styleRight);

        Course course = courseName.getValue();
        if (course == null) {
            Help.markWrongField(focus, courseName);
            scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add("Kurs nicht ausgewählt");
        } else
            courseName.setStyle(Help.styleRight);

        text = javaKnowledgeLabel.getText().trim();
        try {
            int number = Integer.parseInt(text);
            if (number < 0 || number > 100) {
                Help.markWrongField(focus, javaKnowledgeLabel);
                scrollPane.setVvalue(100);
                focus = true;
                errorMessageL.add("Java-Wissen Index ist falsch");
            }
        } catch (NumberFormatException ignored) {
            Help.markWrongField(focus, javaKnowledgeLabel);
            scrollPane.setVvalue(100);
            focus = true;
            errorMessageL.add("Java-Wissen Index ist falsch");
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
