module org.dhbw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.dhbw to javafx.fxml;
    exports org.dhbw;
}