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
    private Button changeButton;
    public Button deleteButton;

    Class<?> clazz = this.getClass();
    InputStream inputEdit = clazz.getResourceAsStream("/org/dhbw/images/editButton.png");
    Image imageEdit = new Image(inputEdit);
    InputStream inputDelete = clazz.getResourceAsStream("/org/dhbw/images/deleteButton.png");
    Image imageDelete = new Image(inputDelete);

    public Company(String name, Address address, Person contactPerson) {
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.changeButton = new Button();
        this.deleteButton = new Button();
        this.changeButton.setOnAction((ActionEvent event) -> {
            try {
                changeButton = new ShowStudentsController().addFunction(this.changeButton, this, "editCompany");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        changeButton.setGraphic(new ImageView(imageEdit));
        this.deleteButton.setOnAction((ActionEvent event) -> {
            University.removeCompany(this);
        });
        deleteButton.setGraphic(new ImageView(imageDelete));
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

    public Button getChangeButton() {return changeButton;}

    public Button getDeleteButton() {return deleteButton;}

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

    public void setChangeButton(Button changeButton) {this.changeButton = changeButton;}

    public void setDeleteButton(Button deleteButton) {this.deleteButton = deleteButton;}

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

    //    @Override
//    public String toString() {
//        return "Company{" +
//                "name='" + name + '\'' +
//                ", address=" + address +
//                ", contactPerson=" + contactPerson +
//                '}';
//    }
}
