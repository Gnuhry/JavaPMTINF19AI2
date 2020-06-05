package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dhbw.classes.Company;
import org.dhbw.classes.DualStudent;
import org.dhbw.classes.University;

public class AcceptDeleteCompanyController {

    private Company company;

    @FXML
    private Button cancelButton;

    public void initVariables(Company company) {
        this.company = company;
    }

    @FXML
    private void acceptDelete() {
        University.removeCompany(company);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
