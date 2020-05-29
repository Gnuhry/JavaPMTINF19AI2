package org.dhbw.classes;

import java.util.Date;
import java.util.Objects;

public class Docent extends Person{
    private String docentNumber; //TODO automatic? or manuel with constructer?

    public Docent(String name, String forename, Date birthday, Address address) {
        super(name, forename, birthday, address);
    }

    public Docent(String name, String forename, Date birthday, Address address, String email, String docentNumber) {
        super(name, forename, birthday, address, email);
        this.docentNumber = docentNumber;
    }

    //----------------------Getter--------------------------------
    public String getDocentNumber() {
        return docentNumber;
    }

    //---------------------Overrides---------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docent docent = (Docent) o;
        return Objects.equals(docentNumber, docent.docentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docentNumber);
    }

    @Override
    public String toString() {
        return "Docent{" +
                "person=" + super.toString() +
                "docentNumber='" + docentNumber + '\'' +
                '}';
    }
}
