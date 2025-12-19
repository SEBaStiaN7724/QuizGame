import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleTest {
    public static void main(String[] args) {
        // To jest symulacja gry w konsoli
        Scanner scanner = new Scanner(System.in);

        // 1. Tworzenie przykładowego pytania
        List<Question.Answer> answers = new ArrayList<>();
        answers.add(new Question.Answer("Berlin", false));
        answers.add(new Question.Answer("Paryż", true));
        answers.add(new Question.Answer("Londyn", false));
        answers.add(new Question.Answer("Madryt", false));
        
        Question q = new Question("Stolica Francji?", answers);
        // 2. Wyświetlenie pytania na ekranie
        System.out.println("\n=== QUIZ ===");
        System.out.println("Pytanie: " + q.getText());
        System.out.println("-----------------");
        
        for (int i = 0; i < q.getAnswers().size(); i++) {
            // Wyświetlenie odpowiedzi z numeracją
            System.out.println((i + 1) + ". " + q.getAnswers().get(i).getText());
        }

        // 3. Pobranie odpowiedzi od gracza
        System.out.println("-----------------");
        System.out.print("Twój wybór (wpisz numer): ");
        
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            
            // Sprawdzenie poprawności odpowiedzi
            if (choice > 0 && choice <= answers.size()) {
                Question.Answer selectedAnswer = q.getAnswers().get(choice - 1);
                
                if (selectedAnswer.isCorrect()) {
                    System.out.println(" BRAWO! To poprawna odpowiedź!");
                } else {
                    System.out.println(" Niestety, to błąd.");
                }
            } else {
                System.out.println(" Nie ma takiej opcji na liście.");
            }
        } else {
            System.out.println(" Proszę wpisać liczbę!");
        }

        scanner.close();
    }
}