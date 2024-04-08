package org.example;


import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Główna klasa programu, obsługująca uruchamianie biblioteki oraz interakcję z użytkownikiem.
 */
public class Main {
    /** Liczba czytelników. */
    static int lc;
    /** Liczba pisarzy. */
    static int lp;
    /** Logger do rejestrowania komunikatów. */
    static final Logger logger = Logger.getLogger("Logger");
    /** Scanner do wczytywania danych od użytkownika. */
    static Scanner scanner = new Scanner(System.in);
    /**
     * Główna metoda programu, która inicjuje bibliotekę, wczytuje liczbę pisarzy i czytelników,
     * a następnie uruchamia odpowiednią liczbę wątków pisarzy i czytelników.
     *
     * @param args Argumenty przekazywane podczas uruchamiania programu (nie są używane).
     *
     */
    public static void main(String[] args) throws IOException {
        Library library = new Library();

        // Wczytywanie liczby pisarzy
        logger.info("Podaj liczbę pisarzy:");
        while (true) {
            try {
                lp = scanner.nextInt();
                if (lp >= 0) {
                    break;
                } else {
                    logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą nieujemną.");
                }
            } catch (NumberFormatException e) {
                logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą.");
            }
        }

        // Wczytywanie liczby czytelników
        logger.info("Podaj liczbę czytelników:");
        while (true) {
            try {
                lc = scanner.nextInt();
                if (lc >= 0) {
                    break;
                } else {
                    logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą nieujemną.");
                }
            } catch (NumberFormatException e) {
                logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą.");
            }
        }

        for (int i = 1; i <= lp ; i++) {
            Writer writer =  new Writer("Pisarz " + i, library);
            writer.start();
        }

        for (int i = 1; i <= lc; i++) {
            Reader reader = new Reader("Czytelnik " + i, library);
            reader.start();
        }

    }


}