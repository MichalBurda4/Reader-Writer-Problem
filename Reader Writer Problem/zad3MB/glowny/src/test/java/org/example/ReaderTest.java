package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import org.awaitility.Awaitility;


public class ReaderTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }
    @Test
    void testReaderFinishing() {
        Reader reader = new Reader("TestReader", library);
        reader.start();

        Awaitility.await().atMost(5000, MILLISECONDS).until(() -> library.getReadersCount() == 0);

        assertEquals(0, library.getReadersCount());
    }

    @Test
    void testReaderInterrupted() {
        Reader reader = new Reader("TestReader", library);
        reader.start();
        reader.interrupt();

        Awaitility.await().atMost(5000, MILLISECONDS).until(() -> library.getReadersCount() == 0);

        assertEquals(0, library.getReadersCount());
    }

}


