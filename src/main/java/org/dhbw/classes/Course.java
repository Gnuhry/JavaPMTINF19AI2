package org.dhbw.classes;

import java.util.Date;
import java.util.Objects;

public class Course {
    private final Date registrationDate;
    private final StudyCourse studyCourse;
    private String name;
    private Docent studyDirector;
    private CourseRoom room;


    public Course(String name, StudyCourse studyCourse, Date registrationDate) {
        this.name = name;
        this.studyCourse = studyCourse;
        this.registrationDate = registrationDate;
    }

    public Course(String name, StudyCourse studyCourse, Docent studyDirector, Date registrationDate, CourseRoom room) {
        this.name = name;
        this.studyCourse = studyCourse;
        this.studyDirector = studyDirector;
        this.registrationDate = registrationDate;
        this.room = room;
    }

    //-------------------------------Setter----------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setStudyDirector(Docent studyDirector) {
        this.studyDirector = studyDirector;
    }

    public void setRoom(CourseRoom room) {
        this.room = room;
    }

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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public CourseRoom getRoom() {
        return room;
    }

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


    @Override
    public String toString() {
        return name;
    }
}
