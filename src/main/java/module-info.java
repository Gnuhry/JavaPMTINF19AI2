module org.dhbw {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;
    opens org.dhbw to javafx.fxml;
    opens org.dhbw.classes to javafx.base;
    opens org.dhbw.controller to javafx.fxml;
    opens org.dhbw.help to javafx.base;
    exports org.dhbw.classes;
    exports org.dhbw.controller;
    exports org.dhbw.help;
    exports org.dhbw;
}