package org.dhbw.classes;

import org.dhbw.help.Validation;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

    @Test
    public void validateDateBefore() {
        Calendar cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        assertTrue(Validation.validateDateBefore(cal.getTime()));

    }

    @Test
    public void validateBirthdate() {
        Calendar cal = Calendar.getInstance();
        cal.set(1989, Calendar.NOVEMBER, 9);
        assertTrue(Validation.validateBirthday(cal.getTime()));
    }

    @Test
    public void validateEmail() {
        assertTrue(Validation.validateEmail("karl.stroetmann@dhbw.mannheim.de"));
        assertFalse(Validation.validateEmail("dhbw.mannheim.de"));
        assertFalse(Validation.validateEmail("Nix is besser wie Lewwerwoschdebrood!"));
        assertTrue(Validation.validateEmail("user@128.0.0.1"));
    }

    @Test
    public void validatePostalCode() {
        assertTrue(Validation.validatePostalCode("64646"));
        assertFalse(Validation.validatePostalCode("-1"));

        // Poor Austrians with their four digit postal codes
        assertFalse(Validation.validatePostalCode("6992"));
    }
}