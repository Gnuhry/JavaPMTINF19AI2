package org.dhbw.classes;

import java.util.Objects;

public class CourseRoom implements Comparable<CourseRoom>{
    private final String name;
    private Campus campus;
    private String building;
    private String floor;
    private int seats;
    private boolean beamer;
    private boolean documentCamera;
    private boolean laboratory;

    public CourseRoom(String name, Campus campus, String building, String floor, int seats, boolean beamer, boolean documentCamera, boolean laboratory) {
        this.name = name;
        this.campus = campus;
        this.building = building;
        this.floor = floor;
        this.seats = seats;
        this.beamer = beamer;
        this.documentCamera = documentCamera;
        this.laboratory = laboratory;
    }

    public CourseRoom(String name) {this.name = name;}

    //------------------Getter-----------------------
    public String getName() {
        return name;
    }

    public Campus getCampus() {
        return campus;
    }

    //------------------Overrides---------------------


    @Override
    public String toString() {
        return "CourseRoom{" +
                "name='" + name + '\'' +
                ", campus=" + campus +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", seats=" + seats +
                ", beamer=" + beamer +
                ", documentCamera=" + documentCamera +
                ", laboratory=" + laboratory +
                '}';
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
