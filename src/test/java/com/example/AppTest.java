package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void greetWithName() {
        assertEquals("Hello, Alice!", App.greet("alice"));
    }

    @Test
    void greetWithBlankName() {
        assertEquals("Hello, World!", App.greet("  "));
    }

    @Test
    void toJsonContainsMessage() throws Exception {
        assertTrue(App.toJson("bob").contains("Hello, Bob!"));
    }
}
