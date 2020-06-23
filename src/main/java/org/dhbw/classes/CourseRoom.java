package org.dhbw.classes;

import java.util.Objects;

public class CourseRoom implements Comparable<CourseRoom> {
    private final String name;
    private final Campus campus;
    private final String building, floor;
    private int seats;
    private boolean projector, documentCamera, laboratory;

    public CourseRoom(String name, Campus campus, String building, String floor, int seats, boolean projector, boolean documentCamera, boolean laboratory) {
        this.name = name;
        this.campus = campus;
        this.building = building;
        this.floor = floor;
        this.seats = seats;
        this.projector = projector;
        this.documentCamera = documentCamera;
        this.laboratory = laboratory;
    }

    public CourseRoom(String name, Campus campus, String building, String floor) {
        this.name = name;
        this.campus = campus;
        this.building = building;
        this.floor = floor;
        seats = 0;
        projector = documentCamera = laboratory = false;
    }

    //------------------Getter-----------------------
    public String getName() {
        return name;
    }

    public Campus getCampus() {
        return campus;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public int getSeats() {
        return seats;
    }

    public boolean getCamera() {
        return documentCamera;
    }

    public boolean getLaboratory() {
        return laboratory;
    }

    public boolean getProjector() {
        return projector;
    }


    //-----------------------Setter-----------------
    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setCamera(boolean documentCamera) {
        this.documentCamera = documentCamera;
    }

    public void setLaboratory(boolean laboratory) {
        this.laboratory = laboratory;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    //------------------Overrides---------------------


    @Override
    public String toString() {
        return name + ", " + campus;
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
