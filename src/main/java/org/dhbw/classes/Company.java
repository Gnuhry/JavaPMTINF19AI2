package org.dhbw.classes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.dhbw.ShowStudentsController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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
}
