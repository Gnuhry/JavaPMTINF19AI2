package org.dhbw;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.dhbw.classes.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowStudentsController extends Application implements Initializable {
    private ObservableList<DualStudent> students = FXCollections.observableArrayList(
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
    );

    private String file;
    private Object object;
    private DualStudent student;
    private Docent lecture;
    private Company company;
    private Course course;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");


    @FXML
    private TextField searchBox;
    @FXML
    private ComboBox<Course> courseFilterBox;
    @FXML
    private ComboBox<Company> companyFilterBox;
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
    private TableColumn<Company, Void> companyC;
    @FXML
    private TableColumn<Company, Void> companyD;

    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void refresh() {
        studentTable.setItems(FXCollections.observableArrayList(University.getStudents()));
        studentTable.refresh();
        lectureTable.setItems(FXCollections.observableArrayList(University.getDocents()));
        lectureTable.refresh();
        courseTable.setItems(FXCollections.observableArrayList(University.getCourses()));
        courseTable.refresh();
        companyTable.setItems(FXCollections.observableArrayList(University.getCompanies()));
        companyTable.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentForename.setCellValueFactory(new PropertyValueFactory<>("forename"));
        studentBirth.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b) {
                    setText(null);
                } else {
                    this.setText(format.format(date));
                }
            }
        });
        studentBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        matriculationNumber.setCellValueFactory(new PropertyValueFactory<>("matriculationNumber"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        studentCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentJava.setCellValueFactory(new PropertyValueFactory<>("javaKnowledge"));
        studentC.setCellFactory(getCallback("editStudent", "editButton"));
        studentD.setCellFactory(getCallback("acceptDelete", "deleteButton"));
        studentTable.setItems(students);
        studentTable.requestFocus();
        courseFilterBox.getItems().setAll(courses);
        companyFilterBox.getItems().setAll(companies);
        FilteredList<DualStudent> filteredName = new FilteredList<>(students, p -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        companyFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        courseFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        SortedList<DualStudent> sortedName = new SortedList<>(filteredName);
        sortedName.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedName);

        lectureNumber.setCellValueFactory(new PropertyValueFactory<>("docentNumber"));
        lectureLastName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lectureFirstName.setCellValueFactory(new PropertyValueFactory<>("forename"));
        lectureBirth.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b) {
                    setText(null);
                } else {
                    this.setText(format.format(date));
                }
            }
        });
        lectureBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureC.setCellFactory(getCallback("editLecture", "editButton"));
        lectureD.setCellFactory(getCallback("acceptDelete", "deleteButton"));
        lectureTable.setItems(docents);

        FilteredList<Docent> filteredLecture = new FilteredList<>(docents, p -> true);
        searchBoxLecture.textProperty().addListener((observable, oldValue, newValue) -> filteredLecture.setPredicate(person -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (person.getForename().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return ("" + person.getDocentNumber()).toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Docent> sortedLecture = new SortedList<>(filteredLecture);
        sortedLecture.comparatorProperty().bind(lectureTable.comparatorProperty());
        lectureTable.setItems(sortedLecture);

        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseType.setCellValueFactory(new PropertyValueFactory<>("studyCourse"));
        courseRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        courseDate.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b) {
                    setText(null);
                } else {
                    this.setText(format.format(date));
                }
            }
        });
        courseDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        courseLecture.setCellValueFactory(new PropertyValueFactory<>("studyDirector"));
        courseC.setCellFactory(getCallback("editCourse", "editButton"));
        courseD.setCellFactory(getCallback("acceptDelete", "deleteButton"));
        courseTable.setItems(courses);

        FilteredList<Course> filteredCourse2 = new FilteredList<>(courses, p -> true);
        searchBoxCourse.textProperty().addListener((observable, oldValue, newValue) -> filteredCourse2.setPredicate(course -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return course.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Course> sortedCourses = new SortedList<>(filteredCourse2);
        sortedCourses.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(sortedCourses);

        companyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        companyPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        companyC.setCellFactory(getCallback("editCompany", "editButton"));
        companyD.setCellFactory(getCallback("acceptDelete", "deleteButton"));
        courseTable.setItems(courses);

        FilteredList<Company> filteredCompany2 = new FilteredList<>(companies, p -> true);
        searchBoxCompany.textProperty().addListener((observable, oldValue, newValue) -> filteredCompany2.setPredicate(course -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return course.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Company> sortedCompany2 = new SortedList<>(filteredCompany2);
        sortedCompany2.comparatorProperty().bind(companyTable.comparatorProperty());
        companyTable.setItems(sortedCompany2);
    }

    @FXML
    public Button addFunction(Button button, Object object, String file) {
        button.setOnAction((ActionEvent event) -> {
            try {
                if (object instanceof DualStudent) this.student = (DualStudent) object;
                else if (object instanceof Docent) this.lecture = (Docent) object;
                else if (object instanceof Company) this.company = (Company) object;
                else if (object instanceof Course) this.course = (Course) object;
                this.file = file;
                this.object = object;
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
        Parent root = fxmlLoader.load();
        if (file.equals("editStudent")) {
            EditStudentController controller = fxmlLoader.getController();
            controller.initVariables(student);
        } else if (file.equals("editLecture")) {
            EditLectureController controller = fxmlLoader.getController();
            controller.initVariables(lecture);
        } else if (file.equals("editCompany")) {
            EditCompanyController controller = fxmlLoader.getController();
            controller.initVariables(company);
        } else if (file.equals("editCourse")) {
            EditCourseController controller = fxmlLoader.getController();
            controller.initVariables(course);
        } else if (object instanceof DualStudent) {
            AcceptDeleteController controller = fxmlLoader.getController();
            controller.initVariables(student);
        } else if (object instanceof Docent) {
            AcceptDeleteController controller = fxmlLoader.getController();
            controller.initVariables(lecture);
        } else if (object instanceof Company) {
            AcceptDeleteController controller = fxmlLoader.getController();
            controller.initVariables(company);
        } else if (object instanceof Course) {
            AcceptDeleteController controller = fxmlLoader.getController();
            controller.initVariables(course);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHidden(windowEvent -> refresh());
        stage.initOwner(studentTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private <T> Callback<TableColumn<T, Void>, TableCell<T, Void>> getCallback(String function, String image) {
        return new Callback<>() {
            @Override
            public TableCell<T, Void> call(TableColumn<T, Void> dualStudentVoidTableColumn) {
                return new TableCell<>() {
                    Button btn = new Button();

                    @Override
                    protected void updateItem(Void aVoid, boolean b) {
                        super.updateItem(aVoid, b);
                        if (b) {
                            setGraphic(null);
                        } else {
                            btn = addFunction(btn, getTableView().getItems().get(getIndex()), function);
                            btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/" + image + ".png"))));
                            setGraphic(btn);

                        }
                    }
                };
            }
        };
    }

    private boolean checkFilterStudent(Object newValue, DualStudent person) {
        boolean erg = true;
        String search;
        if (newValue instanceof String)
            search = ((String) newValue);
        else
            search = searchBox.getText();
        if (search != null && !search.isEmpty()) {
            search = search.trim().toLowerCase();
            if (person.getForename().toLowerCase().contains(search))
                erg = true;
            else if (person.getName().toLowerCase().contains(search))
                erg = true;
            else if (("" + person.getStudentNumber()).toLowerCase().contains(search))
                erg = true;
            else
                erg = ("" + person.getMatriculationNumber()).toLowerCase().contains(search);
        }
        Company company;
        if (newValue instanceof Company)
            company = (Company) newValue;
        else
            company = companyFilterBox.getValue();
        if (company != null)
            erg &= company.equals(person.getCompany());

        Course course;
        if (newValue instanceof Course)
            course = (Course) newValue;
        else
            course = courseFilterBox.getValue();
        if (course != null)
            erg &= course.equals(person.getCourse());
        return erg;
    }


}
