# Reader-Writer-Problem

*******************************************************************
Co to jest Problem czytelników i pisarzy?
*******************************************************************

Problem czytelników i pisarzy – klasyczny informatyczny problem synchronizacji dostępu do jednego zasobu
(pliku, rekordu bazy danych) dwóch rodzajów procesów: dokonujących i niedokonujących w nim zmian.
W problemie czytelników i pisarzy zasób jest dzielony pomiędzy dwie grupy procesów:
czytelnicy – wszystkie procesy niedokonujące zmian w zasobie,
pisarze – pozostałe procesy.
Problem czytelników i pisarzy to kolejny, znany problem synchronizacji. Dotyczy on dostępu do jednego zasobu dwóch rodzajów procesów:
dokonujących w nim zmian (pisarzy) i
niedokonujących w nim zmian (czytelników).
Jednoczesny dostęp do zasobu może uzyskać dowolna liczba czytelników. Pisarz może otrzymać tylko dostęp wyłączny.
Równocześnie z pisarzem dostępu do zasobu nie może otrzymać ani inny pisarz, ani czytelnik, gdyż mogłoby to spowodować błędy.

*******************************************************************
Omówienie algorytmu:
*******************************************************************

Klasa Library:
```java
private final Semaphore readSemaphore = new Semaphore(5);
private final Semaphore writeSemaphore = new Semaphore(1);
Deklaracja dwóch pól prywatnych typu Semaphore. readSemaphore kontroluje dostęp do operacji czytania, ograniczając jednoczesne czytanie do 5 czytelników. writeSemaphore kontroluje dostęp do operacji pisania, umożliwiając jednoczesne pisanie tylko jednemu pisarzowi.
private int readersCount = 0;
private int writersCount = 0;
Deklaracja dwóch pól prywatnych przechowujących liczbę aktualnych czytelników (readersCount) i pisarzy (writersCount) w czytelni.
public void requestRead(String readerName) throws InterruptedException {
    readSemaphore.acquire();
    readersCount++;
}
Metoda requestRead służy do żądania dostępu do czytania. Pisarz próbuje zająć semafor readSemaphore za pomocą readSemaphore.acquire(). Jeśli semafor jest dostępny (nie jest już zajęty przez maksymalną liczbę czytelników), to czytelnik może wejść do sekcji czytania, a liczba czytelników w czytelni jest zwiększana.
public void finishRead(String readerName) {
    readSemaphore.release();
    readersCount--;
}
Metoda finishRead informuje, że czytelnik zakończył operację czytania, zwalniając semafor readSemaphore i zmniejszając liczbę czytelników w czytelni.
public void requestWrite(String writerName) throws InterruptedException {
    writeSemaphore.acquire();
    readSemaphore.acquire(5);
    writersCount++;
}
Metoda requestWrite służy do żądania dostępu do pisania. Pisarz próbuje zająć semafor writeSemaphore, a następnie semafor readSemaphore dla maksymalnie 5 czytelników (blokując wejście czytelników na czas pisania). Jeśli oba semafory są dostępne, pisarz może zacząć pisanie, a liczba pisarzy w czytelni jest zwiększana.
public void finishWrite(String writerName) {
    writeSemaphore.release();
    readSemaphore.release(5);
    writersCount--;
}
Metoda finishWrite informuje, że pisarz zakończył operację pisania, zwalniając semafor writeSemaphore i semafor readSemaphore dla maksymalnie 5 czytelników. Zmniejsza również liczbę pisarzy w czytelni.
public int getReadersQueue() {
    return Main.lc - readersCount;
}

public int getWritersQueue() {
    return Main.lp - writersCount;
}
Metody te zwracają liczbę czytelników i pisarzy znajdujących się w kolejce, obliczaną na podstawie całkowitej liczby czytelników (Main.lc) i pisarzy (Main.lp) minus aktualna liczba czytelników (readersCount) i pisarzy (writersCount) w czytelni.
public int getReadersCount() {
    return readersCount;
}

public int getWritersCount() {
    return writersCount;
}
Metody te zwracają aktualną liczbę czytelników (readersCount) i pisarzy (writersCount) w czytelni.
```
```
Klasa Writer:
private final Library library; - Deklaracja prywatnego pola o nazwie library o typie Library. Pole to przechowuje referencję do obiektu klasy Library i umożliwia dostęp do operacji związanych z czytaniem i pisaniem.
private final String name; - Deklaracja prywatnego pola o nazwie name o typie String, które przechowuje unikalną nazwę pisarza.
private final Random random = new Random(); - Deklaracja prywatnego pola o nazwie random o typie Random, które jest używane do generowania losowych wartości. Jest to przydatne do symulowania losowych czasów oczekiwania i operacji pisania.
public Writer(String name, Library library) {
    this.name = name;
    this.library = library;
}
Konstruktor klasy Writer przyjmuje dwie wartości: name (nazwa pisarza) i library (referencja do obiektu biblioteki). Przypisanie tych wartości do odpowiednich pól obiektu.
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
```
Metoda run reprezentuje działania pisarza w pętli nieskończonej. Pisarz próbuje uzyskać dostęp do biblioteki poprzez wywołanie library.requestWrite(name),
a następnie wypisuje informacje dotyczące chęci wejścia do czytelni, liczby pisarzy w czytelni, liczby pisarzy w kolejce itp. Po zakończeniu operacji pisania, pisarz zwalnia zasoby biblioteki i
przechodzi do stanu oczekiwania na ponowne wejście do czytelni. W międzyczasie generowane są losowe czasy oczekiwania przy użyciu Random i Thread.sleep.


