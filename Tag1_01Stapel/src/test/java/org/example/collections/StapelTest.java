package org.example.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @Test
    @DisplayName("should return true when isEmpty called on empty Stapel")
    void isEmpty_emptyStack_returnsTrue() {

        // Arrange

        // Action
        var result = objectUnderTest.isEmpty();
        // Assertion

        assertTrue(result);
    }
    @Test
    void isEmpty_notEmptyStack_returnsFalse() throws Exception{

        // Arrange

        objectUnderTest.push(1);
        // Action
        var result = objectUnderTest.isEmpty();
        // Assertion

        assertFalse(result);
    }

    @Test
    void isEmpty_stackEmptyAgain_returnsTrue() throws Exception{

        // Arrange

        objectUnderTest.push(1);
        objectUnderTest.pop();
        // Action
        var result = objectUnderTest.isEmpty();
        // Assertion

        assertTrue(result);
    }
    @Test
    void push_pushValueIntoStapel_ValueIsOnTopOfStapel() throws Exception{

        // Arrange


        // Action
        objectUnderTest.push(1);

        // Assertion
        assertEquals(1, objectUnderTest.pop());
    }
    @Test
    void push_fillupToLimit_noExceptionIsThrown() throws Exception{

        fillUpToLimit();


    }



    @Test
    void push_overflow_throwsStapelException() throws Exception{

        // Arrange
        fillUpToLimit();


        // Action
        StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(1));
        assertEquals("Overflow", ex.getMessage());
    }

    private void fillUpToLimit() {
        // Arrange
        for(int i = 0; i < 10; i++)
            assertDoesNotThrow(()->objectUnderTest.push(1));
    }
}