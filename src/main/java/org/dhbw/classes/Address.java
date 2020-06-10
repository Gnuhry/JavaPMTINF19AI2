package org.dhbw.classes;

import java.util.Objects;

public class Address implements Comparable<Address> {

    private String street;
    private String number;
    private String postcode;
    private String city;
    private String country;

    public Address(String street, String number, String postcode, String city, String country) {
        this.street = street;
        this.number = number;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }

    //-----------------------------------------Getter------------------------------------------------
    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    //-----------------------------------Setter-----------------------------------------------
    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    //-------------------------------------Override Methods-------------------------------------
    @Override
    public String toString() {
        return street + " " + number + ", " + postcode + " " + city + ", " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(postcode, address.postcode) &&
                Objects.equals(city, address.city) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, postcode, city, country);
    }


    @Override
    public int compareTo(Address o) {
        if (country == null)
            return -1;
        if (o.getCountry() == null)
            return 1;
        int compare = country.toLowerCase().compareTo(o.getCountry().toLowerCase());
        if (compare != 0) return compare;

        if (city == null)
            return -1;
        if (o.getCity() == null)
            return 1;
        compare = city.toLowerCase().compareTo(o.getCity().toLowerCase());
        if (compare != 0) return compare;

        if (street == null)
            return -1;
        if (o.getStreet() == null)
            return 1;
        compare = street.toLowerCase().compareTo(o.getStreet().toLowerCase());
        return compare != 0 ? compare : number == null ? -1 : o.getNumber() == null ? 1 : number.toLowerCase().compareTo(o.getNumber().toLowerCase());
    }
}
