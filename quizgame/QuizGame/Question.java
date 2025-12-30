package QuizGame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    public String tresc;
    public String kategoria;
    public String[] odpowiedzi;
    public int poprawnyIndeks;

    public Question(String tresc, String kategoria, String[] odpowiedzi, int poprawnyIndeks) {
        this.tresc = tresc;
        this.kategoria = kategoria;
        
        if (poprawnyIndeks < 0 || poprawnyIndeks >= 4) {
            poprawnyIndeks = 0;
        }

        // 1. Zapamiętujemy tekst poprawnej odpowiedzi przed mieszaniem
        String poprawnyTekst = odpowiedzi[poprawnyIndeks];
        // 2. Mieszamy odpowiedzi
        List<String> lista = Arrays.asList(odpowiedzi);
        Collections.shuffle(lista); 
        // 3. Przypisujemy pomieszane odpowiedzi z powrotem do tablicy
        this.odpowiedzi = lista.toArray(new String[0]);
        // 4. Aktualizujemy indeks poprawnej odpowiedzi po mieszaniu
        this.poprawnyIndeks = lista.indexOf(poprawnyTekst);
    }
}