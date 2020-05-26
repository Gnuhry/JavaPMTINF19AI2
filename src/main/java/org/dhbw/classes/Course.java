package org.dhbw.classes;

import java.util.Date;
import java.util.List;

public class Course {
    private List<Student> students;
    private String name;
    private StudyCourse studyCourse;
    private LecturePlan lecturePlan;
    private Docent studyDirector;
    private Student courseSpeaker;
    private Date registrationDate;
    private CourseRoom room;


    public Course(String name, StudyCourse studyCourse, Date registrationDate) {
        this.name = name;
        this.studyCourse = studyCourse;
        this.registrationDate = registrationDate;
    }

    //-------------------------------Setter----------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setLecturePlan(LecturePlan lecturePlan) {
        this.lecturePlan = lecturePlan;
    }

    public void setStudyDirector(Docent studyDirector) {
        this.studyDirector = studyDirector;
    }

    public void setCourseSpeaker(Student courseSpeaker) {
        this.courseSpeaker = courseSpeaker;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setRoom(CourseRoom room) {
        this.room = room;
    }

    //-------------------------------Getter----------------------------------------------
    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public StudyCourse getStudyCourse() {
        return studyCourse;
    }

    public LecturePlan getLecturePlan() {
        return lecturePlan;
    }

    public Docent getStudyDirector() {
        return studyDirector;
    }

    public Student getCourseSpeaker() {
        return courseSpeaker;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public CourseRoom getRoom() {
        return room;
    }

    /**
     * Adding only one student
     *
     * @param student student who is a part of this course
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }
}
