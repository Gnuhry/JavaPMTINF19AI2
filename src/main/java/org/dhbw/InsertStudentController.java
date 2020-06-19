package org.dhbw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.dhbw.classes.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InsertStudentController {

    private final ObservableList<Company> chooseCompanyOptions = FXCollections.observableArrayList(
            University.getCompanies()
    );

    private final ObservableList<Course> chooseCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
    );

    private final Company newCompany = new Company("neues Unternehmen", new Address("", "", "", "", ""), new Person("", "", ""));
    private final Course noCourse = new Course("kein Kurs", null, null, null, null);
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
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
     * initializing javaKnowlage textfield and the comboboxes with object lists
     */
    @FXML
    private void initialize() {
        studentBirth.setOnKeyReleased(keyEvent -> {
            String text = studentBirth.getEditor().getText();
            if (text.length() < 10 || text.split("\\.").length != 3) return;
            try {
                Date date = format.parse(text);
                studentBirth.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException ignored) {
            }
        });
        chooseCompanyOptions.add(0, newCompany);
        companyChoose.setItems(chooseCompanyOptions);
        chooseCourseOptions.add(0, noCourse);
        courseName.setItems(chooseCourseOptions);
        javaKnowledgeLabel.setText("0");
        generateSN();
        generateMN();
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
        int maxiteration = 100000;
        Random random = new Random();
        Check check = new Check();
        while (--maxiteration > 0) {
            int studentNumber = random.nextInt(900000) + 100000;
            if (check.checkSNContains(studentNumber)) {
                studentNumberField.setText("s" + studentNumber);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (check.checkSNContains(number++)) {
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
        Check check = new Check();
        while (--maxiteration > 0) {
            int matriculationnumber = random.nextInt(9000000) + 1000000;
            if (check.checkMNContains(matriculationnumber)) {
                matriculationNumberField.setText("" + matriculationnumber);
                return;
            }
        }
        int number = 100000;
        while (number < 10000000)
            if (check.checkMNContains(number++)) {
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
        if (course.equals(noCourse))
            courseDate.setValue(LocalDate.now());
        else
            courseDate.setValue(convertToLocalDateViaSqlDate(course.getRegistrationDate()));
        courseType.setText("" + course.getStudyCourse());
        courseRoom.setText("" + course.getRoom());
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
    private void submit() throws IOException {
        final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
        List<String> errorMessageL = new ArrayList<>();
        String text = studentFirstName.getText().trim();
        Date date;
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

        date = convertToDateViaSqlDate(studentBirth.getValue());
        if (date == null) {
            wrongField(focus, studentBirth, 0);
            focus = true;
            errorMessageL.add("Geburtstag fehlt");
        } else if (!Check.validateBirthdate(date)) {
            wrongField(focus, studentBirth, 0);
            focus = true;
            errorMessageL.add("Geburtstag ist falsch");
        } else
            studentBirth.setStyle(styleRight);

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

        text = companyName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyName, 70);
            focus = true;
            errorMessageL.add("Unternehmensname fehlt");
        } else
            companyName.setStyle(styleRight);

        text = companyStreet.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyStreet, 70);
            focus = true;
            errorMessageL.add("Unternehmensstraße fehlt");
        } else
            companyStreet.setStyle(styleRight);

        text = companyHomeNumber.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyHomeNumber, 70);
            focus = true;
            errorMessageL.add("Unternehmenshausnummer fehlt");
        } else
            companyHomeNumber.setStyle(styleRight);

        text = companyPostalCode.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyPostalCode, 70);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl fehlt");
        } else if (!Check.validatePostalCode(text)) {
            wrongField(focus, companyPostalCode, 70);
            focus = true;
            errorMessageL.add("Unternehmenspostleitzahl ist falsch");
        } else
            companyPostalCode.setStyle(styleRight);

        text = companyCity.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyCity, 70);
            focus = true;
            errorMessageL.add("Unternehmensstadt fehlt");
        } else
            companyCity.setStyle(styleRight);

        text = companyCountry.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyCountry, 70);
            focus = true;
            errorMessageL.add("Unternehmensland fehlt");
        } else
            companyCountry.setStyle(styleRight);

        text = companyPersonFirstName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyPersonFirstName, 70);
            focus = true;
            errorMessageL.add("Ansprechspersonvorname fehlt");
        } else
            companyPersonFirstName.setStyle(styleRight);

        text = companyPersonLastName.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyPersonLastName, 70);
            focus = true;
            errorMessageL.add("Ansprechspersonsname fehlt");
        } else
            companyPersonLastName.setStyle(styleRight);

        text = companyPersonEmail.getText().trim();
        if (text.isEmpty()) {
            wrongField(focus, companyPersonEmail, 70);
            focus = true;
            errorMessageL.add("Ansprechspersonemail fehlt");
        } else if (!Check.validateEmail(text)) {
            wrongField(focus, companyPersonEmail, 70);
            focus = true;
            errorMessageL.add("Ansprechpersonemail ist falsch");
        } else
            companyPersonEmail.setStyle(styleRight);

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
            University.addStudent(new DualStudent(
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
            ));
            backToOverview();
        }
        
