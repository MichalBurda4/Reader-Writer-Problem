package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class LibraryTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }
    @Test
    void requestRead_ShouldIncrementReadersCount() throws InterruptedException {
        // Arrange
        Library library = new Library();

        // Act
        library.requestRead("Czytelnik");

        // Assert
        assertEquals(1, library.getReadersCount());
    }

    @Test
    void requestWrite_ShouldIncrementWritersCount() throws InterruptedException {
        // Arrange
        Library library = new Library();

        // Act
        library.requestWrite("Pisarz");

        // Assert
        assertEquals(1, library.getWritersCount());
    }
    @Test
    void testInitialCounts() {
        assertEquals(0, library.getReadersCount());
        assertEquals(0, library.getWritersCount());
    }
    @Test
    void testRequestReadAndFinishRead() throws InterruptedException {
        library.requestRead("Czytelnik1");
        assertEquals(1, library.getReadersCount());

        library.finishRead("Czytelnik1");
        assertEquals(0, library.getReadersCount());
    }



}