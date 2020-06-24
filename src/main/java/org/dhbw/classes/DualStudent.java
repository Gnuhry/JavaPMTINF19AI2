package org.dhbw.classes;

import java.util.Date;
import java.util.Objects;

public class DualStudent extends Person implements Comparable<Person> {

    private final Company company;
    private final int matriculationNumber;
    private final int studentNumber;
    private Course course;
    private int javaKnowledge;

    public DualStudent(int matriculationNumber, int studentNumber, String name, String forename, Date birthday, Address address, String email, Course course, int javaKnowledge, Company company) {
        super(name, forename, birthday, address, email);
        this.matriculationNumber = matriculationNumber;
        this.studentNumber = studentNumber;
        this.course = course;
        this.javaKnowledge = javaKnowledge;
        this.company = company;
    }

    public DualStudent(DualStudent ds) {
        super(ds);
        this.company = ds.company;
        this.matriculationNumber = ds.matriculationNumber;
        this.studentNumber = ds.studentNumber;
        this.course = ds.course;
        this.javaKnowledge = ds.javaKnowledge;
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
    public int compareTo(Person o) {
        return super.compareTo(o);
    }
}
