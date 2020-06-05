package org.dhbw.classes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University {


    //-----------------------------Getter---------------------

    public static List<Docent> getDocents() {
        return Database.getDocents();
    }

    public static List<DualStudent> getStudents() {
        return Database.getStudents();
    }

    public static List<Course> getCourses() {
        return Database.getCourses();
    }

    public static List<Company> getCompanies() {
        return Database.getCompanies();
    }


    //-------------------------------Add------------------------

    public static void addDocent(Docent d) {
        Database.addDocent(d);
    }

    public static void addStudent(DualStudent s) {
        Database.addStudent(s);
    }

    public static void addCourse(Course c) {
        Database.addCourse(c);
    }

    public static void addCompany(Company c) {
        Database.addCompany(c);
    }

    //-------------------------------IS-------------------------
    public static boolean isDocent(Docent d) {
        return false;
    }

    public static boolean isStudent(DualStudent s) {
        return false;
    }

    public static boolean isCourse(Course c) {
        return false;
    }

    public static boolean isCompany(Company company) throws ClassNotFoundException {
        return false;
    }

    //-----------------------------Remove----------------------
    public static void removeDocent(Docent d) {
        Database.deleteDocent(d);
    }

    public static void removeStudent(DualStudent s) {
        Database.deleteStudent(s);
    }

    public static void removeCourse(Course c) {
        Database.deleteCourse(c);
    }

    public static void removeCompany(Company c) {
        Database.deleteCompany(c);
    }

    //------------------------------Update-----------------
    public static void updateDocent(Docent d, Docent old) {
        Database.updateDocent(d, old);
    }

    public static void updateStudent(DualStudent s, DualStudent old) {
        Database.updateStudent(s, old);
    }

    public static void updateCourse(Course c, Course old) {
        Database.updateCourse(c, old);
    }

    public static void updateCompany(Company c, Company old) {
        Database.updateCompany(c, old);
    }
}
