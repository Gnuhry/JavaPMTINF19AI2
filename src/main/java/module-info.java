module org.dhbw {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    opens org.dhbw to javafx.fxml;
    opens org.dhbw.classes to javafx.base;
    exports org.dhbw.classes;
    exports org.dhbw;
}