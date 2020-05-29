package org.dhbw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class InsertStudentController {

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextField studentNumberField;

    @FXML
    private void generateSN() throws IOException {
        String studentNumber = "s" + (100000+(int)(Math.random()*999999));
        studentNumberField.setText(studentNumber);
    }

    @FXML
    private TextField matriculationNumberField;

    @FXML
    private void generateMN() throws IOException {
        String matriculationNumber = "" + (1000000+(int)(Math.random()*9999999));
        matriculationNumberField.setText(matriculationNumber);
    }

    @FXML
    private Label javaKnowledgeLabel;
    @FXML
    private Slider javaKnowledgeSlider;

    @FXML
    private void showSlider() throws IOException {
        javaKnowledgeLabel.setText("" + (int)javaKnowledgeSlider.getValue());
    }

    @FXML
    private VBox cCompany;

    @FXML
    private void createCompany() throws IOException {
        cCompany.setVisible(true);
    }

    @FXML
    private HBox companyPerson;

    @FXML
    private void createCompanyPerson() throws IOException {
        companyPerson.setVisible(true);
    }


}
