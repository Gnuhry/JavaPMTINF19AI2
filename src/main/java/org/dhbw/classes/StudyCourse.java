package org.dhbw.classes;

public enum StudyCourse {
    Info("computer_science"),
    CTech("chemical_eng"),
    EleEng("electrical_eng"),
    IntEng("integrated_engineering"),
    MBau("mechanical_eng"),
    Mechatronics("mechatronics"),
    WWI("industrial_eng"),
    GP("gp"),
    BWL("bwl"),
    Media("media_tech"),
    ATB("atb"),
    WInfo("w_comp");

    private final String name;

    StudyCourse(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return Help.getResourcedBundle().getString(name);
    }
}
