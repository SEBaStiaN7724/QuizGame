package QuizGame;

public class QuizGame {
    public static void main(String[] args) {
        // Używamy nowej klasy do generowania danych
        DataGenerator.stworzPytaniaTestowe();

        // Uruchamiamy ekran konfiguracji
        new QuizConfigScreen();
    }
}