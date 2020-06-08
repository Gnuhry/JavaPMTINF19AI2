package org.dhbw.classes;

public enum StudyCourse {
    AInformatik("Angewandte Informatik"), BWL("Betriebswirtschaftslehre"), WInformatik("Wirtschaftsinformatik");

    private final String name;

    StudyCourse(String name){
        this.name=name;
    }


    @Override
    public String toString() {
        return name;
    }
}
