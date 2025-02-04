package com.saxonica.bugs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void generate() {
        assertEquals("excellent", Main.generate(), "Generates the correct message");
    }
}