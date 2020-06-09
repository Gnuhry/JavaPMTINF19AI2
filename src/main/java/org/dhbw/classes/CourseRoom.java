package org.dhbw.classes;

public class CourseRoom {
    private final String name;

    public CourseRoom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
