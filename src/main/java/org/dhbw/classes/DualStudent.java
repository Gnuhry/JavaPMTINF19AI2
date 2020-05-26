package org.dhbw.classes;

import java.util.Date;

public class DualStudent extends Student{
    private Company company;
    public DualStudent(String name, String forename, Date birthday, Address address, Company company) {
        super(name, forename, birthday, address);
        this.company=company;
    }

    //--------------------------Getter--------------------------
    public Company getCompany() {
        return company;
    }
}
