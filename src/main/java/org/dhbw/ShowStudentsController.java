package org.dhbw;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dhbw.classes.*;

public class ShowStudentsController extends Application implements Initializable {
    private ObservableList<DualStudent> students = FXCollections.observableArrayList(
            //new DualStudent(123456, 1234567, "Silas", "Wessely", new Date(100, 5,27), new Address("Birkenauer Straße", "51", "68309", "Mannheim", "Deutschland"), "silas.wessely@gmx.de", new Course("TINF19AI2", StudyCourse.AInformatik, new Date(119, Calendar.OCTOBER, 1)), 75, new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Hofmann", "Janina"))).
            University.getStudents()
    );
    private ObservableList<Docent> docents = FXCollections.observableArrayList(
            University.getDocents()
    );
    private ObservableList<Course> courses = FXCollections.observableArrayList(
            University.getCourses()
    );
    private ObservableList<Company> companies = FXCollections.observableArrayList(
            University.getCompanies()
            //new Company("Alnatura", new Address("Test", "1", "12345", "Test", "Test"), new Person("Hofmann", "Janina", ""))
    );

    private static Scene scene;
    private String file;
    private DualStudent student;
    private Docent lecture;
    private Company company;
    private Course course;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @FXML
    private Label courseFilter;
    @FXML
    private Label companyFilter;
    @FXML
    private TextField searchBox;
    @FXML
    private ComboBox<Course> courseFilterBox;
    @FXML
    private ComboBox<Company> companyFilterBox;
    @FXML
    private Button filterButton;
    @FXML
    public TableView<DualStudent> studentTable;
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
    private TextField searchBoxLecture;
    @FXML
    private TableView<Docent> lectureTable;
    @FXML
    private TableColumn<Docent, Integer> lectureNumber;
    @FXML
    private TableColumn<Docent, String> lectureFirstName;
    @FXML
    private TableColumn<Docent, String> lectureLastName;
    @FXML
    private TableColumn<Docent, Date> lectureBirth;
    @FXML
    private TableColumn<Docent, String> lectureEmail;
    @FXML
    private TableColumn<Docent, Address> lectureAddress;
    @FXML
    private TableColumn<Docent, Void> lectureC;
    @FXML
    private TableColumn<Docent, Void> lectureD;

    @FXML
    private TextField searchBoxCourse;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseName;
    @FXML
    private TableColumn<Course, StudyCourse> courseType;
    @FXML
    private TableColumn<Course, CourseRoom> courseRoom;
    @FXML
    private TableColumn<Course, Date> courseDate;
    @FXML
    private TableColumn<Course, Docent> courseLecture;
    @FXML
    private TableColumn<Course, Void> courseC;
    @FXML
    private TableColumn<Course, Void> courseD;

    @FXML
    private TextField searchBoxCompany;
    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company, String> companyName;
    @FXML
    private TableColumn<Company, Address> companyAddress;
    @FXML
    private TableColumn<Company, Person> companyPerson;
    @FXML
    private TableColumn<Course, Void> companyC;
    @FXML
    private TableColumn<Course, Void> companyD;

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void filter() {
        if (courseFilterBox.getValue() != null) {
            courseFilter.setText(courseFilterBox.getValue().getName());
            courseFilter.setVisible(true);
        }
        if (companyFilterBox.getValue() != null) {
            companyFilter.setText(companyFilterBox.getValue().getName());
            companyFilter.setVisible(true);
        }
    }

    @FXML
    public void refresh() {
        ObservableList<DualStudent> data2 = FXCollections.observableArrayList(
                University.getStudents()
        );
        studentTable.setItems(data2);
    }

    @FXML
    private void refreshLecture() {
        ObservableList<Docent> docents2 = FXCollections.observableArrayList(
                University.getDocents()
        );
        lectureTable.setItems(docents2);
    }

    @FXML
    private void refreshCourse() {
        ObservableList<Course> courses2 = FXCollections.observableArrayList(
                University.getCourses()
        );
        courseTable.setItems(courses2);
    }

