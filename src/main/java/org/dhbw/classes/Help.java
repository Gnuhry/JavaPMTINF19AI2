package org.dhbw.classes;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Help {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)"),
            VALID_POSTAL_CODE_REGEX = Pattern.compile("^[0-9]{5}$");
    private static final String styleWrong = "-fx-text-fill: darkred; -fx-border-color: darkred", lang_file_path = "org.dhbw.lang.strings", lang_file_path_error = "org.dhbw.lang.error", settings_path = "settings.properties";

    public static final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private static final String[] supportedLanguage = new String[]{"de", "en"};

    private static final String email_end_student="@student.dhbw-mannheim.de", email_end_docent="@docent.dhbw-mannheim.de";

    private static Locale locale;

    private List<Integer> student_ids, docent_ids, matriculation_ids;

    //-----------------------------------validation----------------------------------------

    /**
     * validate if date is before today
     *
     * @param date the date to validate
     * @return {true} if date is before today, {false} if after or equal today
     */
    public static boolean validateDateBefore(Date date) {
        return date.before(Calendar.getInstance().getTime());
    }

    /**
     * validate if date can be a birthday date. (means before today and max 150 years before)
     *
     * @param date the date to validate
     * @return {true} if date can be a birth date, {false} if not
     */
    public static boolean validateBirthday(Date date) {
        return Help.validateDateBefore(date) && Calendar.getInstance().getTime().getTime() - date.getTime() < 11826000000000L; //150 years in milli second
    }

    /**
     * validate if date is after birth date
     *
     * @param date the date to validate
     * @param p    the person with the birth date to compare
     * @return {true} if date is before birth date, {false} if after or equal birth date
     */
    public static boolean validateAfterBirthday(Date date, Person p) {
        return p.getBirthday().before(date);
    }

    /**
     * validate if string is an email address
     *
     * @param emailStr string to validate
     * @return {true} if is an email address, {false} if not
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * validate if string is an post code
     *
     * @param postalCodeStr string to validate
     * @return {true} if is an postal code, {false} if not
     */
    public static boolean validatePostalCode(String postalCodeStr) {
        Matcher matcher = VALID_POSTAL_CODE_REGEX.matcher(postalCodeStr);
        return matcher.find();
    }

    /**
     * validate if string is a room
     *
     * @param text string to validate
     * @return {true} if is a room, {false} if not
     */
    public static boolean validateRoom(String text) {
        return !text.contains(" ");
    }

    //----------------------------check if taken-------------------------

    /**
     * validate if studentNumber is not occupied
     *
     * @param studentNumber number to validate
     * @return {true} if number is free, {false} if not
     */
    public boolean checkSNContains(int studentNumber) {
        if (student_ids == null)
            student_ids = Database.getStudentIDs();
        return !student_ids.contains(studentNumber);
    }

    /**
     * validate if matriculationNumber is not occupied
     *
     * @param matriculationNumber number to validate
     * @return {true} if number is free, {false} if not
     */
    public boolean checkMNContains(int matriculationNumber) {
        if (matriculation_ids == null)
            matriculation_ids = Database.getMatriculationNumbers();
        return !matriculation_ids.contains(matriculationNumber);
    }

    /**
     * validate if docentNumber is not occupied
     *
     * @param docentNumber number to validate
     * @return {true} if number is free, {false} if not
     */
    public boolean checkDNContains(int docentNumber) {
        if (docent_ids == null)
            docent_ids = Database.getDocentIDs();
        return !docent_ids.contains(docentNumber);
    }

    //--------------------Converter-------------------

    /**
     * converting a Date to a LocalDate
     *
     * @param dateToConvert given Date to convert
     * @return LocalDate with the same value as the dateToConvert
     */
    public static LocalDate convertLocalDateDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    /**
     * converting a LocalDate to a Date
     *
     * @param dateToConvert given LocalDate to convert
     * @return Date with the same value as the dateToConvert
     */
    public static Date convertLocalDateDate(LocalDate dateToConvert) {
        return dateToConvert == null ? null : java.sql.Date.valueOf(dateToConvert);
    }

    //------------------------------gui function----------------------------------------

    /**
     * visualize the wrong fields
     *
     * @param focus   has any field requested focus
     * @param control control to mark visualized
     */
    public static void markWrongField(boolean focus, Control control) {
        control.setStyle(styleWrong);
        if (!focus)
            control.requestFocus();
    }

    /**
     * set error message if there is a wrong entry
     *
     * @param errorMessageL List of error messages
     * @param errorMessage  label where error message shut be shown
     */
    public static void setErrorMessage(List<String> errorMessageL, Label errorMessage) {
        StringBuilder sb = new StringBuilder(errorMessageL.remove(0));
        for (String s : errorMessageL)
            sb.append(", ").append(s);
        for (int f = 190; f < sb.length(); f += 190)
            sb.insert(f, "\n");
        errorMessage.setText(sb.toString());
        errorMessage.setVisible(true);
    }

    /**
     * adding KeyListener to DatePicker to validate the input if is it a date and set it
     *
     * @param datePicker the datepicker to add teh Listener
     */
    public static void addKeyEventDatePicker(DatePicker datePicker) {
//        datePicker.setOnKeyReleased(keyEvent -> {
//            String text = datePicker.getEditor().getText();
//            if (text.length() < 10 || text.split("\\.").length != 3) return;
//            try {
//                Date date = format.parse(text);
//                datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//            } catch (ParseException ignored) {
//            }
//        });

        datePicker.addEventFilter(KeyEvent.ANY, new EventHandler<>() {
            private boolean willConsume = false;

            @Override
            public void handle(KeyEvent event) {
                if (willConsume)
                    event.consume();

                if (event.isControlDown()) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED)
                        if (event.getCode() == KeyCode.V)
                            try {
                                String s = Clipboard.getSystemClipboard().getString();
                                format.parse(s);
                                datePicker.getEditor().setText(s);
                            } catch (ParseException ignored) {
                            } finally {
                                event.consume();
                            }
                } else if (!event.getCode().isNavigationKey() && !event.getCode().isDigitKey() && !(event.getCode() == KeyCode.PERIOD) && !(event.getCode() == KeyCode.TAB) && !(event.getCode() == KeyCode.DELETE) && !(event.getCode() == KeyCode.BACK_SPACE)) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED)
                        willConsume = true;
                    else if (event.getEventType() == KeyEvent.KEY_RELEASED)
                        willConsume = false;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    String text = datePicker.getEditor().getText();
                    if (text.length() < 10 || text.split("\\.").length != 3) return;
                    try {
                        Date date = format.parse(text);
                        datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    } catch (ParseException ignored) {
                    }
                }
            }
        });
    }

    /**
     * fill all text box with address attributes
     *
     * @param address  address to insert
     * @param street   's text box
     * @param number   's text box
     * @param postcode 's text box
     * @param city     's text box
     * @param county   's text box
     */
    public static void fillAddressesToTextField(Address address, TextField street, TextField number, TextField postcode, TextField city, TextField county) {
        if (address != null) {
            street.setText(address.getStreet());
            number.setText(address.getNumber());
            postcode.setText(address.getPostcode());
            city.setText(address.getCity());
            county.setText(address.getCountry());
        }
    }

    /**
     * fill all text boxes with person attributes
     *
     * @param person    person to insert
     * @param name      's text box
     * @param forename  's text box
     * @param birthday 's text box
     * @param email     's text box
     */
    public static void fillPersonToTextField(Person person, TextField name, TextField forename, DatePicker birthday, TextField email) {
        if (person != null) {
            name.setText(person.getName());
            forename.setText(person.getForename());
            email.setText(person.getEmail());
            if (person.getBirthday() != null)
                birthday.setValue(convertLocalDateDate(person.getBirthday()));
        }
    }

    /**
     * fill all text boxes with person attributes
     *
     * @param person   person to insert
     * @param name     's text box
     * @param forename 's text box
     * @param email    's text box
     */
    public static void fillPersonToTextField(Person person, TextField name, TextField forename, TextField email) {
        if (person != null) {
            name.setText(person.getName());
            forename.setText(person.getForename());
            email.setText(person.getEmail());
        }
    }

    /**
     * get resource bundle to get the translation of the string
     *
     * @return resource bundle
     */
    public static ResourceBundle getResourcedBundle() {
        if (locale == null) readLanguageSetting();
        return ResourceBundle.getBundle(lang_file_path, locale);
    }

    /**
     * get resource bundle to get the translation of the error strings
     *
     * @return resource bundle
     */
    public static ResourceBundle getResourcedBundleError() {
        if (locale == null) readLanguageSetting();
        return ResourceBundle.getBundle(lang_file_path_error, locale);
    }

    /**
     * toggle the languages
     */
    public static void toggleLocal() {
        for (int f = 0; f < supportedLanguage.length; f++) {
            if (supportedLanguage[f].equals(locale.getLanguage())) {
                locale = new Locale(supportedLanguage[(f + 1)%supportedLanguage.length]);
                setLanguageSetting();
                return;
            }
        }
    }

    /**
     * get the union student university email
     * @param student student who's email to return
     * @return conform email
     */
    public static String getStudentUniversityEmail(DualStudent student){
        return "s"+student.getStudentNumber()+email_end_student;
    }

    /**
     * get the union docent university email
     * @param docent docent who's email to return
     * @return conform email
     */
    public static String getDocentUniversityEmail(Docent docent){
        return "d"+docent.getDocentNumber()+email_end_docent;
    }

    //-------------------------Getter------------------------
    public static Locale getLocale() {
        if (locale == null)
            readLanguageSetting();
        return locale;
    }

    //------------------------------settings---------------------------------

    /**
     * read settings file and set location
     */
    private static void readLanguageSetting() {
        String lang = "de";
        try {
            FileReader reader = new FileReader(new File(settings_path));
            Properties props = new Properties();
            props.load(reader);

            lang = props.getProperty("lang");
            reader.close();
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }
        locale = new Locale(lang);
    }

    /**
     * set location to settings file
     */
    private static void setLanguageSetting() {
        try {
            Properties props = new Properties();
            props.setProperty("lang", locale.getLanguage());
            FileWriter writer = new FileWriter(new File(settings_path));
            props.store(writer, "settings");
            writer.close();
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }
    }
}
