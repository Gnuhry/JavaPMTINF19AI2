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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.dhbw.classes.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowController extends Application implements Initializable {
    private final ObservableList<DualStudent> students = FXCollections.observableArrayList(
            University.getStudents()
    );
    private final ObservableList<Docent> docents = FXCollections.observableArrayList(
            University.getDocents()
    );
    private final ObservableList<Course> courses = FXCollections.observableArrayList(
            University.getCourses()
    );
    private final ObservableList<Company> companies = FXCollections.observableArrayList(
            University.getCompanies()
    );
    private final ObservableList<CourseRoom> rooms = FXCollections.observableArrayList(
            University.getRooms()
    );
    private final ObservableList<Campus> campuses = FXCollections.observableArrayList(
            Campus.values()
    );

    private FileType file;
    private List<Object> object;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private final Company allCompany = new Company(Help.getRessourceBundle().getString("all_company"), null, null);
    private final Course allCourse = new Course(Help.getRessourceBundle().getString("all_course"), null, null);

    @FXML
    private AnchorPane studentAnchor, lectureAnchor, courseAnchor, companyAnchor, roomAnchor;
    @FXML
    private TextField searchBox, searchBoxLecture, searchBoxCourse, searchBoxCompany, searchBoxRoom;
    @FXML
    private ComboBox<Course> courseFilterBox;
    @FXML
    private ComboBox<Company> companyFilterBox;
    @FXML
    private ComboBox<Campus> campusFilterBox;
    @FXML
    public TableView<DualStudent> studentTable;
    @FXML
    private TableColumn<DualStudent, Integer> studentNumber, matriculationNumber, studentJava;
    @FXML
    private TableColumn<DualStudent, String> studentForename, studentName, studentEmail;
    @FXML
    private TableColumn<DualStudent, Date> studentBirth;
    @FXML
    private TableColumn<DualStudent, Address> studentAddress;
    @FXML
    private TableColumn<DualStudent, Company> studentCompany;
    @FXML
    private TableColumn<DualStudent, Course> studentCourse;
    @FXML
    private TableColumn<DualStudent, Void> studentC, studentD;

    @FXML
    private TableView<Docent> lectureTable;
    @FXML
    private TableColumn<Docent, Integer> lectureNumber;
    @FXML
    private TableColumn<Docent, String> lectureFirstName, lectureLastName, lectureEmail;
    @FXML
    private TableColumn<Docent, Date> lectureBirth;
    @FXML
    private TableColumn<Docent, Address> lectureAddress;
    @FXML
    private TableColumn<Docent, Void> lectureC, lectureD;

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
    private TableColumn<Course, Void> courseC, courseD, courseMail;

    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company, String> companyName;
    @FXML
    private TableColumn<Company, Address> companyAddress;
    @FXML
    private TableColumn<Company, Person> companyPerson;
    @FXML
    private TableColumn<Company, Void> companyC, companyD;

    @FXML
    private TableView<CourseRoom> roomTable;
    @FXML
    private TableColumn<CourseRoom, String> roomName;
    @FXML
    private TableColumn<CourseRoom, String> roomCampus;
    @FXML
    private TableColumn<CourseRoom, String> roomBuilding;
    @FXML
    private TableColumn<CourseRoom, String> roomFloor;
    @FXML
    private TableColumn<CourseRoom, Integer> roomSeats;
    @FXML
    private TableColumn<CourseRoom, Boolean> roomBeamer;
    @FXML
    private TableColumn<CourseRoom, Boolean> roomDocumentCamera;
    @FXML
    private TableColumn<CourseRoom, Boolean> roomLaboratory;
    @FXML
    private TableColumn<CourseRoom, Void> roomC;
    @FXML
    private TableColumn<CourseRoom, Void> roomD;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    /**
     * refreshing all tables
     */
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
        roomTable.setItems(FXCollections.observableArrayList(University.getRooms()));
        roomTable.refresh();
    }

    /**
     * initializing every column of the table with the data from other classes
     * adding filter functions for lecture table, course table and company table (input from search boxes)
     * creating contextmenu and adding functions to buttons and adding delete key
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu refreshMenu = new ContextMenu();
        MenuItem[] item = new MenuItem[]{new MenuItem(Help.getRessourceBundle().getString("refresh")), new MenuItem(Help.getRessourceBundle().getString("back"))};
        item[0].setOnAction(actionEvent -> refresh());
        item[1].setOnAction(actionEvent -> {
            try {
                backToOverview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        refreshMenu.getItems().addAll(item);

        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentForename.setCellValueFactory(new PropertyValueFactory<>("forename"));
        studentBirth.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b)
                    this.setText(null);
                else
                    this.setText(format.format(date));
            }
        });
        studentBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
//        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentEmail.setCellFactory(dualStudentVoidTableColumn -> {
            TableCell<DualStudent, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty())
                    getHostServices().showDocument("mailto:" + cell.getTableView().getItems().get(cell.getIndex()).getEmail());
            });
            return cell;
        });
        studentAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        matriculationNumber.setCellValueFactory(new PropertyValueFactory<>("matriculationNumber"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        studentCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentJava.setCellValueFactory(new PropertyValueFactory<>("javaKnowledge"));
        studentC.setCellFactory(getCallback(FileType.editStudents));
        studentD.setCellFactory(getCallback(FileType.delete));
        studentTable.setItems(students);
        studentTable.requestFocus();

        List<Course> courseList = new ArrayList<>();
        courseList.add(allCourse);
        courseList.addAll(courses);
        courseFilterBox.getItems().setAll(courseList);
        courseFilterBox.setValue(allCourse);

        List<Company> companyList = new ArrayList<>();
        companyList.add(allCompany);
        companyList.addAll(companies);
        companyFilterBox.getItems().setAll(companyList);
        companyFilterBox.setValue(allCompany);

        FilteredList<DualStudent> filteredName = new FilteredList<>(students, p -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        companyFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        courseFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredName.setPredicate(person -> checkFilterStudent(newValue, person)));
        SortedList<DualStudent> sortedName = new SortedList<>(filteredName);
        sortedName.comparatorProperty().bind(studentTable.comparatorProperty());

        studentTable.setItems(sortedName);
        studentTable.setContextMenu(refreshMenu);
        studentTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addDeleteKey(studentAnchor, studentTable);

        lectureNumber.setCellValueFactory(new PropertyValueFactory<>("docentNumber"));
        lectureLastName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lectureFirstName.setCellValueFactory(new PropertyValueFactory<>("forename"));
        lectureBirth.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b || date == null)
                    setText(null);
                else
                    this.setText(format.format(date));
            }
        });
        lectureBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        lectureEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lectureC.setCellFactory(getCallback(FileType.editDocents));
        lectureD.setCellFactory(getCallback(FileType.delete));

        lectureTable.setItems(docents);
        lectureTable.setContextMenu(refreshMenu);
        lectureTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addDeleteKey(lectureAnchor, lectureTable);

        FilteredList<Docent> filteredLecture = new FilteredList<>(docents, p -> true);
        searchBoxLecture.textProperty().addListener((observable, oldValue, newValue) -> filteredLecture.setPredicate(person -> {
            if (newValue == null || newValue.isEmpty())
                return true;
            String lowerCaseFilter = newValue.toLowerCase();
            return person.getForename().toLowerCase().contains(lowerCaseFilter) || (person.getName().toLowerCase().contains(lowerCaseFilter) || String.valueOf(person.getDocentNumber()).toLowerCase().contains(lowerCaseFilter));
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
                if (b)
                    setText(null);
                else
                    this.setText(format.format(date));
            }
        });
        courseDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        courseLecture.setCellValueFactory(new PropertyValueFactory<>("studyDirector"));
        courseC.setCellFactory(getCallback(FileType.editCourse));
        courseD.setCellFactory(getCallback(FileType.delete));
        courseMail.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Course, Void> call(TableColumn<Course, Void> courseVoidTableColumn) {
                return new TableCell<>() {
                    final Button btn = new Button();

                    @Override
                    protected void updateItem(Void aVoid, boolean b) {
                        super.updateItem(aVoid, b);
                        if (b)
                            setGraphic(null);
                        else {
                            btn.setOnAction(actionEvent -> {
                                StringBuilder sb = new StringBuilder("mailto:");
                                String[] mails = University.getAllEmailFromCourse(getTableView().getItems().get(getIndex()));
                                if (mails == null) return;
                                for (String s : mails)
                                    sb.append(s).append(", ");
                                getHostServices().showDocument(sb.toString().substring(0, sb.toString().length() - 2));
                            });
                            btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/mailButton.png"))));
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        courseTable.setItems(courses);
        courseTable.setContextMenu(refreshMenu);
        courseTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addDeleteKey(courseAnchor, courseTable);

        FilteredList<Course> filteredCourse2 = new FilteredList<>(courses, p -> true);
        searchBoxCourse.textProperty().addListener((observable, oldValue, newValue) -> filteredCourse2.setPredicate(course -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return (course.getName().toLowerCase().contains(lowerCaseFilter)) || (course.getStudyCourse().toString().toLowerCase().contains(lowerCaseFilter));
        }));
        SortedList<Course> sortedCourses = new SortedList<>(filteredCourse2);
        sortedCourses.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(sortedCourses);

        companyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        companyPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        companyC.setCellFactory(getCallback(FileType.editCompany));
        companyD.setCellFactory(getCallback(FileType.delete));
        courseTable.setItems(courses);

        FilteredList<Company> filteredCompany2 = new FilteredList<>(companies, p -> true);
        searchBoxCompany.textProperty().addListener((observable, oldValue, newValue) -> filteredCompany2.setPredicate(company -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return company.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Company> sortedCompany2 = new SortedList<>(filteredCompany2);
        sortedCompany2.comparatorProperty().bind(companyTable.comparatorProperty());
        companyTable.setItems(sortedCompany2);
        companyTable.setContextMenu(refreshMenu);
        companyTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addDeleteKey(companyAnchor, companyTable);

        roomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomCampus.setCellValueFactory(new PropertyValueFactory<>("campus"));
        roomBuilding.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        roomSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        roomBeamer.setCellValueFactory(new PropertyValueFactory<>("beamer"));
        roomDocumentCamera.setCellValueFactory(new PropertyValueFactory<>("documentCamera"));
        roomLaboratory.setCellValueFactory(new PropertyValueFactory<>("laboratory"));
        roomC.setCellFactory(getCallback(FileType.editRoom));
        roomD.setCellFactory(getCallback(FileType.delete));
        roomTable.setItems(rooms);

        List<Campus> campusList = new ArrayList<>();
        campusList.addAll(campuses);
        campusFilterBox.getItems().setAll(campusList);
        campusFilterBox.setValue(Campus.AlleCampus);
        FilteredList<CourseRoom> filteredRoom = new FilteredList<>(rooms, p -> true);
        searchBoxRoom.textProperty().addListener((observable, oldValue, newValue) -> filteredRoom.setPredicate(room -> checkFilterRoom(newValue, room)));
        campusFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredRoom.setPredicate(room -> checkFilterRoom(newValue, room)));
        SortedList<CourseRoom> sortedRooms = new SortedList<>(filteredRoom);
        sortedRooms.comparatorProperty().bind(roomTable.comparatorProperty());
        roomTable.setItems(sortedRooms);
        roomTable.setContextMenu(refreshMenu);
        roomTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addDeleteKey(roomAnchor, roomTable);
    }

    /**
     * adding the start() method on the button
     *
     * @param button button where function should be added
     * @param object object which should be changed or deleted
     * @param file   FileType what should happen with the object
     */
    @FXML
    public void addFunction(Button button, List<Object> object, ShowController.FileType file) {
        button.setOnAction((ActionEvent event) -> {
            try {
                this.file = file;
                this.object = object;
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addDeleteKey(AnchorPane anchorPane, TableView<?> table) {
        anchorPane.setOnKeyPressed(keyEvent -> {
            final List<?> selectedItem = table.getSelectionModel().getSelectedItems();
            if (selectedItem != null)
                if (keyEvent.getCode().equals(KeyCode.DELETE))
                    try {
                        this.file = FileType.delete;
                        this.object = new ArrayList<>(selectedItem);
                        start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        });
    }

    /**
     * opening a new javafx stage to edit or delete an object
     *
     * @param stage new stage show new window
     */
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file.toString()), Help.getRessourceBundle());
        Parent root = fxmlLoader.load();
        switch (file) {
            case editStudents: {
                StudentController controller = fxmlLoader.getController();
                controller.initVariables((DualStudent) object.get(0));
                break;
            }
            case editDocents: {
                DocentController controller = fxmlLoader.getController();
                controller.initVariables((Docent) object.get(0));
                break;
            }
            case editCompany: {
                CompanyController controller = fxmlLoader.getController();
                controller.initVariables((Company) object.get(0));
                break;
            }
            case editCourse: {
                CourseController controller = fxmlLoader.getController();
                controller.initVariables((Course) object.get(0));
                break;
            }
            case editRoom: {
                RoomController controller = fxmlLoader.getController();
                controller.initVariables((CourseRoom) object.get(0));
                break;
            }
            case delete: {
                AcceptDeleteController controller = fxmlLoader.getController();
                controller.initVariables(object);
                break;
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHidden(windowEvent -> refresh());
        stage.initOwner(studentTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(Help.getRessourceBundle().getString("title"));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/dhbwLogoSquare.png")));
        stage.show();
    }

    /**
     * initializing change and delete button with image and adding function
     *
     * @param fileType the fileType what should happen when pressing on button
     */
    private <T> Callback<TableColumn<T, Void>, TableCell<T, Void>> getCallback(FileType fileType) {
        return new Callback<>() {
            @Override
            public TableCell<T, Void> call(TableColumn<T, Void> dualStudentVoidTableColumn) {
                return new TableCell<>() {
                    final Button btn = new Button();

                    @Override
                    protected void updateItem(Void aVoid, boolean b) {
                        super.updateItem(aVoid, b);
                        if (b) {
                            setGraphic(null);
                        } else {
                            ArrayList<Object> a = new ArrayList<>();
                            a.add(getTableView().getItems().get(getIndex()));
                            addFunction(btn, a, fileType);
                            if (fileType.equals(FileType.delete))
                                btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/deleteButton.png"))));
                            else
                                btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/editButton.png"))));
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
    }

    /**
     * filtering the room table with input text from search boxes and combo boxes
     *
     * @param newValue value which get changed (search string, Company or Course)
     * @param room   room of database table which gets checked on input
     * @return {true} if person has the configure attributes from the filters, {false} if not
     */
    private boolean checkFilterRoom(Object newValue, CourseRoom room) {
        boolean erg = true;
        String search;
        if (newValue instanceof String)
            search = ((String) newValue);
        else
            search = searchBoxRoom.getText();
        if (search != null && !search.isEmpty()) {
            search = search.trim().toLowerCase();
            if (room.getName().toLowerCase().contains(search))
                erg = true;
            else
                erg = ("" + room.getCampus()).toLowerCase().contains(search);
        }
        return erg;
    }

    /**
     * filtering the student table with input text from search boxes and combo boxes
     *
     * @param newValue value which get changed (search string, Company or Course)
     * @param person   person of database table which gets checked on input
     * @return {true} if person has the configure attributes from the filters, {false} if not
     */
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
        if (company != null && !company.equals(allCompany))
            erg &= company.equals(person.getCompany());

        Course course;
        if (newValue instanceof Course)
            course = (Course) newValue;
        else
            course = courseFilterBox.getValue();
        if (course != null && !course.equals(allCourse))
            erg &= course.equals(person.getCourse());
        return erg;
    }

    /**
     * Enum for button functions
     */
    public enum FileType {
        editStudents("student.fxml"), editDocents("docent.fxml"), editCourse("course.fxml"), editCompany("company.fxml"), editRoom("room.fxml"), delete("acceptDelete.fxml");
        private final String name;

        FileType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
