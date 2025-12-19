import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SimpleQuiz {

    // Klasa reprezentująca pojedyncze pytanie
    static class Pytanie {
        String tresc;
        String[] odpowiedzi = new String[4];
        int poprawnaOdp; // 1, 2, 3 lub 4

        public Pytanie(String tresc, String a, String b, String c, String d, int poprawna) {
            this.tresc = tresc;
            this.odpowiedzi[0] = a;
            this.odpowiedzi[1] = b;
            this.odpowiedzi[2] = c;
            this.odpowiedzi[3] = d;
            this.poprawnaOdp = poprawna;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pytanie> bazaPytan = wczytajPytaniaZPliku("pytania.txt");

        if (bazaPytan.isEmpty()) {
            System.out.println("Błąd! Nie udało się wczytać pytań z pliku 'pytania.txt'.");
            System.out.println("Upewnij się, że plik jest w głównym folderze projektu.");
            return;
        }

        // --- KONFIGURACJA ---
        System.out.println("=== WITAJ W TEXTOWYM QUIZIE ===");
        System.out.print("Podaj liczbę graczy: ");
        int liczbaGraczy = 0;
        try {
            liczbaGraczy = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Podano nieprawidłową liczbę. Ustawiam 1 gracza.");
            liczbaGraczy = 1;
        }

        String[] imiona = new String[liczbaGraczy];
        int[] punkty = new int[liczbaGraczy];

        for (int i = 0; i < liczbaGraczy; i++) {
            System.out.print("Podaj imię gracza " + (i + 1) + ": ");
            imiona[i] = scanner.nextLine();
        }

        // Mieszamy pytania, żeby za każdym razem były w innej kolejności
        Collections.shuffle(bazaPytan);

        // --- ROZGRYWKA ---
        int numerPytania = 0;
        int turaGracza = 0;

        System.out.println("\n--- ZACZYNAMY GRĘ! ---\n");

        // Pętla trwa dopóki mamy pytania w bazie
        while (numerPytania < bazaPytan.size()) {
            Pytanie p = bazaPytan.get(numerPytania);
            String aktualnyGracz = imiona[turaGracza];

            System.out.println("TURA GRACZA: " + aktualnyGracz.toUpperCase());
            System.out.println("Pytanie: " + p.tresc);
            System.out.println("1. " + p.odpowiedzi[0]);
            System.out.println("2. " + p.odpowiedzi[1]);
            System.out.println("3. " + p.odpowiedzi[2]);
            System.out.println("4. " + p.odpowiedzi[3]);
            
            System.out.print("Twoja odpowiedź (1-4): ");
            
            String wyborStr = scanner.nextLine();
            int wybor = -1;
            try {
                wybor = Integer.parseInt(wyborStr);
            } catch (NumberFormatException e) {
                // Ignorujemy błędy wpisywania, zostanie -1
            }

            if (wybor == p.poprawnaOdp) {
                System.out.println(">>> DOBRZE! (+1 pkt)");
                punkty[turaGracza]++;
            } else {
                System.out.println(">>> ŹLE! Poprawna to: " + p.poprawnaOdp);
            }
            System.out.println("------------------------------------------------");

            // Przejście do następnego gracza i następnego pytania
            turaGracza++;
            if (turaGracza >= liczbaGraczy) {
                turaGracza = 0;
            }
            numerPytania++;
        }

        // --- WYNIKI ---
        System.out.println("\n=== KONIEC GRY ===");
        System.out.println("WYNIKI KOŃCOWE:");
        int max = -1;
        for (int pkt : punkty) {
            if (pkt > max) max = pkt;
        }

        for (int i = 0; i < liczbaGraczy; i++) {
            System.out.print(imiona[i] + ": " + punkty[i] + " pkt");
            if (punkty[i] == max && max > 0) {
                System.out.print(" (ZWYCIĘZCA!)");
            }
            System.out.println();
        }
    }

    // Metoda wczytująca plik tekstowy
    private static ArrayList<Pytanie> wczytajPytaniaZPliku(String sciezka) {
        ArrayList<Pytanie> lista = new ArrayList<>();
        try {
            File plik = new File(sciezka);
            Scanner fs = new Scanner(plik, "UTF-8"); // Kodowanie UTF-8 dla polskich znaków

            while (fs.hasNextLine()) {
                String tresc = fs.nextLine();
                if (tresc.trim().isEmpty()) continue; // Pomiń puste linie

                String a = fs.nextLine();
                String b = fs.nextLine();
                String c = fs.nextLine();
                String d = fs.nextLine();
                String poprawnaStr = fs.nextLine();

                int poprawna = Integer.parseInt(poprawnaStr);
                lista.add(new Pytanie(tresc, a, b, c, d, poprawna));
            }
            fs.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku: " + sciezka);
        } catch (Exception e) {
            System.out.println("Błąd wczytywania pliku (sprawdź format!): " + e.getMessage());
        }
        return lista;
    }
}