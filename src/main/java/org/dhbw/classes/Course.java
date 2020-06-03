package org.dhbw.classes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Course {
    private final Date registrationDate;
    private final StudyCourse studyCourse;
    private String name;
    private Docent studyDirector;
    private int courseSpeakerID;
    private CourseRoom room;
    private Button changeButton;
    public Button deleteButton;

    Class<?> clazz = this.getClass();
    InputStream inputEdit = clazz.getResourceAsStream("/org/dhbw/images/editButton.png");
    Image imageEdit = new Image(inputEdit);
    InputStream inputDelete = clazz.getResourceAsStream("/org/dhbw/images/deleteButton.png");
    Image imageDelete = new Image(inputDelete);


    public Course(String name, StudyCourse studyCourse, Date registrationDate) {
        this.name = name;
        this.studyCourse = studyCourse;
        this.registrationDate = registrationDate;
    }

    public Course(String name, StudyCourse studyCourse, Docent studyDirector, int courseSpeakerID, Date registrationDate, CourseRoom room) {
        this.name = name;
        this.studyCourse = studyCourse;
        this.studyDirector = studyDirector;
        this.courseSpeakerID = courseSpeakerID;
        this.registrationDate = registrationDate;
        this.room = room;
        this.changeButton = new Button();
        this.deleteButton = new Button();
        this.changeButton.setOnAction((ActionEvent event) -> {
            University.updateCourse(this);
        });
        changeButton.setGraphic(new ImageView(imageEdit));
        this.deleteButton.setOnAction((ActionEvent event) -> {
            University.removeCourse(this);
        });
        deleteButton.setGraphic(new ImageView(imageDelete));
    }

    //-------------------------------Setter----------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setStudyDirector(Docent studyDirector) {
        this.studyDirector = studyDirector;
    }

    public void setCourseSpeakerID(int courseSpeakerID) {
        this.courseSpeakerID = courseSpeakerID;
    }

    public void setRoom(CourseRoom room) {
        this.room = room;
    }

    public void setChangeButton(Button changeButton) {this.changeButton = changeButton;}

    public void setDeleteButton(Button deleteButton) {this.deleteButton = deleteButton;}

    //-------------------------------Getter----------------------------------------------
    public String getName() {
        return name;
    }

    public StudyCourse getStudyCourse() {
        return studyCourse;
    }

    public Docent getStudyDirector() {
        return studyDirector;
    }

    public int getCourseSpeakerID() {
        return courseSpeakerID;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public CourseRoom getRoom() {
        return room;
    }

    public Button getChangeButton() {return changeButton;}

    public Button getDeleteButton() {return deleteButton;}

    //----------------------------Override--------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(registrationDate, course.registrationDate) &&
                studyCourse == course.studyCourse &&
                Objects.equals(name, course.name) &&
                room == course.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationDate, studyCourse, name, room);
    }

//    @Override
//    public String toString() {
//        return "Course{" +
//                "registrationDate=" + registrationDate +
//                ", studyCourse=" + studyCourse +
//                ", name='" + name + '\'' +
//                ", studyDirector=" + studyDirector +
//                ", courseSpeakerID=" + courseSpeakerID +
//                ", room=" + room +
//                '}';
//    }


    @Override
    public String toString() {
        return name;
    }
}
