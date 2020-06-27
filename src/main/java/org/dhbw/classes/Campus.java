package org.dhbw.classes;

public enum Campus {
    Neuostheim("Coblitzalle Neuostheim"), Kaefertal("Käfertaler Straße"), Eppelheim("Eppelheim");

    private final String name;

    Campus(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
