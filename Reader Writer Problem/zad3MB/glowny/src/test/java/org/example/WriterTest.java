package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
class WriterTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    void testWriterWriting() {
        Writer writer = new Writer("TestWriter", library);
        writer.start();

        Awaitility.await().atMost(5000, TimeUnit.MILLISECONDS).until(() -> library.getWritersCount() == 0);

        assertEquals(0, library.getWritersCount());
    }

    @Test
    void testWriterLeavingLibrary() {
        Writer writer = new Writer("TestWriter", library);
        writer.start();

        Awaitility.await().atMost(5000, TimeUnit.MILLISECONDS).until(() -> library.getWritersCount() == 0);

        assertEquals(0, library.getWritersCount());
        assertEquals(0, library.getReadersCount());
    }

    @Test
    void testWriterInterrupted() {
        Writer writer = new Writer("TestWriter", library);
        writer.start();
        writer.interrupt();

        Awaitility.await().atMost(5000, TimeUnit.MILLISECONDS).until(() -> library.getWritersCount() == 0);

        assertEquals(0, library.getWritersCount());
    }

}