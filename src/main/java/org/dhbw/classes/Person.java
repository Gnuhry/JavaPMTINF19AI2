package org.dhbw.classes;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

    private Date birthday;
    private String name, forename, email;
    private Address address;

    public Person (String name, String forename, String email) {
        this.name = name;
        this.forename = forename;
        this.email=email;
    }

    public Person(String name, String forename, Date birthday, Address address) {
        this.name = name;
        this.forename = forename;
        this.birthday = birthday;
        this.address = address;
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

    /**
     * To change the name of the person
     *
     * @param name     New name of the person
     * @param forename New forename of the person
     */
    public void rename(String name, String forename) {
        this.name = name;
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

//    @Override
//    public String toString() {
//        return "Person{" +
//                "birthday=" + birthday +
//                ", name='" + name + '\'' +
//                ", forename='" + forename + '\'' +
//                ", email='" + email + '\'' +
//                ", address=" + address +
//                '}';
//    }


    @Override
    public String toString() {
        return name + ", " + forename;
    }
}
