package org.dhbw.classes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    private static Address address;

    @BeforeEach
    public void init() {
        address = new Address("Coblitzallee", "1-9", "68163", "Mannheim", "Deutschland");
    }

    @Test
    public void getStreet() {
        assertEquals(address.getStreet(), "Coblitzallee");
    }

    @Test
    public void getNumber() {
        assertEquals(address.getNumber(), "1-9");
    }

    @Test
    public void getPostcode() {
        assertEquals(address.getPostcode(), "68163");
    }

    @Test
    public void getCity() {
        assertEquals(address.getCity(), "Mannheim");
    }

    @Test
    public void getCountry() {
        assertEquals(address.getCountry(), "Deutschland");
    }

    @Test
    public void setStreet() {
        address.setStreet("Handelsstraße");
        assertNotEquals(address.getStreet(), "Coblitzallee");
        assertEquals(address.getStreet(), "Handelsstraße");
    }

    @Test
    public void setNumber() {
        address.setNumber("13");
        assertNotEquals(address.getNumber(), "1-9");
        assertEquals(address.getNumber(), "13");

    }

    @Test
    public void setPostcode() {
        address.setPostcode("69214");
        assertNotEquals(address.getPostcode(), "68163");
        assertEquals(address.getPostcode(), "69214");
    }

    @Test
    public void setCity() {
        address.setCity("Eppelheim");
        assertNotEquals(address.getCity(), "Mannheim");
        assertEquals(address.getCity(), "Eppelheim");
    }

    @Test
    public void setCountry() {
        address.setCountry("Niemcy");
        assertNotEquals(address.getCountry(), "Deutschland");
        assertEquals(address.getCountry(), "Niemcy");
    }

    @Test
    public void testToString() {
        var s = "Coblitzallee 1-9, 68163 Mannheim, Deutschland";
        assertEquals(address.toString(), s);
    }

    @Test
    public void testEquals() {
        var addressEq = new Address("Coblitzallee", "1-9", "68163", "Mannheim", "Deutschland");
        var addressNotEq = new Address("Handelsstraße", "13,", "69214", "Eppelheim", "Niemcy");

        assertEquals(address, addressEq);
        assertNotEquals(address, addressNotEq);
    }
}