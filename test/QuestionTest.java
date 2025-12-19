import java.util.ArrayList;
import java.util.List;

public class QuestionTest {

    public static void main(String[] args) {
        System.out.println("=== ROZPOCZYNAM TESTOWANIE KLASY QUESTION ===");

        // 1. Przygotowanie danych testowych (tworzymy odpowiedzi)
        List<Question.Answer> answers = new ArrayList<>();
        answers.add(new Question.Answer("Paryż", true));
        answers.add(new Question.Answer("Londyn", false));
        answers.add(new Question.Answer("Berlin", false));
        answers.add(new Question.Answer("Madryt", false));
        
        // 2. Tworzymy obiekt Question (to jest to, co testujemy)
        String questionText = "Stolica Francji?";
        Question question = new Question(questionText, answers);

        // 3. Sprawdzamy, czy tekst pytania się zgadza
        if (question.getText().equals("Stolica Francji?")) {
            System.out.println("✅ Test tekstu pytania: ZALICZONY");
        } else {
            System.err.println("❌ Test tekstu pytania: BŁĄD");
            System.err.println("   Oczekiwano: 'Stolica Francji?'");
            System.err.println("   Otrzymano:  '" + question.getText() + "'");
        }

        // 4. Sprawdzamy, czy lista odpowiedzi ma dobry rozmiar
        if (question.getAnswers().size() == 4) {
            System.out.println("✅ Test liczby odpowiedzi: ZALICZONY");
        } else {
            System.err.println("❌ Test liczby odpowiedzi: BŁĄD (oczekiwano 4)");
        }

        // 5. Sprawdzamy poprawność pierwszej odpowiedzi
        Question.Answer firstAnswer = question.getAnswers().get(0);
        if (firstAnswer.isCorrect() == true) {
            System.out.println("✅ Test poprawności odpowiedzi (Paryż): ZALICZONY");
        } else {
            System.err.println("❌ Test poprawności odpowiedzi: BŁĄD");
        }

        System.out.println("=== KONIEC TESTÓW ===");
    }
}