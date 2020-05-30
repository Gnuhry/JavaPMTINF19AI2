package org.dhbw.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University {
    private final List<Docent> docents;
    private final List<DualStudent> student;
    private final List<Course> courses;

    public University() {
        DualStudent[] student_array = Database.loadAllStudents();
        if (student_array != null)
            student = Arrays.asList(student_array);
        else
            student = new ArrayList<>();
        Docent[] docent_array = Database.loadAllDocents();
        if (docent_array != null)
            docents = Arrays.asList(docent_array);
        else
            docents = new ArrayList<>();
        Course[] course_array = Database.loadAllCourses(docent_array);
        if (course_array != null)
            courses = Arrays.asList(course_array);
        else
            courses = new ArrayList<>();
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


    //-------------------------------Add------------------------

    public void addDocent(Docent d) {
        docents.add(d);
        Database.setDocentsToDatabase(new Docent[]{d});
    }

    public void addStudent(DualStudent s) {
        student.add(s);
        Database.setStudentsToDatabase(new DualStudent[]{s});
    }

    public void addCourse(Course c) {
        courses.add(c);
        Database.setCoursesToDatabase(new Course[]{c});
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
        Database.deleteDocent(d, d.getDocentNumber());
    }

    public void removeStudent(DualStudent s) {
        student.remove(s);
        Database.deleteStudent(s, s.getStudentNumber());
    }

    public void removeCourse(Course c) {
        courses.remove(c);
        Database.deleteCourse(c);
    }

    //------------------------------Update-----------------
    public void updateDocent(Docent d) {
        Database.updateDocent(d, d.getDocentNumber());
    }

    public void updateStudent(DualStudent s) {
        Database.updateStudent(s, s.getStudentNumber());
    }

    public void updateCourse(Course c) {
        Database.updateCourse(c);
    }


    public void exmatriculation(DualStudent s) {
        removeStudent(s);
    }
}
