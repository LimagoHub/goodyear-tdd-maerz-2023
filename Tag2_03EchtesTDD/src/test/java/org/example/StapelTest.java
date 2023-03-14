package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }



    @Test
    void isEmpty_emptyStack_returnsTrue() {
        assertTrue(objectUnderTest.isEmpty());
    }

    @Test
    void isEmpty_notEmptyStack_returnsFalse() {
        objectUnderTest.push(1);
        assertFalse(objectUnderTest.isEmpty());
    }
}
