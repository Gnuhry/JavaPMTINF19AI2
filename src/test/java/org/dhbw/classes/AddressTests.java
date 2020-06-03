package org.dhbw.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddressTests {

    @Test
    @DisplayName("Create new Address Object")
    public void createNewAddress() {
        Address a = new Address("Coblitzallee", "1-9,", "68163", "Mannheim", "Deutschland");
        assertNotNull(a);
    }
}
