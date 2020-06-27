package org.dhbw.classes;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class HelpTest {

    @Test
    public void validateDateBefore() {
        Calendar cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        assertTrue(Help.validateDateBefore(cal.getTime()));

    }

    @Test
    public void validateBirthdate() {
        Calendar cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        assertTrue(Help.validateBirthday(cal.getTime()));
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

    @Test
    public void getRessourceBundle() {
        assertNotNull(Help.getResourcedBundle());
    }

    @Test
    public void getRessourceBundleError() {
        assertNotNull(Help.getResourcedBundleError());
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