Klasa Reader:
Analogicznie do klasy Writer
```
Klasa Main:
static int lc;
static int lp;
Deklaracja dwóch statycznych pól przechowujących liczby czytelników (lc) i pisarzy (lp). Są one dostępne na poziomie klasy, co oznacza, że mogą być współdzielone przez różne instancje tej klasy.
private static final Logger logger = Logger.getLogger("Logger"); - Deklaracja statycznego pola prywatnego logger typu Logger, który jest używany do logowania komunikatów z programu. Jest to narzędzie pomocne do monitorowania działania programu i śledzenia operacji czytania i pisania w czasie rzeczywistym.
static Scanner scanner = new Scanner(System.in); - Deklaracja statycznego obiektu Scanner do wczytywania danych od użytkownika z konsoli. Umożliwia wprowadzanie liczby pisarzy i czytelników podczas uruchamiania programu.
public static void main(String[] args) throws IOException {
    Library library = new Library();

    lp = wczytajLiczbe("Podaj liczbę pisarzy:");
    lc = wczytajLiczbe("Podaj liczbę czytelników:");
W metodzie main tworzony jest obiekt Library, a następnie użytkownik jest proszony o wprowadzenie liczby pisarzy (lp) i czytelników (lc). Wartości te są wczytywane za pomocą metody wczytajLiczbe, której definicja znajduje się poniżej.
for (int i = 1; i <= lp; i++) {
    Writer writer = new Writer("Pisarz " + i, library);
    writer.start();
}

for (int i = 1; i <= lc; i++) {
    Reader reader = new Reader("Czytelnik " + i, library);
    reader.start();
}
Następnie, w pętli, tworzone są obiekty pisarzy (Writer) i czytelników (Reader), nadawane są im unikalne nazwy, a każdy z nich uruchamiany jest w osobnym wątku (start()). W ten sposób symulowana jest współbieżność operacji czytania i pisania.
public static int wczytajLiczbe(String komunikat) {
    while (true) {
        try {
            logger.info(komunikat);
            int liczba = Integer.parseInt(scanner.nextLine());
            if (liczba >= 0) {
                return liczba;
            } else {
                logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą nieujemną.");
            }
        } catch (NumberFormatException e) {
            logger.info("Podano niepoprawną wartość. Wprowadź liczbę całkowitą.");
        }
    }
}
```
Metoda wczytajLiczbe przyjmuje komunikat jako argument i prosi użytkownika o wprowadzenie liczby. Jeśli wprowadzona wartość nie jest liczbą całkowitą nieujemną, program pyta użytkownika ponownie. Metoda ta jest wykorzystywana do wczytania liczby pisarzy i czytelników.


