package org.dhbw.classes;

import java.sql.SQLType;
import java.sql.Types;
import java.util.Date;

public class Main {
    public static void main(String[]args) throws InterruptedException {
        University DHBW=new University("Duale Hochschule Baden-Wüttenberg Mannheim");
        DHBW.addDocent(new Docent("Kruse", "Eckhard", new Date(2000,11,1),new Address("hh","34","2343","Hung","33")));
        System.out.println("TEST");
        Databank.SetToDatabank("INSERT INTO student (`student_id`, `matriculation_number`, `person_id`, `java_knowlage`, `course_id`, `company_id`) VALUES (?, ?, '1', '12', '1', '1');", new Object[]{4,123456}, new int[]{Types.INTEGER, Types.INTEGER});
        Thread.sleep(5000);
        for(Object[] arr2:  Databank.GetFromDatabank("SELECT * FROM student"))
        {
            for(Object o: arr2)
                if(o instanceof Integer)
                    System.out.println((int) o);
                else if (o instanceof String)
                    System.out.print(o);
        }
    }
}
