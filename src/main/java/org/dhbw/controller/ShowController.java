package org.dhbw.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
import org.dhbw.Database;
import org.dhbw.classes.*;
import org.dhbw.help.GuiHelp;
import org.dhbw.help.Language;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShowController implements Initializable {
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

    private static boolean alert;
    private FileType file;
    private List<Object> object;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private final Company allCompany = new Company(Language.getResourcedBundle().getString("all_company"), null, null);
    private final Course allCourse = new Course(Language.getResourcedBundle().getString("all_course"), null, null);

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
    private TabPane tabPaneShow;
    @FXML
    private ToolBar barStudent, barDocent, barCourse, barCompany, barRoom;

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
    private TableColumn<CourseRoom, String> roomName, roomCampus, roomBuilding, roomFloor, roomProjector, roomDocumentCamera, roomLaboratory;
    @FXML
    private TableColumn<CourseRoom, Integer> roomSeats;
    @FXML
    private TableColumn<CourseRoom, Void> roomC, roomD;

    /**
     * changing the scene root in App to "primary.fxml"
     */
    @FXML
    private void backToOverview() throws IOException {
        App.setRoot("primary");
    }

    /**
     * action on refresh button
     */
    @FXML
    public void refresh() {
        refresh(true);
    }

    /**
     * refreshing all tables
     */
    public void refresh(boolean show) {
        if (show)
            studentTable.getScene().setCursor(Cursor.WAIT);
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
        if (show)
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                studentTable.getScene().setCursor(Cursor.DEFAULT);
            }).start();
    }

    /**
     * initializing every column of the table with the data from other classes
     * adding filter functions for lecture table, course table and company table (input from search boxes)
     * creating contextmenu and adding functions to buttons and adding delete key
     */
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
                    App.getHostService().showDocument("mailto:" + cell.getTableView().getItems().get(cell.getIndex()).getEmail());
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
        addKeyListener(studentAnchor, studentTable, barStudent);

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
                    App.getHostService().showDocument("mailto:" + cell.getTableView().getItems().get(cell.getIndex()).getEmail());
            });
            return cell;
        });
        docentC.setCellFactory(getCallback(FileType.editDocents));
        docentD.setCellFactory(getCallback(FileType.delete));

        addContextMenu(docentTable);
        docentTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        addKeyListener(docentAnchor, docentTable, barDocent);

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
                                App.getHostService().showDocument(sb.toString().substring(0, sb.toString().length() - 2));
                            });
                            btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/controller/images/mailButton.png"))));
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
        addKeyListener(courseAnchor, courseTable, barCourse);

        List<String> studyTypeList = new ArrayList<>();
        studyTypeList.add(Language.getResourcedBundle().getString("all_study_types"));
        for (StudyCourse course : StudyCourse.values())
            studyTypeList.add(course.toString());
        studyTypeFilterBox.getItems().setAll(studyTypeList);
        studyTypeFilterBox.setValue(Language.getResourcedBundle().getString("all_study_types"));
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
                                    App.getHostService().showDocument("mailto:" + company.getContactPerson().getEmail());

                            });
                            btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/controller/images/mailButton.png"))));
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
        addKeyListener(companyAnchor, companyTable, barCompany);

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
        campusList.add(Language.getResourcedBundle().getString("all_campus"));
        for (Campus campus : Campus.values())
            campusList.add(campus.toString());
        campusFilterBox.getItems().setAll(campusList);
        campusFilterBox.setValue(Language.getResourcedBundle().getString("all_campus"));
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
        addKeyListener(roomAnchor, roomTable, barRoom);
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
            this.file = file;
            this.object = object;
            start(new Stage());
        });
    }

    /**
     * set a alert for the user
     */
    private static void setAlert() {
        if (alert) {
            alert = false;
            Alert a = new Alert(Alert.AlertType.NONE, Language.getResourcedBundleError().getString("fxml_error"), ButtonType.OK);
            a.setTitle(Language.getResourcedBundleError().getString("error"));
            Stage stage = ((Stage) a.getDialogPane().getScene().getWindow());
            stage.getIcons().add(new Image(App.getClass_().getResourceAsStream(GuiHelp.logoPath)));
            a.showAndWait();
            alert = true;
        }
    }

    /**
     * addContextMenu to TableViews
     *
     * @param view tableView to adding the menu
     */
    public void addContextMenu(TableView<?> view) {
        ContextMenu refreshMenu = new ContextMenu();
        MenuItem[] optional_menu = new MenuItem[]{new MenuItem(Language.getResourcedBundle().getString("edit")), new MenuItem(Language.getResourcedBundle().getString("send_email")), new MenuItem(Language.getResourcedBundle().getString("set_to_uni_email")), new MenuItem(Language.getResourcedBundle().getString("delete")), new MenuItem(Language.getResourcedBundle().getString("send_email_with_docent")), new MenuItem(Language.getResourcedBundle().getString("clear_selection"))};
        MenuItem[] item = new MenuItem[]{new MenuItem(Language.getResourcedBundle().getString("refresh")), new MenuItem(Language.getResourcedBundle().getString("back"))};

        item[0].setOnAction(actionEvent -> refresh(true));
        item[1].setOnAction(actionEvent -> {
            try {
                backToOverview();
            } catch (IOException e) {
                setAlert();
            }
        });
        refreshMenu.getItems().addAll(item);
        refreshMenu.setOnShown(show -> {
            if (view.getSelectionModel().getSelectedItems() != null && view.getSelectionModel().getSelectedItems().size() > 0) {
                ContextMenu contextMenu = view.getContextMenu();
                int counter = 0;

                if (view.getSelectionModel().getSelectedItems().size() == 1) {
                    MenuItem edit = optional_menu[0];
                    edit.setOnAction(actionEvent -> editObject(view.getSelectionModel().getSelectedItems().get(0)));
                    contextMenu.getItems().add(counter++, edit);
                } else
                    contextMenu.getItems().remove(optional_menu[0]);

                if (view.getItems() != null && view.getItems().get(0) != null) {
                    Class<?> class_ = view.getItems().get(0).getClass();

                    if (class_ == DualStudent.class || class_ == Docent.class || class_ == Company.class || class_ == Course.class) {
                        MenuItem mail = optional_menu[1];
                        mail.setOnAction(actionEvent -> {
                            sendMailToObject(view.getSelectionModel().getSelectedItems(), false);
                            view.getSelectionModel().clearSelection();
                        });
                        contextMenu.getItems().add(counter++, mail);
                    } else
                        contextMenu.getItems().remove(optional_menu[1]);

                    if (class_ == Course.class) {
                        MenuItem setEmail2 = optional_menu[4];
                        setEmail2.setOnAction(actionEvent -> {
                            sendMailToObject(view.getSelectionModel().getSelectedItems(), true);
                            view.getSelectionModel().clearSelection();
                        });
                        contextMenu.getItems().add(counter++, setEmail2);
                    } else
                        contextMenu.getItems().remove(optional_menu[4]);

                    if (class_ == DualStudent.class || class_ == Docent.class) {
                        MenuItem setEmail = optional_menu[2];
                        setEmail.setOnAction(actionEvent -> {
                            setToUniversityMail(view.getSelectionModel().getSelectedItems());
                            refresh(false);
                        });
                        contextMenu.getItems().add(counter++, setEmail);
                    } else
                        contextMenu.getItems().remove(optional_menu[2]);
                }

                MenuItem delete = optional_menu[3];
                delete.setOnAction(actionEvent -> deleteObject(view.getSelectionModel().getSelectedItems()));
                contextMenu.getItems().add(counter++, delete);
                view.setContextMenu(contextMenu);

                MenuItem none = optional_menu[5];
                none.setOnAction(actionEvent -> view.getSelectionModel().clearSelection());
                contextMenu.getItems().add(counter, none);
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
    public void addKeyListener(AnchorPane anchorPane, TableView<?> table, ToolBar bar) {
        if (table.getItems() != null && table.getItems().size() > 0 && table.getItems().get(0) != null && (table.getItems().get(0).getClass() == DualStudent.class || table.getItems().get(0).getClass() == Docent.class)) {
            anchorPane.setOnKeyPressed(keyEvent -> {
                final List<?> selectedItem = table.getSelectionModel().getSelectedItems();
                if (selectedItem != null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE))
                        deleteObject(selectedItem);
                    else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                        table.getSelectionModel().clearSelection();
                    else if (keyEvent.getCode().equals(KeyCode.ENTER) && selectedItem.size() == 1)
                        editObject(selectedItem.get(0));
                    else if (keyEvent.getCode().equals(KeyCode.U) && keyEvent.isControlDown()) {
                        setToUniversityMail(selectedItem);
                        refresh(false);
                    } else if (keyEvent.getCode().equals(KeyCode.M) && keyEvent.isControlDown()) {
                        sendMailToObject(selectedItem, keyEvent.isShiftDown());
                        table.getSelectionModel().clearSelection();
                    } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.R))
                        refresh(true);
                }
            });
        } else {
            anchorPane.setOnKeyPressed(keyEvent -> {
                final List<?> selectedItem = table.getSelectionModel().getSelectedItems();
                if (selectedItem != null)
                    if (keyEvent.getCode().equals(KeyCode.DELETE))
                        deleteObject(selectedItem);
                    else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                        table.getSelectionModel().clearSelection();
                    else if (keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.R))
                        refresh(true);
            });
        }
        table.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown())
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    tabPaneShow.getSelectionModel().select(tabPaneShow.getSelectionModel().getSelectedIndex() == 0 ? tabPaneShow.getTabs().size() - 1 : tabPaneShow.getSelectionModel().getSelectedIndex() - 1);
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    tabPaneShow.getSelectionModel().select((tabPaneShow.getSelectionModel().getSelectedIndex() + 1) % tabPaneShow.getTabs().size());
                }
        });
        bar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown())
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    tabPaneShow.getSelectionModel().select(tabPaneShow.getSelectionModel().getSelectedIndex() == 0 ? tabPaneShow.getTabs().size() - 1 : tabPaneShow.getSelectionModel().getSelectedIndex() - 1);
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    tabPaneShow.getSelectionModel().select((tabPaneShow.getSelectionModel().getSelectedIndex() + 1) % tabPaneShow.getTabs().size());
                }
        });
    }

    /**
     * send an email to all objects or inner object with email addresses
     *
     * @param objects  objects to get the email from
     * @param director {true} if the course email include the director email, {false} if not
     */
    private void sendMailToObject(List<?> objects, boolean director) {
        StringBuilder sb = new StringBuilder("mailto:");
        for (Object o : objects) {
            if (o instanceof DualStudent)
                sb.append(((DualStudent) o).getEmail()).append(", ");
            else if (o instanceof Docent)
                sb.append(((Docent) o).getEmail()).append(", ");
            else if (o instanceof Company && ((Company) o).getContactPerson() != null && ((Company) o).getContactPerson().getEmail() != null && !((Company) o).getContactPerson().getEmail().isEmpty())
                sb.append(((Company) o).getContactPerson().getEmail()).append(", ");
            else if (o instanceof Course) {
                if (director) {
                    Course c = (Course) o;
                    if (c.getStudyDirector() != null && c.getStudyDirector().getEmail() != null && !c.getStudyDirector().getEmail().isEmpty())
                        sb.append(((Course) o).getStudyDirector().getEmail()).append(", ");
                }
                for (String email : Objects.requireNonNullElseGet(University.getAllEmailFromCourse((Course) o), () -> new String[0]))
                    sb.append(email).append(", ");
            }
        }
        if (!sb.toString().equals("mailto:"))
            App.getHostService().showDocument(sb.toString().substring(0, sb.toString().length() - 2));
    }

    /**
     * set student or docent email addresses to university conform addresses
     *
     * @param objects student or docent to change the email address
     */
    private void setToUniversityMail(List<?> objects) {
        for (Object o : objects) {
            if (o instanceof DualStudent) {
                DualStudent d = new DualStudent((DualStudent) o);
                d.setEmail(GuiHelp.getStudentUniversityEmail(d));
                Database.updateStudent(d, (DualStudent) o);
            } else if (o instanceof Docent) {
                Docent d = new Docent((Docent) o);
                d.setEmail(GuiHelp.getDocentUniversityEmail(d));
                Database.updateDocent(d, (Docent) o);
            }
        }
    }

    /**
     * delete object
     *
     * @param objects object to delete
     */
    private void deleteObject(List<?> objects) {
        this.file = FileType.delete;
        this.object = new ArrayList<>(objects);
        start(new Stage());
    }

    /**
     * edit object
     *
     * @param object object to edit
     */
    private void editObject(Object object) {
        if (object instanceof DualStudent)
            this.file = FileType.editStudents;
        else if (object instanceof Docent)
            this.file = FileType.editDocents;
        else if (object instanceof Course)
            this.file = FileType.editCourse;
        else if (object instanceof Company)
            this.file = FileType.editCompany;
        else if (object instanceof CourseRoom)
            this.file = FileType.editRoom;
        this.object = Collections.singletonList(object);
        start(new Stage());
    }

    /**
     * opening a new javafx stage to edit or delete an object
     *
     * @param stage new stage show new window
     */
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file.toString()), Language.getResourcedBundle());
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            setAlert();
            return;
        }
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
        stage.setOnHidden(windowEvent -> refresh(false));
        stage.setResizable(false);
        stage.initOwner(studentTable.getScene().getWindow());
        stage.setX(studentTable.getScene().getWindow().getX());
        stage.setY(studentTable.getScene().getWindow().getY());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(Language.getResourcedBundle().getString("title"));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(GuiHelp.logoPath)));
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
                                btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/controller/images/deleteButton.png"))));
                            else
                                btn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/org/dhbw/controller/images/editButton.png"))));
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
     * Enum for button functions and with the right fxml file path
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
