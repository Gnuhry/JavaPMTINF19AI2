package org.dhbw.classes;

import java.util.List;

public class University {


    //-----------------------------GetObject---------------------

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

    public static List<CourseRoom> getRooms() {
        return Database.getRooms();
    }

    public static String[] getAllEmailFromCourse(Course course) {
        return Database.getAllEmailsFromCourse(course);
    }

    //-------------------------------AddObject------------------------

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

    public static void addRoom(CourseRoom r) {
        Database.addRoom(r);
    }

    //-----------------------------RemoveObject----------------------
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

    public static void removeRoom(CourseRoom c) {
        Database.deleteRoom(c);
    }

    //------------------------------UpdateObject-----------------
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

    public static void updateRoom(CourseRoom r, CourseRoom old) {
        Database.updateRoom(r, old);
    }
}
