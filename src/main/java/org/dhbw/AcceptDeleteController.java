package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.*;

import java.util.List;

public class AcceptDeleteController {

    private List<Object> object;

    @FXML
    private Button cancelButton;

    /**
     * initializing student, lecture, course or company with object value; the choose which object gets initialized depends on the type of the over given object
     */
    public void initVariables(List<Object> object) {
        this.object = object;
    }

    /**
     * deleting the object from the database; the choose which object gets initialized depends on the type of the over given object
     */
    @FXML
    private void acceptDelete() {
        for (Object o : object)
            if (o instanceof DualStudent)
                University.removeStudent((DualStudent) o);
            else if (o instanceof Docent)
                University.removeDocent((Docent) o);
            else if (o instanceof Course)
                University.removeCourse((Course) o);
            else if (o instanceof Company)
                University.removeCompany((Company) o);
        cancel();
    }

    /**
     * closing the stage / window
     */
    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
