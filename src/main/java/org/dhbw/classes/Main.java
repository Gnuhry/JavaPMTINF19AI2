package org.dhbw.classes;

import java.sql.SQLException;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
//        University DHBW = new University();
//        Calendar c = Calendar.getInstance();
//        c.set(2000, Calendar.NOVEMBER, 1);
//        DHBW.addDocent(new Docent("Kruse", "Eckhard", c.getTime(), new Address("hh", "34", "2343", "Hung", "33")));
//        System.out.println("TEST");
//        Database.setToDatabank("ALTER TABLE docent CHANGE perosn_id person_id int(11)", null, null);
//        Database.setToDatabank("INSERT INTO student (`student_id`, `matriculation_number`, `person_id`, `java_knowlage`, `course_id`, `company_id`) VALUES (?, ?, '1', '12', '1', '1');", new Object[]{6, 1234567890}, new int[]{Types.INTEGER, Types.INTEGER});
//        Database.setToDatabank("INSERT INTO person (`person_id`, `first_name`, `last_name`, `birthdate`, `email`, `adress_id`) VALUES (?, ?, ?, ?, ?, ?);", new Object[]{1,"Jannik", "Junker", new Date(200,1,1), "jannik@junker.com", 1234567890}, new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER});
//        Database.setToDatabank("INSERT INTO adress (`adress_id`, `street`, `number`, `postal_code`, `city`, `country`) VALUES (?, ?, ?, ?, ?, ?);", new Object[]{1234567890,"Holzwiesenweg", "38a","546373", "Hungen", "Germany"}, new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
//        Thread.sleep(5000);
//        printout(Database.getFromDatabase("SELECT * FROM student"));
//       printout(Database.getFromDatabase("SELECT * FROM student INNER JOIN person ON student.person_id = person.person_id INNER JOIN address ON person.address_id=address.address_id"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'student'"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'person'"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'adress'"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'course'"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'company'"));
//        printout(Database.getFromDatabase("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'docent'"));
//        printout(Database.getFromDatabase("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='dhbw' "));
//        for(Student s:Database.loadStudent())
//            System.out.println(s.getName());
        Calendar c = Calendar.getInstance();
        c.set(2000, Calendar.NOVEMBER, 1, 0, 0, 0);
        Calendar c2 = Calendar.getInstance();
        c2.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar c3 = Calendar.getInstance();
        c3.set(2020, Calendar.JANUARY, 1, 0, 0, 0);
         try {
             Database.setStudent(new DualStudent(9874985, 7485688, "Wessely", "Silas", c.getTime(), new Address("Darmstädter Straße", "45", "78459", "Hanau", "Deutschland"), "silas.wessely@gmx.de", new Course("TINF19AI2", StudyCourse.AInformatik,
                     new Docent("Hoffmann", "Prof. Dr. Holger", c2.getTime(), new Address("Baumgartenstraße", "23a", "64738", "Stuttgart", "Deutschland"), "holger@hoffmann.de", 879546), 0, c3.getTime(), CourseRoom.A222),
                     78, new Company("Alnatura Produktions- und Handels GmbH", new Address("Mahatma-Gandhi-Straße", "7", "64295", "Darmstadt", "Deutschland"), new Person("Kasch", "Rüdiger", "kasch@alnatura.de"))));
             for (DualStudent dualStudent : Database.getAllStudent()) {
                System.out.println(dualStudent);
                System.out.println(dualStudent.getCompany());
                System.out.println(dualStudent.getCourse());
                System.out.println(dualStudent.getAddress());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            System.out.println("Test");
        }

    }

    private static void printout(Object[][] help) {
        if (help != null) {
            StringBuilder sb;
            for (Object[] arr2 : help) {
                sb = new StringBuilder();
                for (Object o : arr2) {
                    if (o instanceof Integer)
                        sb.append((int) o);
                    else if (o instanceof String)
                        sb.append(o);
                    else {
                        try {
                            sb.append(o.toString());
                        } catch (Exception ignored) {

                        }
                    }
                    sb.append(" | ");
                }
                System.out.println(sb.toString());
            }
        }
    }
}
