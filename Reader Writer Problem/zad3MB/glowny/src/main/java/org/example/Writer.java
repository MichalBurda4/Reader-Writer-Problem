package org.example;

import java.util.Random;
import java.util.logging.Logger;

public class Writer extends Thread {
    /** Biblioteka, z której korzysta czytelnik. */
    private final Library library;
    private final Random random = new Random();
    /** Imię pisarza. */
    private final String name;
    /** Logger do rejestrowania komunikatów. */
    private final Logger logger = Logger.getLogger("Logger");
    /**
     * Konstruktor tworzący nowego pisarza.
     *
     * @param name    Imię pisarza.
     * @param library Biblioteka, z której korzysta pisarz.
     */
    public Writer(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    /**
     * Metoda wykonująca działania pisarza w pętli nieskończonej - pisania i oczekiwania.
     */
    @Override
    public void run() {
        try {
            while (true) {
                library.requestWrite(name);
                //Deklaracja zmiennych do odczytu liczby pisarzy i czytelników
                int readersCount = this.library.getReadersCount();
                int writersCount = this.library.getWritersCount();
                int readersInQue = this.library.getReadersQueue();
                int writersInQue = this.library.getWritersQueue();

                //Pisarz wchodzi do czytelni
                logger.info(name + " pisze " +
                        "Liczba czytelnikow w czytelni: " + readersCount +
                        " Liczba czytelnikow w kolejce: " + readersInQue +
                        " Liczba pisarzy w czytelni: " + writersCount +
                        " Liczba pisarzy w kolejce: " + writersInQue +"\n");
                Thread.sleep(2500);
//                int writingTime = random.nextInt(3) + 1;
//                long sleepTime1 = (long) writingTime * 1000;
//                Thread.sleep(sleepTime1); // opóźnienie

                //Pisarz opuszcza czytelnie
                logger.info(name + " opuszcza czytelnie " +
                        "Liczba czytelnikow w czytelni: " + readersCount +
                        " Liczba czytelnikow w kolejce: " + readersInQue +
                        " Liczba pisarzy w czytelni: " + writersCount +
                        " Liczba pisarzy w kolejce: " + writersInQue +"\n");
                library.finishWrite(name);
                Thread.sleep(700);
//                int waitingTime = random.nextInt(3) + 1;
//                long sleepTime2 = (long) waitingTime * 1000;
//                Thread.sleep(sleepTime2); //opóźnienie
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


