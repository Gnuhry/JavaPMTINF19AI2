package org.dhbw.classes;

public enum Campus {
    AllCampus(Help.getRessourceBundle().getString("all_campus")), Coblitzalle("Coblitzallee"), Kaefertal("Käfertal"), Eppelheim("Eppelheim");

    private final String name;

    Campus(String name) {this.name = name;}


    @Override
    public String toString() {return name;}
}
