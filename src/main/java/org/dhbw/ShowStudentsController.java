package org.dhbw;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kotlin.time.AbstractDoubleTimeSource;
import org.dhbw.classes.*;

import javax.security.auth.callback.Callback;

public class ShowStudentsController implements Initializable {
    private ObservableList<DualStudent> data = FXCollections.observableArrayList(
            //new DualStudent(123456, 1234567, "Silas", "Wessely", new Date(100, 5,27), new Address("Birkenauer Straße", "51", "68309", "Mannheim", "Deutschland"), "silas.wessely@gmx.de", new Course("TINF19AI2", StudyCourse.AInformatik, new Date(119, Calendar.OCTOBER, 1)), 75, new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Hofmann", "Janina"))).
            University.getStudents()
    );
    private ObservableList<Course> filterCourseOptions = FXCollections.observableArrayList(
            University.getCourses()
            //new Course("TINF19AI2", StudyCourse.AInformatik, new Date(119, Calendar.OCTOBER, 1))
    );
    private ObservableList<Company> filterCompanyOptions = FXCollections.observableArrayList(
            new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Hofmann", "Janina", ""))
    );


    @FXML
    private Label courseFilter;
    @FXML
    private Label companyFilter;
    @FXML
    private Label groupFilter;
    @FXML
    private TextField searchBox;
    @FXML
    private ComboBox<Course> courseFilterBox;
    @FXML
    private ComboBox<Company> companyFilterBox;
    @FXML
    private ComboBox<String> groupFilterBox;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<DualStudent> studentTable;
    @FXML
    private TableColumn<DualStudent, Integer> studentNumber;
    @FXML
    private TableColumn<DualStudent, String> studentForename;
    @FXML
    private TableColumn<DualStudent, String> studentName;
    @FXML
    private TableColumn<DualStudent, Date> studentBirth;
    @FXML
    private TableColumn<DualStudent, String> studentEmail;
    @FXML
    private TableColumn<DualStudent, Address> studentAddress;
    @FXML
    private TableColumn<DualStudent, Integer> matriculationNumber;
    @FXML
    private TableColumn<DualStudent, Company> studentCompany;
    @FXML
    private TableColumn<DualStudent, Course> studentCourse;
    @FXML
    private TableColumn<DualStudent, Integer> studentJava;
    @FXML
    private TableColumn<DualStudent, Void> studentC;
    @FXML
    private TableColumn<DualStudent, Void> studentD;


    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void filter() {
        courseFilter.setText(courseFilterBox.getEditor().getText());
        courseFilter.setVisible(true);
        companyFilter.setText(companyFilterBox.getEditor().getText());
        companyFilter.setVisible(true);
        groupFilter.setText(groupFilterBox.getEditor().getText());
        groupFilter.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentForename.setCellValueFactory(new PropertyValueFactory<>("forename"));
        studentBirth.setCellFactory(column -> {
            TableCell<DualStudent, Date> cell = new TableCell<>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date date, boolean b) {
                    super.updateItem(date, b);
                    if (b) {
                        setText(null);
                    } else {
                        this.setText(format.format(date));
                    }
                }
            };
            return cell;
        });
        studentBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        matriculationNumber.setCellValueFactory(new PropertyValueFactory<>("matriculationNumber"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        studentCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentJava.setCellValueFactory(new PropertyValueFactory<>("javaKnowledge"));
        studentC.setCellValueFactory(new PropertyValueFactory<>("changeButton"));
        studentD.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        studentTable.setItems(data);

        courseFilterBox.getItems().setAll(filterCourseOptions);
        System.out.println(filterCompanyOptions);
        companyFilterBox.getItems().setAll(filterCompanyOptions);
        groupFilterBox.getItems().setAll("Studierende", "Dozenten", "Kurse", "Unternehmen");

        FilteredList<DualStudent> filteredData = new FilteredList<>(data, p -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getForename().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<DualStudent> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedData);

    }
}
