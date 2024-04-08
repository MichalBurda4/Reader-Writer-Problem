package org.example;

//import java.util.Random;
import java.util.logging.Logger;
/**
 * Klasa reprezentująca czytelnika, który korzysta z biblioteki do czytania i oczekiwania.
 */
class Reader extends Thread {

    /** Biblioteka, z której korzysta czytelnik. */
    private final Library library;
    ///** Generator liczb losowych dla opóźnień. */
    //private final Random random = new Random();
    /** Imię czytelnika. */
    private final String name;
    /** Logger do rejestrowania komunikatów. */
    private final Logger logger = Logger.getLogger("Logger");
    /**
     * Konstruktor tworzący nowego czytelnika.
     *
     * @param name    Imię czytelnika.
     * @param library Biblioteka, z której korzysta czytelnik.
     */
    public Reader(String name, Library library) {
        this.name = name;
        this.library = library;
    }
    /**
     * Metoda wykonująca działania czytelnika w pętli nieskończonej - czytania i oczekiwania.
     */
    @Override
    public void run() {
        try {
            while (true) {
                library.requestRead(name);
                //Deklaracja zmiennych do odczytu liczby pisarzy i czytelników
                int readersCount = this.library.getReadersCount();
                int writersCount = this.library.getWritersCount();
                int readersInQue = this.library.getReadersQueue();
                int writersInQue = this.library.getWritersQueue();

                //Czytelnik wchodzi do czytelni
                logger.info(name + " czyta " +
                        "Liczba czytelnikow w czytelni: " + readersCount +
                        " Liczba czytelnikow w kolejce: " + readersInQue +
                        " Liczba pisarzy w czytelni: " + writersCount +
                        " Liczba pisarzy w kolejce: " + writersInQue +"\n");
                Thread.sleep(2000);
//                int readingTime = random.nextInt(3) + 1;
//                long sleepTime1 = (long) readingTime * 1000;
//                Thread.sleep(sleepTime1); // opóźnienie

                //Czytelnik opuszcza czytelnie
                logger.info(name + " opuszcza czytelnie " +
                        "Liczba czytelnikow w czytelni: " + readersCount +
                        " Liczba czytelnikow w kolejce: " + readersInQue +
                        " Liczba pisarzy w czytelni: " + writersCount +
                        " Liczba pisarzy w kolejce: " + writersInQue +"\n");
                library.finishRead(name);
                Thread.sleep(1000);
//                int waitingTime = random.nextInt(3) + 1;
//                long sleepTime2 = (long) waitingTime * 1000;
//                Thread.sleep(sleepTime2);


            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
