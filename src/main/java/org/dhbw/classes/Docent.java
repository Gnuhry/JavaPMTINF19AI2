package org.dhbw.classes;

import java.util.Date;
import java.util.Objects;

public class Docent extends Person implements Comparable<Person>{
    private final int docentNumber;

    public Docent(String name, String forename, Date birthday, Address address, String email, int docentNumber) {
        super(name, forename, birthday, address, email);
        this.docentNumber = docentNumber;
    }

    //----------------------Getter--------------------------------
    public int getDocentNumber() {
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
    public int compareTo(Person o) {
        return super.compareTo(o);
    }
}