*******************************************************************
Komunikacja między wątkami
*******************************************************************

Program symuluje współbieżne korzystanie z biblioteki przez pisarzy i czytelników za pomocą wielowątkowości. Komunikacja między wątkami w tym programie opiera się na mechanizmach synchronizacyjnych, takich jak semafory, które są używane do zarządzania dostępem do zasobów biblioteki.

Semaphore w klasie Library:

readSemaphore to semafor dla operacji czytania, zainicjowany wartością 5. Oznacza to, że jednocześnie może czytać do 5 czytelników.
writeSemaphore to semafor dla operacji pisania, zainicjowany wartością 1. Oznacza to, że tylko jeden pisarz może jednocześnie pisać.
Operacje pisarza (Writer) i czytelnika (Reader):

Każdy pisarz i czytelnik używa metod requestRead, finishRead, requestWrite i finishWrite w klasie Library do zarządzania semaforami, które kontrolują dostęp do zasobów biblioteki.
Pisarze wywołują requestWrite przed rozpoczęciem pisania i finishWrite po zakończeniu pisania. Analogicznie, czytelnicy wywołują requestRead przed rozpoczęciem czytania i finishRead po zakończeniu czytania.
Zabezpieczenie dostępu do zmiennych współdzielonych:

Zmienne takie jak readersCount i writersCount w klasie Library są dostępne dla wielu wątków. W związku z tym, dostęp do tych zmiennych jest zabezpieczony przy użyciu semaforów, aby uniknąć współbieżnych problemów.
Logger:

Logger jest używany do rejestrowania komunikatów, a każdy wątek wypisuje informacje o swoich operacjach i aktualnym stanie biblioteki.

*******************************************************************
Sposób uruchomienia:
*******************************************************************
Aby uruchomić program w Java, użyj komendy java -jar nazwa-paczki.jar. Ta komenda wskazuje na plik JAR, który zawiera skompilowany kod i zależności.
Java wczytuje plik JAR i uruchamia aplikację zdefiniowaną w pliku manifestu. Dzięki temu podejściu można łatwo dostarczać i
uruchamiać aplikacje jako jedno samodzielne archiwum JAR.


```
public void finishWrite(String writerName){
    writeSemaphore.release();
    readSemaphore.release(5);
    writersCount--;
}
writeSemaphore.release();:
writeSemaphore to obiekt semafora, który wcześniej został użyty do zarezerwowania dostępu do operacji pisania przez pisarza.
Wywołanie release() zwalnia zezwolenie semafora, co oznacza zakończenie operacji pisania przez pisarza i zwolnienie dostępu do zasobów biblioteki.
Zwalnianie zezwoleń jest istotne, ponieważ pozwala na korzystanie z tych zasobów przez inne wątki.
readSemaphore.release(5);:
Ponieważ pisarz kończy operację pisania, musi również zwolnić blokadę dla czytelników. Stąd używane jest readSemaphore.release(5).
Wartość 5 oznacza zwolnienie blokady dla pięciu czytelników, którzy byli wcześniej zablokowani.
writersCount--;:
Po zwolnieniu zezwoleń, licznik aktualnych pisarzy (writersCount) jest dekrementowany.
Licznik ten zmniejsza się, ponieważ pisarz kończy operację pisania i zwalnia zasoby dla innych pisarzy lub czytelników.


public void requestWrite(String writerName) throws InterruptedException {
    writeSemaphore.acquire();
    readSemaphore.acquire(5);
    writersCount++;
}
```
W skrócie, requestWrite() służy do rezerwowania dostępu do operacji pisania w bibliotece dla danego pisarza. Jeśli dostęp jest możliwy
(czyli są dostępne zezwolenia semaforów), wątek pisarza uzyskuje zezwolenie, blokuje dostęp dla czytelników, a licznik pisarzy jest inkrementowany, co oznacza, że jeden pisarz więcej korzysta z biblioteki.
Jeśli nie ma dostępnych zezwoleń, wątek zostanie zablokowany do momentu, gdy zezwolenia będą dostępne do użycia.
