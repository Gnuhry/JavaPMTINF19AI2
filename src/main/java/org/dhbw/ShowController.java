package org.dhbw;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import java.util.*;

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

    private FileType file;
    private List<Object> object;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private final Company allCompany = new Company(Help.getResourcedBundle().getString("all_company"), null, null);
    private final Course allCourse = new Course(Help.getResourcedBundle().getString("all_course"), null, null);

    @FXML
    private AnchorPane studentAnchor, docentAnchor, courseAnchor, companyAnchor, roomAnchor;
    @FXML
    private TextField searchBox, searchBoxDocent, searchBoxCourse, searchBoxCompany, searchBoxRoom;
    @FXML
    private ComboBox<Course> courseFilterBox;
    @FXML
    private ComboBox<Company> companyFilterBox;
    @FXML
    private ComboBox<String> campusFilterBox, studyTypeFilterBox;
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
    private TableView<Docent> docentTable;
    @FXML
    private TableColumn<Docent, Integer> docentNumber;
    @FXML
    private TableColumn<Docent, String> docentFirstName, docentLastName, docentEmail;
    @FXML
    private TableColumn<Docent, Date> docentBirth;
    @FXML
    private TableColumn<Docent, Address> docentAddress;
    @FXML
    private TableColumn<Docent, Void> docentC, docentD;

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
    private TableColumn<Course, Docent> courseDocent;
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
    private TableColumn<Company, Void> companyC, companyD, companyMail;

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
    private TableColumn<CourseRoom, String> roomProjector;
    @FXML
    private TableColumn<CourseRoom, String> roomDocumentCamera;
    @FXML
    private TableColumn<CourseRoom, String> roomLaboratory;
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
        students.clear();
        students.addAll(University.getStudents());
        docents.clear();
        docents.addAll(University.getDocents());
        courses.clear();
        courses.addAll(University.getCourses());
        companies.clear();
        companies.addAll(University.getCompanies());
        rooms.clear();
        rooms.addAll(University.getRooms());
    }

    /**
     * initializing every column of the table with the data from other classes
     * adding filter functions for lecture table, course table and company table (input from search boxes)
     * creating contextmenu and adding functions to buttons and adding delete key
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        studentEmail.setCellFactory(dualStudentVoidTableColumn -> {
            TableCell<DualStudent, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (e.isControlDown() && !cell.isEmpty())
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
        addContextMenu(studentTable);
        studentTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(studentAnchor, studentTable);

        docentNumber.setCellValueFactory(new PropertyValueFactory<>("docentNumber"));
        docentLastName.setCellValueFactory(new PropertyValueFactory<>("name"));
        docentFirstName.setCellValueFactory(new PropertyValueFactory<>("forename"));
        docentBirth.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean b) {
                super.updateItem(date, b);
                if (b || date == null)
                    setText(null);
                else
                    this.setText(format.format(date));
            }
        });
        docentBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        docentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        docentAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        docentEmail.setCellFactory(lectureVoidTableColumn -> {
            TableCell<Docent, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (e.isControlDown() && !cell.isEmpty())
                    getHostServices().showDocument("mailto:" + cell.getTableView().getItems().get(cell.getIndex()).getEmail());
            });
            return cell;
        });
        docentC.setCellFactory(getCallback(FileType.editDocents));
        docentD.setCellFactory(getCallback(FileType.delete));

        addContextMenu(docentTable);
        docentTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(docentAnchor, docentTable);

        FilteredList<Docent> filteredLecture = new FilteredList<>(docents, p -> true);
        searchBoxDocent.textProperty().addListener((observable, oldValue, newValue) -> filteredLecture.setPredicate(person -> {
            if (newValue == null || newValue.isEmpty())
                return true;
            String lowerCaseFilter = newValue.toLowerCase();
            return person.getForename().toLowerCase().contains(lowerCaseFilter) || (person.getName().toLowerCase().contains(lowerCaseFilter) || String.valueOf(person.getDocentNumber()).toLowerCase().contains(lowerCaseFilter));
        }));
        SortedList<Docent> sortedLecture = new SortedList<>(filteredLecture);
        sortedLecture.comparatorProperty().bind(docentTable.comparatorProperty());
        docentTable.setItems(sortedLecture);

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
        courseDocent.setCellValueFactory(new PropertyValueFactory<>("studyDirector"));
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
        addContextMenu(courseTable);
        courseTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(courseAnchor, courseTable);

        List<String> studyTypeList = new ArrayList<>();
        studyTypeList.add(Help.getResourcedBundle().getString("all_study_types"));
        for (StudyCourse course : StudyCourse.values())
            studyTypeList.add(course.toString());
        studyTypeFilterBox.getItems().setAll(studyTypeList);
        studyTypeFilterBox.setValue(Help.getResourcedBundle().getString("all_study_types"));
        FilteredList<Course> filteredCourse2 = new FilteredList<>(courses, p -> true);
        searchBoxCourse.textProperty().addListener((observable, oldValue, newValue) -> filteredCourse2.setPredicate(this::checkFilterCourse));
        studyTypeFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredCourse2.setPredicate(this::checkFilterCourse));
        SortedList<Course> sortedCourses = new SortedList<>(filteredCourse2);
        sortedCourses.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(sortedCourses);

        companyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        companyPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        companyC.setCellFactory(getCallback(FileType.editCompany));
        companyD.setCellFactory(getCallback(FileType.delete));
        companyMail.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Company, Void> call(TableColumn<Company, Void> companyVoidTableColumn) {
                return new TableCell<>() {
                    final Button btn = new Button();

                    @Override
                    protected void updateItem(Void aVoid, boolean b) {
                        super.updateItem(aVoid, b);
                        if (b)
                            setGraphic(null);
                        else {
                            btn.setOnAction(actionEvent -> {
                                Company company = getTableView().getItems().get(getIndex());
                                if (company != null && company.getContactPerson() != null && company.getContactPerson().getEmail() != null && !company.getContactPerson().getEmail().isEmpty())
                                    getHostServices().showDocument("mailto:" + company.getContactPerson().getEmail());

                            });
                            btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/images/mailButton.png"))));
                            setGraphic(btn);
                        }
                    }
                };
            }
        });

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
        addContextMenu(companyTable);
        companyTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(companyAnchor, companyTable);

        roomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomCampus.setCellValueFactory(new PropertyValueFactory<>("campus"));
        roomBuilding.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        roomSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        roomProjector.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProjector() ? "\u2713" : "\u274c"));
        roomDocumentCamera.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCamera() ? "\u2713" : "\u274c"));
        roomLaboratory.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLaboratory() ? "\u2713" : "\u274c"));
        roomC.setCellFactory(getCallback(FileType.editRoom));
        roomD.setCellFactory(getCallback(FileType.delete));

        List<String> campusList = new ArrayList<>();
        campusList.add(Help.getResourcedBundle().getString("all_campus"));
        for (Campus campus : Campus.values())
            campusList.add(campus.toString());
        campusFilterBox.getItems().setAll(campusList);
        campusFilterBox.setValue(Help.getResourcedBundle().getString("all_campus"));
        FilteredList<CourseRoom> filteredRoom = new FilteredList<>(rooms, p -> true);
        searchBoxRoom.textProperty().addListener((observable, oldValue, newValue) -> filteredRoom.setPredicate(this::checkFilterRoom));
        campusFilterBox.valueProperty().addListener((observable, oldValue, newValue) -> filteredRoom.setPredicate(this::checkFilterRoom));
        SortedList<CourseRoom> sortedRooms = new SortedList<>(filteredRoom);
        sortedRooms.comparatorProperty().bind(roomTable.comparatorProperty());
        roomTable.setItems(sortedRooms);
        addContextMenu(roomTable);
        roomTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(roomAnchor, roomTable);
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

    public void addContextMenu(TableView<?> view) {
        MenuItem[] optional_menu = new MenuItem[]{new MenuItem(Help.getResourcedBundle().getString("edit")), new MenuItem(Help.getResourcedBundle().getString("send_email")), new MenuItem(Help.getResourcedBundle().getString("set_to_uni_email")), new MenuItem(Help.getResourcedBundle().getString("delete"))};
        ContextMenu refreshMenu = new ContextMenu();
        MenuItem[] item = new MenuItem[]{new MenuItem(Help.getResourcedBundle().getString("refresh")), new MenuItem(Help.getResourcedBundle().getString("back"))};
        item[0].setOnAction(actionEvent -> refresh());
        item[1].setOnAction(actionEvent -> {
            try {
                backToOverview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        refreshMenu.getItems().addAll(item);

        view.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ContextMenu contextMenu = view.getContextMenu();
                int counter = 0;
                if (view.getSelectionModel().getSelectedItems().size() == 1) {
                    MenuItem edit = optional_menu[0];
                    edit.setOnAction(actionEvent -> {
                        try {
                            Object o = view.getSelectionModel().getSelectedItems().get(0);
                            if (o instanceof DualStudent)
                                this.file = FileType.editStudents;
                            else if (o instanceof Docent)
                                this.file = FileType.editDocents;
                            else if (o instanceof Course)
                                this.file = FileType.editCourse;
                            else if (o instanceof Company)
                                this.file = FileType.editCompany;
                            else if (o instanceof CourseRoom)
                                this.file = FileType.editRoom;
                            this.object = Collections.singletonList(o);
                            start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    contextMenu.getItems().add(counter++, edit);
                } else {
                    contextMenu.getItems().remove(optional_menu[0]);
                }

                if (view.getItems() != null && view.getItems().get(0) != null && (view.getItems().get(0).getClass() == DualStudent.class || view.getItems().get(0).getClass() == Docent.class || view.getItems().get(0).getClass() == Company.class)) {
                    MenuItem mail = optional_menu[1];
                    mail.setOnAction(actionEvent -> {
                        StringBuilder sb = new StringBuilder("mailto:");
                        for (Object o : view.getSelectionModel().getSelectedItems()) {
                            if (o instanceof DualStudent)
                                sb.append(((DualStudent) o).getEmail()).append(", ");
                            else if (o instanceof Docent)
                                sb.append(((Docent) o).getEmail()).append(", ");
                            else if (o instanceof Company)
                                sb.append(((Company) o).getContactPerson().getEmail()).append(", ");
                        }
                        if (!sb.toString().equals("mailto:"))
                            getHostServices().showDocument(sb.toString().substring(0, sb.toString().length() - 2));
                        view.getSelectionModel().clearSelection();
                    });
                    contextMenu.getItems().add(counter++, mail);
                }
                if (view.getItems() != null && view.getItems().get(0) != null && (view.getItems().get(0).getClass() == DualStudent.class || view.getItems().get(0).getClass() == Docent.class)) {
                    MenuItem setEmail = optional_menu[2];
                    setEmail.setOnAction(actionEvent -> {
                        for (Object o : view.getSelectionModel().getSelectedItems()) {
                            if (o instanceof DualStudent) {
                                DualStudent d = new DualStudent((DualStudent) o);
                                d.setEmail(Help.getStudentUniversityEmail(d));
                                Database.updateStudent(d, (DualStudent) o);
                            } else if (o instanceof Docent) {
                                Docent d = new Docent((Docent) o);
                                d.setEmail(Help.getDocentUniversityEmail(d));
                                Database.updateDocent(d, (Docent) o);
                            }
                        }
                        refresh();
                    });
                    contextMenu.getItems().add(counter++, setEmail);
                }
                MenuItem delete = optional_menu[3];
                delete.setOnAction(actionEvent -> {
                    try {
                        this.file = FileType.delete;
                        this.object = new ArrayList<>(view.getSelectionModel().getSelectedItems());
                        start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                contextMenu.getItems().add(counter, delete);
                view.setContextMenu(contextMenu);
            } else {
                view.getContextMenu().getItems().removeAll(optional_menu);
            }
        });
        view.setContextMenu(refreshMenu);
    }

    /**
     * add the delete key to delete rows
     *
     * @param anchorPane controller to bind the key
     * @param table      row to delete
     */
    public void addKeyListener(AnchorPane anchorPane, TableView<?> table) {
        if (table.getItems() != null && table.getItems().size() > 0 && table.getItems().get(0) != null && (table.getItems().get(0).getClass() == DualStudent.class || table.getItems().get(0).getClass() == Docent.class)) {
            anchorPane.setOnKeyPressed(keyEvent -> {
                final List<?> selectedItem = table.getSelectionModel().getSelectedItems();
                if (selectedItem != null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE))
                        try {
                            this.file = FileType.delete;
                            this.object = new ArrayList<>(selectedItem);
                            start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                        table.getSelectionModel().clearSelection();
                    else if (keyEvent.getCode().equals(KeyCode.U) && keyEvent.isControlDown()) {
                        for (Object o : selectedItem) {
                            if (o instanceof DualStudent) {
                                DualStudent d = new DualStudent((DualStudent) o);
                                d.setEmail(Help.getStudentUniversityEmail(d));
                                Database.updateStudent(d, (DualStudent) o);
                            } else if (o instanceof Docent) {
                                Docent d = new Docent((Docent) o);
                                d.setEmail(Help.getDocentUniversityEmail(d));
                                Database.updateDocent(d, (Docent) o);
                            }
                        }
                        refresh();
                    } else if (keyEvent.getCode().equals(KeyCode.M) && keyEvent.isControlDown()) {
                        StringBuilder sb = new StringBuilder("mailto:");
                        for (Object o : selectedItem) {
                            if (o instanceof DualStudent)
                                sb.append(((DualStudent) o).getEmail()).append(", ");
                            else if (o instanceof Docent)
                                sb.append(((Docent) o).getEmail()).append(", ");
                        }
                        if (!sb.toString().equals("mailto:"))
                            getHostServices().showDocument(sb.toString().substring(0, sb.toString().length() - 2));
                        table.getSelectionModel().clearSelection();
                    }
                }
            });
        } else {
            anchorPane.setOnKeyPressed(keyEvent -> {
                final List<?> selectedItem = table.getSelectionModel().getSelectedItems();
                if (selectedItem != null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE))
                        try {
                            this.file = FileType.delete;
                            this.object = new ArrayList<>(selectedItem);
                            start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                        table.getSelectionModel().clearSelection();
                }
            });
        }
    }

    /**
     * opening a new javafx stage to edit or delete an object
     *
     * @param stage new stage show new window
     */
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file.toString()), Help.getResourcedBundle());
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
        stage.setResizable(false);
        stage.initOwner(studentTable.getScene().getWindow());
        stage.setX(studentTable.getScene().getWindow().getX());
        stage.setY(studentTable.getScene().getWindow().getY());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(Help.getResourcedBundle().getString("title"));
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
     * @param room room of database table which gets checked on input
     * @return {true} if room has the configure attributes from the filters, {false} if not
     */
    private boolean checkFilterRoom(CourseRoom room) {
        boolean erg = true;
        String search = searchBoxRoom.getText();
        int id = campusFilterBox.getSelectionModel().getSelectedIndex() - 1;
        if (id > -1 && (id >= Campus.values().length ? null : Campus.values()[id]) != room.getCampus())
            return false;
        if (search != null && !search.isEmpty()) {
            search = search.trim().toLowerCase();
            erg = room.getName().toLowerCase().contains(search) || room.getCampus().toString().toLowerCase().contains(search);
        }
        return erg;
    }

    /**
     * filtering the course table with input text from search boxes and combo boxes
     *
     * @param course course of database table which gets checked on input
     * @return {true} if course has the configure attributes from the filters, {false} if not
     */
    private boolean checkFilterCourse(Course course) {
        boolean erg = true;
        String search = searchBoxCourse.getText();
        int id = studyTypeFilterBox.getSelectionModel().getSelectedIndex() - 1;
        if (id > -1 && (id >= StudyCourse.values().length ? null : StudyCourse.values()[id]) != course.getStudyCourse())
            return false;
        if (search != null && !search.isEmpty()) {
            search = search.trim().toLowerCase();
            erg = course.getName().toLowerCase().contains(search) || course.getRoom().getName().toLowerCase().contains(search);
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
