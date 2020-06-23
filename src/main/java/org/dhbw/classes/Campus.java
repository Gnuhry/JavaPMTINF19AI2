package org.dhbw.classes;

public enum Campus {
    Coblitzalle("Coblitzallee"), Kaefertal("Käfertal"), Eppelheim("Eppelheim");

    private final String name;

    Campus(String name) {this.name = name;}


    @Override
    public String toString() {return name;}
}
