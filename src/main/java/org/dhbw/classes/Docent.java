package org.dhbw.classes;

import java.util.Date;

public class Docent extends Person{
    private String docentNumber; //TODO automatic? or manuel with constructer?

    public Docent(String name, String forename, Date birthday, Address address) {
        super(name, forename, birthday, address);
    }

    //----------------------Getter--------------------------------
    public String getDocentNumber() {
        return docentNumber;
    }
}
