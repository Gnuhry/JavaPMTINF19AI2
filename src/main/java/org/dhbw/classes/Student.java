package org.dhbw.classes;

import java.util.Date;

public abstract class Student extends Person {
    private int matriculationNumber; //TODO automatic? or manuel with constructer?
    private int studentNumber;
    private Course course;
    private String email;
    private int javaKnowledge;


    public Student(String name, String forename, Date birthday, Address address) {
        super(name, forename, birthday, address);
    }

    //--------------------------Setter------------------------
    public void setCourse(Course course) {
        this.course = course;
    }

    public void setJavaKnowledge(int javaKnowledge) {
        this.javaKnowledge = javaKnowledge;
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

    public String getEmail() {
        return email;
    }

    public int getJavaKnowledge() {
        return javaKnowledge;
    }
}
