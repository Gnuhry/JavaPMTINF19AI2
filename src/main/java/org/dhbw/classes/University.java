package org.dhbw.classes;

import java.util.ArrayList;
import java.util.List;

public class University {
    private final List<Docent> docents;
    private final List<DualStudent> student;
    private final List<Course> courses;
    private String name;
    private Docent director;

    public University(String name) {
        docents = new ArrayList<>();
        student = new ArrayList<>();
        courses = new ArrayList<>();
        this.name = name;
    }

    //--------------------------------Setter---------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(Docent director) {
        this.director = director;
    }

    //-----------------------------Getter---------------------

    public List<Docent> getDocents() {
        return docents;
    }

    public List<DualStudent> getStudent() {
        return student;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getName() {
        return name;
    }

    public Docent getDirector() {
        return director;
    }

    //-------------------------------Add------------------------

    public void addDocent(Docent d) {
        docents.add(d);
    }

    public void addStudent(DualStudent s) {
        student.add(s);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    //-------------------------------IS-------------------------
    public boolean isDocent(Docent d) {
        return docents.contains(d);
    }

    public boolean isStudent(DualStudent s) {
        return student.contains(s);
    }

    public boolean isCourse(Course c) {
        return courses.contains(c);
    }

    //-----------------------------Remove----------------------
    public void removeDocent(Docent d) {
        docents.remove(d);
    }

    public void removeStudent(DualStudent s) {
        student.remove(s);
    }

    public void removeCourse(Course c) {
        courses.remove(c);
    }


    public void exmatriculation(DualStudent s) {
        removeStudent(s);
    }
}
