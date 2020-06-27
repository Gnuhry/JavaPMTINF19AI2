package org.dhbw.classes;

import org.dhbw.help.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanguageTest {

    @Test
    public void getRessourceBundle() {
        assertNotNull(Language.getResourcedBundle());
    }

    @Test
    public void getRessourceBundleError() {
        assertNotNull(Language.getResourcedBundleError());
    }

    @Test
    public void toggleLocal() {
        assertEquals(Language.getLocale().getLanguage(), "de");
        Language.toggleLocal();
        assertEquals(Language.getLocale().getLanguage(), "en");
        Language.toggleLocal();
        assertEquals(Language.getLocale().getLanguage(), "de");
    }
}