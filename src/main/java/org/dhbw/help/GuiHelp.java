package org.dhbw.help;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.dhbw.classes.Address;
import org.dhbw.classes.Docent;
import org.dhbw.classes.DualStudent;
import org.dhbw.classes.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class GuiHelp {
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    public static final String styleRight = "-fx-text-fill: -fx-text-base-color; -fx-border-color: rgba(0,0,0,0) rgba(0,0,0,0) rgb(0, 0, 0) rgba(0,0,0,0)";
    public static final String logoPath = "/org/dhbw/controller/images/dhbwLogoSquare.png";
    private static final String styleWrong = "-fx-text-fill: darkred; -fx-border-color: darkred";
    private static final String email_end_student = "@student.dhbw-mannheim.de";
    private static final String email_end_docent = "@docent.dhbw-mannheim.de";

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
     * @param person   person to insert
     * @param name     's text box
     * @param forename 's text box
     * @param birthday 's text box
     * @param email    's text box
     */
    public static void fillPersonToTextField(Person person, TextField name, TextField forename, DatePicker birthday, TextField email) {
        if (person != null) {
            name.setText(person.getName());
            forename.setText(person.getForename());
            email.setText(person.getEmail());
            if (person.getBirthday() != null)
                birthday.setValue(DateConverter.convertLocalDateDate(person.getBirthday()));
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
     * get the union student university email
     *
     * @param student student who's email to return
     * @return conform email
     */
    public static String getStudentUniversityEmail(DualStudent student) {
        return "s" + student.getStudentNumber() + email_end_student;
    }

    /**
     * get the union docent university email
     *
     * @param docent docent who's email to return
     * @return conform email
     */
    public static String getDocentUniversityEmail(Docent docent) {
        return "d" + docent.getDocentNumber() + email_end_docent;
    }
}
