package org.example;

import java.util.concurrent.Semaphore;


/**
 * Klasa reprezentująca bibliotekę, gdzie czytelnicy i pisarze mogą korzystać z zasobów zgodnie z zasadami
 * semaforów.
 */
public class Library {
    private final Semaphore readerSema = new Semaphore(5);
    private final Semaphore writerSema = new Semaphore(1);

    private int readersCount = 0;
    private int writersCount = 0;

    /**
     * Metoda wywoływana przez czytelnika, aby zarezerwować dostęp do czytania w bibliotece.
     *
     * @param readerName Nazwa czytelnika.
     * @throws InterruptedException Jeśli wątek zostanie przerwany podczas oczekiwania na dostęp do zasobu.
     */
    public void requestRead(String readerName) throws InterruptedException {
        readerSema.acquire();
        readersCount++;
    }

    /**
     * Metoda wywoływana przez czytelnika, aby zakończyć czytanie i zwolnić dostęp do biblioteki.
     *
     * @param readerName Nazwa czytelnika.
     */
    public void finishRead(String readerName){
        readerSema.release();
        readersCount--;
    }

    /**
     * Metoda wywoływana przez pisarza, aby zarezerwować dostęp do pisania w bibliotece.
     *
     * @param writerName Nazwa pisarza.
     * @throws InterruptedException Jeśli wątek zostanie przerwany podczas oczekiwania na dostęp do zasobu.
     */
    public void requestWrite(String writerName) throws InterruptedException {
        writerSema.acquire();
        readerSema.acquire(5);
        writersCount++;
    }

    /**
     * Metoda wywoływana przez pisarza, aby zakończyć pisanie i zwolnić dostęp do biblioteki.
     *
     * @param writerName Nazwa pisarza.
     */
    public void finishWrite(String writerName){
        writerSema.release();
        readerSema.release(5);
        writersCount--;
    }

    /**
     * Metoda zwracająca liczbę czytelników w kolejce do biblioteki.
     *
     * @return Liczba czytelników w kolejce.
     */
    public int getReadersQueue() {
        int readersInQue = Main.lc - readersCount;
        return readersInQue;
    }

    /**
     * Metoda zwracająca liczbę pisarzy w kolejce do biblioteki.
     *
     * @return Liczba pisarzy w kolejce.
     */
    public int getWritersQueue() {
        int writersInQue = Main.lp - writersCount;
        return writersInQue;
    }

    /**
     * Metoda zwracająca liczbę aktualnych czytelników w bibliotece.
     *
     * @return Liczba aktualnych czytelników.
     */
    public int getReadersCount() {
        return readersCount;
    }

    /**
     * Metoda zwracająca liczbę aktualnych pisarzy w bibliotece.
     *
     * @return Liczba aktualnych pisarzy.
     */
    public int getWritersCount() {
        return writersCount;
    }

}