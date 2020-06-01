package org.dhbw.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University {

    public University() {
    }


    //-----------------------------Getter---------------------

    public List<Docent> getDocents() {
        Docent[] docent_array = Database.loadAllDocents();
        if (docent_array != null)
            return Arrays.asList(docent_array);
        else
            return new ArrayList<>();
    }

    public List<DualStudent> getStudents() {
        DualStudent[] student_array = Database.loadAllStudents();
        if (student_array != null)
            return Arrays.asList(student_array);
        else
            return new ArrayList<>();
    }

    public List<Course> getCourses() {
        Course[] course_array = Database.loadAllCourses(Database.loadAllDocents());
        if (course_array != null)
            return Arrays.asList(course_array);
        else
            return new ArrayList<>();
    }


    //-------------------------------Add------------------------

    public void addDocent(Docent d) {
        Database.setDocentsToDatabase(new Docent[]{d});
    }

    public void addStudent(DualStudent s) {
        Database.setStudentsToDatabase(new DualStudent[]{s});
    }

    public void addCourse(Course c) {
        Database.setCoursesToDatabase(new Course[]{c});
    }

    //-------------------------------IS-------------------------
    public boolean isDocent(Docent d) {
        int d2 = Database.getDocentID(d);
        return d2 != Integer.MIN_VALUE;
    }

    public boolean isStudent(DualStudent s) {
        int s2 = Database.getStudentID(s);
        return s2 != Integer.MIN_VALUE;
    }

    public boolean isCourse(Course c) {
        int c2 = Database.getCourseID(c);
        return c2 != Integer.MIN_VALUE;
    }

    //-----------------------------Remove----------------------
    public void removeDocent(Docent d) {
        Database.deleteDocent(d, d.getDocentNumber());
    }

    public void removeStudent(DualStudent s) {
        Database.deleteStudent(s, s.getStudentNumber());
    }

    public void removeCourse(Course c) {
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
