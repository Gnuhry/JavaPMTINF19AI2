package org.dhbw.classes;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class HelpTest {

    @Test
    public void validateDateBefore() {
        var cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        var date = cal.getTime();
        assertTrue(Help.validateDateBefore(date));

    }

    @Test
    public void validateBirthdate() {
        var cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        var date = cal.getTime();
        assertTrue(Help.validateBirthdate(date));
    }

    @Test
    public void validateAfterBirthday() {
        var p = new Person("Mustermann", "Max", new Date(), null, null);
        var cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        var date = cal.getTime();
        assertFalse(Help.validateAfterBirthday(date, p));
        assertTrue(Help.validateAfterBirthday(new Date(), p));
    }

    @Test
    public void validateEmail() {
        assertTrue(Help.validateEmail("karl.stroetmann@dhbw.mannheim.de"));
        assertFalse(Help.validateEmail("dhbw.mannheim.de"));
        assertFalse(Help.validateEmail("Nix is besser wie Lewwerwoschdebrood!"));
        assertTrue(Help.validateEmail("user@128.0.0.1"));
    }

    @Test
    public void validatePostalCode() {
        assertTrue(Help.validatePostalCode("64646"));
        assertFalse(Help.validatePostalCode("-1"));

        // Poor Austrians with their four digit postal codes
        assertFalse(Help.validatePostalCode("6992"));
    }

//    @Test
//    void validateRoom() {
//        // TODO: Implement test when room class is done
//    }

//    @Test
//    void checkSNContains() {
//        // TODO: Make sure, there are some test cases available
//        // -> Prepare database
//    }

//    @Test
//    void checkMNContains() {
//        // TODO: Make sure, there are some test cases available
//        // -> Prepare database
//    }
//
//    @Test
//    void checkDNContains() {
//        // TODO: Make sure, there are some test cases available
//        // -> Prepare database
//    }


    // TODO: Discuss automatic testing of UI components
//    @Test
//    void markWrongField() {
//    }
//
//    @Test
//    void setErrorMessage() {
//    }
//
//    @Test
//    void addKeyEventDatePicker() {
//    }
//
//    @Test
//    void fillAddressesToTextField() {
//    }
//
//    @Test
//    void fillPersonToTextField() {
//    }
//
//    @Test
//    void testFillPersonToTextField() {
//    }
//
    @Test
    public void getRessourceBundle() {
        assertNotNull(Help.getRessourceBundle());
    }

    @Test
    public void getRessourceBundleError() {
        assertNotNull(Help.getRessourceBundleError());
    }

    @Test
    public void toggleLocal() {
        assertEquals(Help.getLocale().getLanguage(), "de");
        Help.toggleLocal();
        assertEquals(Help.getLocale().getLanguage(), "en");
        Help.toggleLocal();
        assertEquals(Help.getLocale().getLanguage(), "de");
    }
}