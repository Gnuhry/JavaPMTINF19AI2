package org.dhbw.classes;

import java.sql.SQLType;
import java.util.Date;

public class Main {
    public static void main(String[]args){
        University DHBW=new University("Duale Hochschule Baden-Wüttenberg Mannheim");
        DHBW.addDocent(new Docent("Kruse", "Eckhard", new Date(2000,11,1),new Address("hh","34","2343","Hung","33")));
        System.out.println("TEST");
        //Databank.setToDatabank("INSERT INTO student (`student_id`, `matriculation_number`, `person_id`, `java_knowlage`, `course_id`, `company_id`) VALUES ('2', '213221', '1', '12', '1', '1');", null, new SQLType[0]);
        System.out.println(Databank.getFromDatabank("SELECT * FROM student")[0][3]);
    }
}
