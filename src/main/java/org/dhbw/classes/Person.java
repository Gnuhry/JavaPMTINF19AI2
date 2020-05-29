package org.dhbw.classes;

import java.util.Date;

public class Person {

    private final Date birthday;
    private String name, forename, email;
    private Address address;

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

    public String getForeName() {
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
}
