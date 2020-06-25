package org.dhbw.classes;

public enum StudyCourse {
    Info("Informatik"),
    CTech("Chemische_Technik"),
    ETect("Elektrotechnik"),
    IntEng("Integrated_Engineering"),
    MBau("Maschinenbau"),
    Mecha("Mechatronik"),
    WWI("Wirtschaftsingenieurwesen"),
    GP("GP"),
    BWL("BWL"),
    Media("Medien"),
    RSW("RSW"),
    WInfo("WINFO");

    private final String name;

    StudyCourse(String name){
        this.name=name;
    }


    @Override
    public String toString() {
        return Help.getResourcedBundle().getString(name);
    }
}
