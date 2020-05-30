package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.dhbw.classes.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertCourseController {

    @FXML
    private TextField courseName;
    @FXML
    private ComboBox courseType;
    @FXML
    private ComboBox courseRoom;
    @FXML
    private DatePicker courseDate;
    @FXML
    private ComboBox courseRepresent;
    @FXML
    private ComboBox courseDirector;


    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void submit() {

        LocalDate localDateCourseBirth = courseDate.getValue();
        Instant instantCourseBirth = Instant.from(localDateCourseBirth.atStartOfDay(ZoneId.systemDefault()));
        Date courseRDate = Date.from(instantCourseBirth);

        Course course = new Course(
                courseName.getText(),
                (StudyCourse)courseType.getValue(),
                new Docent("Dozentname", "Dozentvorname", new Date(2000, 05,27), new Address("s", "2", "2", "s", "s")),//courseDirector.getAccessibleText(),             Combobox -> Dozent
                10,//courseRepresent.getAccessibleText(),            Combodbox -> KurssprecherID (int)
                courseRDate,
                (CourseRoom)courseRoom.getValue()
        );

        System.out.println(course);
    }

}
