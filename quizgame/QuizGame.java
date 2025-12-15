package com.mycompany.quizgame;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities; // <--- WAŻNE: Dodany import

public class QuizGame {

    public static void main(String[] args) {
        System.out.println("Start aplikacji Quiz Game...");

        // 1. Próba nawiązania połączenia z bazą danych (Test)
        try (Connection conn = JDBC.getConnection()) {
            System.out.println("Połączenie z bazą danych (question) udane!");

            // 2. Jeśli połączenie działa, URUCHAMIAMY OKNO GRY
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Tworzenie i wyświetlanie okna konfiguracji
                    new QuizConfigScreen();
                }
            });

        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych! Aplikacja nie zostanie uruchomiona.");
            System.err.println("Sprawdź hasło, URL lub czy serwer MySQL działa.");
            System.err.println("Komunikat błędu: " + e.getMessage());
        }
    }
}
