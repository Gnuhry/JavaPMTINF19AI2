package org.dhbw;

import java.io.IOException;
import java.util.Date;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dhbw.classes.*;

public class ShowStudentsController {

    @FXML
    private Label courseFilter;
    @FXML
    private Label companyFilter;
    @FXML
    private ComboBox courseFilterBox;
    @FXML
    private ComboBox companyFilterBox;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<DualStudent> studentTable;
    @FXML
    private TableColumn<DualStudent, Integer> studentNumber;
    @FXML
    private TableColumn<DualStudent, String> studentName;
    @FXML
    private TableColumn<DualStudent, String> studentForename;
    @FXML
    private TableColumn<DualStudent, Date> studentBirth;
    @FXML
    private TableColumn<DualStudent, String> studentEmail;
    @FXML
    private TableColumn<DualStudent, Address> studentStreetNumber;
    @FXML
    private TableColumn<DualStudent, String> studentPostalCodeCity;
    @FXML
    private TableColumn<DualStudent, String> studentCountry;
    @FXML
    private TableColumn<DualStudent, Integer> matriculationNumber;
    @FXML
    private TableColumn<DualStudent, Company> studentCompany;
    @FXML
    private TableColumn<DualStudent, Course> studentCourse;
    @FXML
    private TableColumn<DualStudent, Integer> studentJava;


    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    private ObservableList<DualStudent> data = FXCollections.observableArrayList(
            new DualStudent(1234567, 123456, "Wessely", "Silas", new Date(2000, 5,27), new Address("Oestricher Straße", "42", "65719", "Hofheim", "Deutschland"), "silas.wessely@gmx.de", new Course("TINF19AI2", StudyCourse.AInformatik, new Date(1965, 1,1)), 75, new Company("Alnatra", new Address("Test", "1", "12345", "Test", "Test"), new Person("Janina", "Hofmann")))
    );


    @FXML
    private void test() {
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<DualStudent, String>("name"));
        studentForename.setCellValueFactory(new PropertyValueFactory<>("forename"));
        studentBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentStreetNumber.setCellValueFactory(new PropertyValueFactory<>("Address"));
        studentPostalCodeCity.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentCountry.setCellValueFactory(new PropertyValueFactory<>("email"));
        matriculationNumber.setCellValueFactory(new PropertyValueFactory<>("matriculationNumber"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        studentCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentJava.setCellValueFactory(new PropertyValueFactory<>("javaKnowledge"));
        studentTable.setItems(data);
    }

}
