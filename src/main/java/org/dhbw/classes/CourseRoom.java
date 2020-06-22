package org.dhbw.classes;

import java.util.Objects;

public class CourseRoom implements Comparable<CourseRoom> {
    private final String name, building, floor;
    private int seatsAmount;
    private boolean documentCamera, laboratory;

    public CourseRoom(String name, String building, String floor) {
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.seatsAmount = 0;
        documentCamera = laboratory = false;
    }

    public CourseRoom(String name, String building, String floor, int seatsAmount, boolean documentCamera, boolean laboratory) {
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.seatsAmount = seatsAmount;
        this.documentCamera = documentCamera;
        this.laboratory = laboratory;
    }

    //------------------Getter-----------------------
    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public int getSeatsAmount() {
        return seatsAmount;
    }

    public boolean hasDocumentCamera() {
        return documentCamera;
    }

    public boolean isLaboratory() {
        return laboratory;
    }

    //-----------------------Setter-----------------
    public void setSeatsAmount(int seatsAmount) {
        this.seatsAmount = seatsAmount;
    }

    public void setDocumentCamera(boolean documentCamera) {
        this.documentCamera = documentCamera;
    }

    public void setLaboratory(boolean laboratory) {
        this.laboratory = laboratory;
    }

    //------------------Overrides---------------------
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRoom room = (CourseRoom) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(CourseRoom o) {
        return name == null ? -1 : o.getName() == null ? 1 : name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}
