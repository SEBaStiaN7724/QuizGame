package QuizGame;
/** Klasa Question reprezentuje pytanie w grze quizowej */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    // Pola są teraz prywatne (hermetyzacja)
    private String tresc;
    private String kategoria;
    private String[] odpowiedzi;
    private int poprawnyIndeks;

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

    // --- GETTERY ---
    // Pozwalają na odczyt danych, ale nie na ich przypadkową zmianę z zewnątrz

    public String getTresc() {
        return tresc;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String[] getOdpowiedzi() {
        return odpowiedzi;
    }

    public int getPoprawnyIndeks() {
        return poprawnyIndeks;
    }
}