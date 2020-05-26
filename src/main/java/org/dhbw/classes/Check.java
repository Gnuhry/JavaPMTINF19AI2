package org.dhbw.classes;

import java.util.Calendar;
import java.util.Date;

public class Check {

    public static boolean date(Date date){
        return !date.before(Calendar.getInstance().getTime());
    }
    public static boolean birthdate(Date date){
        return Check.date(date) && Calendar.getInstance().getTime().getTime() - date.getTime() < 11826000000000L; //150 Jahre in Millisekunden
    }
    public static boolean afterBirthday(Date d, Person p){
        return p.getBirthday().before(d);
    }
}