/*
        try {

            boolean allRight;

            if (studentFirstName.getText().trim().isEmpty() || studentLastName.getText().trim().isEmpty() || studentBirth.getValue() == null || studentEmail.getText().trim().isEmpty() || studentStreet.getText().trim().isEmpty() || studentHomeNumber.getText().trim().isEmpty() || studentPostalCode.getText().trim().isEmpty() || studentCity.getText().trim().isEmpty() || studentCountry.getText().trim().isEmpty() || studentNumberField.getText().trim().isEmpty() || matriculationNumberField.getText().trim().isEmpty() || companyName.getText().trim().isEmpty() || companyStreet.getText().trim().isEmpty() || companyHomeNumber.getText().trim().isEmpty() || companyPostalCode.getText().trim().isEmpty() || companyCity.getText().trim().isEmpty() || companyCountry.getText().trim().isEmpty() || companyPersonFirstName.getText().trim().isEmpty() || companyPersonLastName.getText().trim().isEmpty() || courseName.getEditor().getText().equals("Kurs auswählen") || javaKnowledgeLabel.getText().trim().isEmpty()) {
                showNullPointer.setVisible(true);
                System.out.println("NPE2 found");
            } else {
                Company company;
                Person contactPerson = new Person(companyPersonLastName.getText(), companyPersonFirstName.getText(), companyPersonEmail.getText());
                Address companyAddress = new Address(companyStreet.getText(), companyHomeNumber.getText(), companyPostalCode.getText(), companyCity.getText(), companyCountry.getText());

                int focusStage = 0;
                errorMessage.setText("Bitte korrigieren Sie die Fehler in folgenden Feldern: ");
                company = new Company(companyName.getText(), companyAddress, contactPerson);

                if (!Check.validateEmail(studentEmail.getText())) {
                    studentEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    focusStage = 1;
                    errorMessage.setText(errorMessage.getText() + " E-Mail-Adresse");
                } else {
                    studentEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                }
                if (!Check.validatePostalCode(studentPostalCode.getText())) {
                    studentPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 0)
                        errorMessage.setText(errorMessage.getText() + ",");
                    if (focusStage != 1) focusStage = 2;
                    errorMessage.setText(errorMessage.getText() + " Student-Postleitzahl");
                } else
                    studentPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validatePostalCode(companyPostalCode.getText())) {
                    companyPostalCode.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 0)
                        errorMessage.setText(errorMessage.getText() + ",");
                    if (!(focusStage < 1)) focusStage = 3;
                    errorMessage.setText(errorMessage.getText() + " Unternehmen-Postleitzahl");
                } else
                    companyPostalCode.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validateBirthdate(convertToDateViaSqlDate(studentBirth.getValue()))) {
                    studentBirth.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 0)
                        errorMessage.setText(errorMessage.getText() + ",");
                    if (!(focusStage < 1)) focusStage = 4;
                    errorMessage.setText(errorMessage.getText() + " Student-Geburtstag");
                } else
                    studentBirth.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                if (!Check.validateEmail(companyPersonEmail.getText())) {
                    companyPersonEmail.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 0)
                        errorMessage.setText(errorMessage.getText() + ",");
                    if (!(focusStage < 1)) focusStage = 5;
                    errorMessage.setText(errorMessage.getText() + " Ansprechperson-Email");
                } else
                    companyPersonEmail.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                try {
                    int value = Integer.parseInt(javaKnowledgeLabel.getText());
                    if (value > 100 || value < 0) {
                        javaKnowledgeLabel.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                        if (focusStage != 0)
                            errorMessage.setText(errorMessage.getText() + ",");
                        if (!(focusStage < 1)) focusStage = 6;
                        errorMessage.setText(errorMessage.getText() + " Java-Wissen");
                    } else
                        javaKnowledgeLabel.setStyle("-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)");
                } catch (NumberFormatException ignored) {
                    javaKnowledgeLabel.setStyle("-fx-text-fill: darkred; -fx-border-color: darkred");
                    if (focusStage != 0)
                        errorMessage.setText(errorMessage.getText() + ",");
                    if (!(focusStage < 1)) focusStage = 6;
                    errorMessage.setText(errorMessage.getText() + " Java-Wissen");
                }
                switch (focusStage) {
                    case 1:
                        studentEmail.requestFocus();
                        scrollPane.setVvalue(0);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    case 2:
                        studentPostalCode.requestFocus();
                        scrollPane.setVvalue(0);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    case 3:
                        companyPostalCode.requestFocus();
                        scrollPane.setVvalue(100);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    case 4:
                        studentBirth.requestFocus();
                        scrollPane.setVvalue(0);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    case 5:
                        companyPersonEmail.requestFocus();
                        scrollPane.setVvalue(100);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    case 6:
                        javaKnowledgeLabel.requestFocus();
                        scrollPane.setVvalue(100);
                        errorMessage.setVisible(true);
                        allRight = false;
                        break;
                    default:
                        errorMessage.setVisible(false);
                        allRight = true;
                        break;
                }

                if (allRight) {
                    Course course = courseName.getValue();
                    if (course.equals(noCourse))
                        course = new Course(null, null, null, null, null);
                    University.addStudent(new DualStudent(
                            Integer.parseInt(matriculationNumberField.getText()),
                            Integer.parseInt(studentNumberField.getText().substring(1)),
                            studentLastName.getText(),
                            studentFirstName.getText(),
                            convertToDateViaSqlDate(studentBirth.getValue()),
                            new Address(studentStreet.getText(), studentHomeNumber.getText(), studentPostalCode.getText(), studentCity.getText(), studentCountry.getText()),
                            studentEmail.getText(),
                            course,
                            Integer.parseInt(javaKnowledgeLabel.getText()),
                            company
                    ));
                    backToOverview();
                }
            }
        } catch (NumberFormatException npe) {
            showNullPointer.setVisible(true);
            System.out.println("NPE found");
        }*/
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
