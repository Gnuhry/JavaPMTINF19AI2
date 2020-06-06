package org.dhbw.classes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.dhbw.ShowStudentsController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class Docent extends Person{
    private int docentNumber;
    private Button changeButton;
    public Button deleteButton;

    Class<?> clazz = this.getClass();
    InputStream inputEdit = clazz.getResourceAsStream("/org/dhbw/images/editButton.png");
    Image imageEdit = new Image(inputEdit);
    InputStream inputDelete = clazz.getResourceAsStream("/org/dhbw/images/deleteButton.png");
    Image imageDelete = new Image(inputDelete);

    public Docent(String name, String forename, Date birthday, Address address) {
        super(name, forename, birthday, address);
    }

    public Docent(String name, String forename, Date birthday, Address address, String email, int docentNumber) {
        super(name, forename, birthday, address, email);
        this.docentNumber = docentNumber;
        this.changeButton = new Button();
        this.deleteButton = new Button();
        this.changeButton.setOnAction((ActionEvent event) -> {
            try {
                changeButton = new ShowStudentsController().addFunction(this.changeButton, this, "editLecture", "lecture");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        changeButton.setGraphic(new ImageView(imageEdit));
        this.deleteButton.setOnAction((ActionEvent event) -> {
            try {
                deleteButton = new ShowStudentsController().addFunction(this.deleteButton, this, "acceptDelete", "lecture");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteButton.setGraphic(new ImageView(imageDelete));
    }

    //----------------------Setter--------------------------------
    public void setChangeButton(Button changeButton) {
        this.changeButton = changeButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    //----------------------Getter--------------------------------
    public int getDocentNumber() {
        return docentNumber;
    }

    public Button getChangeButton() {
        return changeButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
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

//    @Override
//    public String toString() {
//        return "Docent{" +
//                "person=" + super.toString() +
//                "docentNumber='" + docentNumber + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return super.toString();
    }
}
