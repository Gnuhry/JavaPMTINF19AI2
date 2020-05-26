package org.dhbw.classes;

import java.util.Date;

public class Person {

    private String name, forename;
    private Date birthday;
    private Address address;

    public Person(String name, String forename, Date birthday, Address address) {
        this.name = name;
        this.forename = forename;
        this.birthday = birthday;
        this.address = address;
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

    //-----------------------------------Setter-------------------------------------------
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * To change the name of the person
     * @param name New name of the person
     * @param forename New forename of the person
     */
    public void rename(String name, String forename){
        this.name=name;
        this.forename=forename;
    }
}
