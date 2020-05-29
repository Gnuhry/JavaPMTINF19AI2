package org.dhbw.classes;

public class Company {
    private String name;
    private Address address;
    private Person contactPerson;

    public Company(String name, Address address, Person contactPerson) {
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
    }

    //-----------------------Getter-------------------------------
    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Person getContactPerson() {
        return contactPerson;
    }

    //--------------------------Setter-------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }
}
