package org.dhbw.classes;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class DualStudent extends Person {
    private final Company company;
    private int matriculationNumber; //TODO automatic? or manuel with constructer?
    private String studentNumber;
    private Course course;
    private int javaKnowledge;


    public DualStudent(String name, String forename, Date birthday, Address address, Company company) {
        super(name, forename, birthday, address);
        this.company = company;
        javaKnowledge = 0;
    }

    public DualStudent(int matriculationNumber, String studentNumber, String name, String forename, Date birthday, Address address, String email, Course course, int javaKnowledge, Company company) {
        super(name, forename, birthday, address, email);
        this.matriculationNumber = matriculationNumber;
        this.studentNumber = studentNumber;
        this.course = course;
        this.javaKnowledge = javaKnowledge;
        this.company = company;
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

    public String getStudentNumber() {
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
        return "DualStudent{" +
                "Student" + super.toString() +
                "company=" + company +
                ", matriculationNumber=" + matriculationNumber +
                ", studentNumber='" + studentNumber + '\'' +
                ", course=" + course +
                ", javaKnowledge=" + javaKnowledge +
                '}';
    }
}
