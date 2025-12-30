package QuizGame;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreManager {

    private static final String SCIEZKA_PLIKU = "QuizGame/wyniki.txt";

    public static void zapiszWynik(String imie, int punkty, String tryb) {
        try {
            File plik = new File(SCIEZKA_PLIKU);
            if (plik.getParentFile() != null) plik.getParentFile().mkdirs();
            
            FileWriter pisarz = new FileWriter(plik, true);
            pisarz.write(imie + ";" + punkty + ";" + tryb + "\n");
            pisarz.close();
        } catch (Exception e) {
            System.out.println("Błąd zapisu: " + e.getMessage());
        }
    }

    public static String pobierzNajlepszeWyniki(String szukanyTryb) {
        ArrayList<String[]> lista = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("--- NAJLEPSZE WYNIKI (" + szukanyTryb + ") ---\n");

        try {
            File plik = new File(SCIEZKA_PLIKU);
            if (!plik.exists()) return "(Brak wyników)";

            Scanner skaner = new Scanner(plik);
            while (skaner.hasNextLine()) {
                String[] czesci = skaner.nextLine().split(";");

                // Sprawdzamy czy linia jest poprawna i czy dotyczy tego trybu
                if (czesci.length == 3 && czesci[2].equals(szukanyTryb)) {
                    try {
                        
                        Integer.parseInt(czesci[1]); 
                        lista.add(czesci); 
                    } catch (NumberFormatException e) {} 
                }
            }
            skaner.close();

            lista.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

            int limit = Math.min(5, lista.size());
            if (limit == 0) sb.append("(Brak wyników)");

            for (int i = 0; i < limit; i++) {
                sb.append((i + 1) + ". " + lista.get(i)[0] + ": " + lista.get(i)[1] + "\n");
            }

        } catch (Exception e) {
            return "Błąd: " + e.getMessage();
        }
        return sb.toString();
    }
}