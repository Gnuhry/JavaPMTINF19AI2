package org.dhbw.classes;

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

    public int getCourseSpeaker() {
        return courseSpeakerID;
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
        return "Course{" +
                "registrationDate=" + registrationDate +
                ", studyCourse=" + studyCourse +
                ", name='" + name + '\'' +
                ", studyDirector=" + studyDirector +
                ", courseSpeakerID=" + courseSpeakerID +
                ", room=" + room +
                '}';
    }
}
