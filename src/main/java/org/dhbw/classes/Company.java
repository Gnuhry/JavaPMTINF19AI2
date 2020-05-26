package org.dhbw.classes;

public class Company {
    private String name;
    private Person owner;
    private Address address;
    private Person contactPerson;

    public Company(String name, Person owner, Address address, Person contactPerson) {
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.contactPerson = contactPerson;
    }

    //-----------------------Getter-------------------------------
    public String getName() {
        return name;
    }

    public Person getOwner() {
        return owner;
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

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }
}
