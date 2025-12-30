package QuizGame;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuestionLoader {

    public static ArrayList<Question> wczytajPytania(String nazwaPliku, String wybranaKategoria) {
        ArrayList<Question> lista = new ArrayList<>();
        File plik = new File(nazwaPliku);

        if (!plik.exists()) return lista; // Zwracamy pustą listę jak brak pliku

        try {
            Scanner skaner = new Scanner(plik);
            while (skaner.hasNextLine()) {
                String tresc = skaner.nextLine();
                if (tresc.trim().isEmpty()) continue;

                String kategoriaPytania = "Ogólny";
                if (skaner.hasNextLine()) kategoriaPytania = skaner.nextLine().trim();

                String[] odp = new String[4];
                if (skaner.hasNextLine()) odp[0] = skaner.nextLine();
                if (skaner.hasNextLine()) odp[1] = skaner.nextLine();
                if (skaner.hasNextLine()) odp[2] = skaner.nextLine();
                if (skaner.hasNextLine()) odp[3] = skaner.nextLine();
                
                int poprawna = 0;
                if (skaner.hasNextLine()) {
                    try {
                        poprawna = Integer.parseInt(skaner.nextLine().trim()) - 1;
                    } catch (Exception e) { poprawna = 0; }
                }

                // Logika filtrowania
                if (wybranaKategoria.equals("Ogólny") || wybranaKategoria.equalsIgnoreCase(kategoriaPytania)) {
                    lista.add(new Question(tresc, kategoriaPytania, odp, poprawna));
                }
            }
            skaner.close();
            Collections.shuffle(lista); // Mieszamy od razu przy wczytywaniu

        } catch (Exception e) {
            System.out.println("Błąd wczytywania: " + e.getMessage());
        }
        return lista;
    }
}