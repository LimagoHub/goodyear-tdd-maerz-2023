package org.example.tiere;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchweinTest {

    private final int intial_weight = 10;
    private Schwein objectUnderTest;
    @BeforeEach
    void setUp() {
        objectUnderTest = new Schwein();
    }

    @Test
    void ctor_default_correctInitialised() {
        assertEquals(intial_weight, objectUnderTest.getGewicht());
    }

}