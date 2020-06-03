package org.dhbw.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import org.dhbw.App;
import org.dhbw.ShowStudentsController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class DualStudent extends Person {

    private final Company company;
    private int matriculationNumber; //TODO automatic? or manuel with constructer? -> Silas: Ist schon automatisch generiert im GUI
    private int studentNumber;
    private Course course;
    private int javaKnowledge;
    private Button changeButton;
    public Button deleteButton;

    Class<?> clazz = this.getClass();
    InputStream inputEdit = clazz.getResourceAsStream("/org/dhbw/images/editButton.png");
    Image imageEdit = new Image(inputEdit);
    InputStream inputDelete = clazz.getResourceAsStream("/org/dhbw/images/deleteButton.png");
    Image imageDelete = new Image(inputDelete);

    public DualStudent(String name, String forename, Date birthday, Address address, Company company) {
        super(name, forename, birthday, address);
        this.company = company;
        javaKnowledge = 0;
    }

    public DualStudent(int matriculationNumber, int studentNumber, String name, String forename, Date birthday, Address address, String email, Course course, int javaKnowledge, Company company) {
        super(name, forename, birthday, address, email);
        this.matriculationNumber = matriculationNumber;
        this.studentNumber = studentNumber;
        this.course = course;
        this.javaKnowledge = javaKnowledge;
        this.company = company;
        this.changeButton = new Button();
        this.deleteButton = new Button();
        this.changeButton.setOnAction((ActionEvent event) -> {
            University.updateStudent(this);
        });
        changeButton.setGraphic(new ImageView(imageEdit));
        this.deleteButton.setOnAction((ActionEvent event) -> {
            University.removeStudent(this);
        });
        deleteButton.setGraphic(new ImageView(imageDelete));
    }



    //--------------------------Setter------------------------
    public void setCourse(Course course) {
        this.course = course;
    }

    public void setJavaKnowledge(int javaKnowledge) {
        this.javaKnowledge = javaKnowledge;
    }

    public void setChangeButton(Button changeButton) {
        this.changeButton = changeButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    //-----------------------Getter-----------------------
    public int getMatriculationNumber() {
        return matriculationNumber;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public Course getCourse() {
        return course;
    }

    public int getJavaKnowledge() {
        return javaKnowledge;
    }

    public Company getCompany() {
        return company;
    }

    public Button getChangeButton() {
        return changeButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    //-------------------Overrides--------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DualStudent that = (DualStudent) o;
        return studentNumber == that.studentNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void addFunctionOnButtons() {

    }
}
