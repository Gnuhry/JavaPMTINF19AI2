package org.dhbw.classes;

import java.util.Objects;

public class Company implements Comparable<Company>{
    private String name;
    private Address address;
    private Person contactPerson;

    public Company(String name, Address address, Person contactPerson) {
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
    }

    public Company(Company company) {
        this.name = company.name;
        this.address = new Address(company.address);
        this.contactPerson = new Person(company.contactPerson);
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

    //---------------------Override-----------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(address, company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Company company) {
        return name == null ? -1 : company.getName() == null ? 1 : name.toLowerCase().compareTo(company.name.toLowerCase());
    }
}
