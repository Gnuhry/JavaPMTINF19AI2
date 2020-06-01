module org.dhbw {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    opens org.dhbw to javafx.fxml;
    opens org.dhbw.classes to javafx.base;
    exports org.dhbw;
}