package QuizGame;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuizTest {
    @Test
    void testNowyGraczMaZeroPunktow() {
        Player p = new Player("Tester");
        assertEquals(0, p.getPunkty(), "Nowy gracz powinien mieć 0 punktów");
    }

    @Test
    void testMieszanieOdpowiedziNieZmieniaPoprawnej() {
        String[] odp = {"A", "B", "C", "D"};
        // Zakładamy, że poprawna to "B" (indeks 1)
        Question q = new Question("Pytanie?", "Test", odp, 1);
        
        // Sprawdzamy, czy poprawnyIndeks wskazuje na "B" w nowej, pomieszanej tablicy
        assertEquals("B", q.odpowiedzi[q.poprawnyIndeks]);
    }
}