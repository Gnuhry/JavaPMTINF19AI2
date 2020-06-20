package org.dhbw.classes;

public enum StudyCourse {
    AInfo("AInfo"), BWL("BWL"), WInfo("WInfo");

    private final String name;

    StudyCourse(String name){
        this.name=name;
    }


    @Override
    public String toString() {
        return Help.getRessourceBundle().getString(name);
    }
}