    @FXML
    private void refreshCompany() {
        ObservableList<Company> companies2 = FXCollections.observableArrayList(
                University.getCompanies()
        );
        System.out.println("Ausgabe");
        companyTable.setItems(companies2);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentForename.setCellValueFactory(new PropertyValueFactory<>("forename"));
        studentBirth.setCellFactory(column -> {
            TableCell<DualStudent, Date> cell = new TableCell<>() {
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
        studentTable.setItems(students);

        courseFilterBox.getItems().setAll(courses);
        companyFilterBox.getItems().setAll(companies);

        FilteredList<DualStudent> filteredName = new FilteredList<>(students, p -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredName.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getForename().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (("" + person.getStudentNumber()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (("" + person.getMatriculationNumber()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<DualStudent> sortedName = new SortedList<>(filteredName);
        sortedName.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedName);

        lectureNumber.setCellValueFactory(new PropertyValueFactory<>("docentNumber"));
        lectureLastName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lectureFirstName.setCellValueFactory(new PropertyValueFactory<>("forename"));
        lectureBirth.setCellFactory(column -> {
            TableCell<Docent, Date> cell = new TableCell<>() {
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
        lectureBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureC.setCellValueFactory(new PropertyValueFactory<>("changeButton"));
        lectureD.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        lectureTable.setItems(docents);

        FilteredList<Docent> filteredLecture = new FilteredList<>(docents, p -> true);
        searchBoxLecture.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredLecture.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getForename().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (("" + person.getDocentNumber()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Docent> sortedLecture = new SortedList<>(filteredLecture);
        sortedLecture.comparatorProperty().bind(lectureTable.comparatorProperty());
        lectureTable.setItems(sortedLecture);

        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseType.setCellValueFactory(new PropertyValueFactory<>("studyCourse"));
        courseRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        courseDate.setCellFactory(column -> {
            TableCell<Course, Date> cell = new TableCell<>() {
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
        courseDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        courseLecture.setCellValueFactory(new PropertyValueFactory<>("studyDirector"));
        courseC.setCellValueFactory(new PropertyValueFactory<>("changeButton"));
        courseD.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        courseTable.setItems(courses);

        FilteredList<Course> filteredCourse2 = new FilteredList<>(courses, p -> true);
        searchBoxCourse.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCourse2.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (course.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Course> sortedCourses = new SortedList<>(filteredCourse2);
        sortedCourses.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(sortedCourses);

        companyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        companyPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        companyC.setCellValueFactory(new PropertyValueFactory<>("changeButton"));
        companyD.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        courseTable.setItems(courses);

        FilteredList<Company> filteredCompany2 = new FilteredList<>(companies, p -> true);
        searchBoxCompany.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCompany2.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (course.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Company> sortedCompany2 = new SortedList<>(filteredCompany2);
        sortedCompany2.comparatorProperty().bind(companyTable.comparatorProperty());
        companyTable.setItems(sortedCompany2);
    }

    @FXML
    public Button addFunction(Button button, Object object, String file) throws IOException {
        button.setOnAction((ActionEvent event) -> {
            try {
                if (file.equals("editStudent") || file.equals("acceptDeleteStudent")) this.student = (DualStudent)object;
                else if (file.equals("editLecture")) this.lecture = (Docent)object;
                else if (file.equals("editCompany")) this.company = (Company)object;
                else if (file.equals("editCourse")) this.course = (Course)object;
                this.file = file;
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    @Override
    public void start(Stage stage) throws Exception {
        String resourcePath = file + ".fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = (Parent)fxmlLoader.load();
        if (file.equals("editStudent")) {
            EditStudentController controller = fxmlLoader.<EditStudentController>getController();
            controller.initVariables(student);
        } else if (file.equals("editLecture")) {
            EditLectureController controller = fxmlLoader.<EditLectureController>getController();
            controller.initVariables(lecture);
        } else if (file.equals("editCompany")) {
            EditCompanyController controller = fxmlLoader.<EditCompanyController>getController();
            controller.initVariables(company);
        } else if (file.equals("editCourse")) {
            EditCourseController controller = fxmlLoader.<EditCourseController>getController();
            controller.initVariables(course);
        } else if (file.equals("acceptDeleteStudent")) {
            AcceptDeleteStudentController controller = fxmlLoader.getController();
            //controller.initVariables(student);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
