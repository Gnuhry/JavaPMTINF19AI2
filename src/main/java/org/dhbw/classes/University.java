package org.dhbw.classes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University {


    //-----------------------------Getter---------------------

    public static List<Docent> getDocents() {
        Docent[] docent_array = new Docent[0];
        try {
            docent_array = Database.getAllDocent();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (docent_array != null)
            return Arrays.asList(docent_array);
        return new ArrayList<>();
    }

    public static List<DualStudent> getStudents() {
        DualStudent[] student_array = new DualStudent[0];
        try {
            student_array = Database.getAllStudent();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (student_array != null)
            return Arrays.asList(student_array);
        return new ArrayList<>();
    }

    public static List<Course> getCourses() {
        Course[] course_array = new Course[0];
        try {
            course_array = Database.getAllCourse();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (course_array != null)
            return Arrays.asList(course_array);
        return new ArrayList<>();
    }

    public static List<Company> getCompanies() {
        Company[] company_array = new Company[0];
        try {
            company_array = Database.getAllCompany();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (company_array != null)
            return Arrays.asList(company_array);
        return new ArrayList<>();
    }

    public static List<Company> getCompany() {
        Company[] company_array = new Company[0];
        try {
            company_array = Database.getAllCompany();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (company_array != null) return  Arrays.asList(company_array);
        else return new ArrayList<>();
    }


    //-------------------------------Add------------------------

    public static void addDocent(Docent d) {
        try {
            Database.setDocent(d);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addStudent(DualStudent s) {
        try {
            Database.setStudent(s);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addCourse(Course c) {
        try {
            Database.setCourse(c);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addCompany(Company c) {
        try {
            Database.setCompany(c);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //-------------------------------IS-------------------------
    public static boolean isDocent(Docent d) {
        try {
            return Database.hasDocent(d);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean isStudent(DualStudent s) {
        try {
            return Database.hasStudent(s);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean isCourse(Course c) {
        try {
            return Database.hasCourse(c);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean isCompany(Company company) throws ClassNotFoundException {
        try {
            return Database.hasCompany(company);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //-----------------------------Remove----------------------
    public static void removeDocent(Docent d) {
        try {
            Database.deleteDocent(d);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeStudent(DualStudent s) {
        try {
            Database.deleteStudent(s);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeCourse(Course c) {
        try {
            Database.deleteCourse(c);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeCompany(Company c) {
        try {
            Database.deleteCompany(c);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //------------------------------Update-----------------
    public static void updateDocent(Docent d) {
        try {
            Database.updateDocent(d, d.getDocentNumber());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateStudent(DualStudent s) {
        try {
            Database.updateStudent(s, s.getStudentNumber());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateCourse(Course c) {
        try {
            Database.updateCourse(c, Database.getCourseID(c));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateCompany(Company c) throws ClassNotFoundException {
        try {
            Database.updateCompany(c, Database.getCompanyID(c));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void exmatriculation(DualStudent s) {
        removeStudent(s);
    }
}
