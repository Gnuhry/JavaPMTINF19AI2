package org.dhbw.classes;

import java.util.Date;

public class Main {
    public static void main(String[]args){
        University DHBW=new University("Duale Hochschule Baden-Wüttenberg Mannheim");
        DHBW.addDocent(new Docent("Kruse", "Eckhard", new Date(2000,11,1),new Address("hh","34","2343","Hung","33")));
        System.out.println("TEST");
    }
}
