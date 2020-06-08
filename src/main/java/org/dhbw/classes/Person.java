package org.dhbw.classes;

import java.util.Date;
import java.util.Objects;

public class Person {

    private final Date birthday;
    private String name, forename, email;
    private Address address;

    public Person(String name, String forename, String email){
        this.name = name;
        this.forename = forename;
        this.birthday = null;
        this.email = email;
    }

    public Person(String name, String forename, Date birthday, Address address, String email) {
        this.name = name;
        this.forename = forename;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
    }

    //-----------------------------------Getter-------------------------------------------
    public String getName() {
        return name;
    }

    public String getForename() {
        return forename;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    //-----------------------------------Setter-------------------------------------------
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    //-----------------------------------Overrides--------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(birthday, person.birthday) &&
                Objects.equals(name, person.name) &&
                Objects.equals(forename, person.forename) &&
                Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, name, forename, address);
    }


    @Override
    public String toString() {
        return name + ", " + forename + " , " + email;
    }
}
