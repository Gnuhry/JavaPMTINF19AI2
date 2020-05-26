package org.dhbw.classes;

public class Address {

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

    @Override
    public String toString() {
        return street+" "+number+", "+postcode+" "+city+", "+country;
    }
}
