package org.dhbw.help;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Language {
    private static final String lang_file_path = "org.dhbw.lang.strings";
    private static final String lang_file_path_error = "org.dhbw.lang.error";
    private static final String settings_path = "settings.properties";
    private static final String[] supportedLanguage = new String[]{"de", "en"};
    private static Locale locale;

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
                locale = new Locale(supportedLanguage[(f + 1) % supportedLanguage.length]);
                setLanguageSetting();
                return;
            }
        }
    }

    //-------------------------Getter------------------------
    public static Locale getLocale() {
        if (locale == null)
            readLanguageSetting();
        return locale;
    }

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
