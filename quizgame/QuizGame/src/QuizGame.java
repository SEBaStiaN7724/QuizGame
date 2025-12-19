

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class QuizGame {

    public static void main(String[] args) {
        System.out.println("Start aplikacji Quiz Game...");

        // Uruchamiamy okno konfiguracji natychmiast — nie blokujemy startu na DB
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizConfigScreen();
            }
        });

        // Test połączenia z bazą w tle (logujemy wynik w konsoli)
        new Thread(() -> {
            try (Connection conn = JDBC.Connection()) {
                System.out.println("Połączenie z bazą danych (question) udane!");
            } catch (SQLException e) {
                System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
            }
        }).start();
    }
}
