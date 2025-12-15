
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizConfigScreen extends JFrame {

    // Komponenty GUI (Deklaracja Pól Klasy)
    private JSpinner questionCountSpinner;
    private JSpinner playerCountSpinner;
    private JComboBox<String> gameTypeComboBox;
    private JButton startButton;

    public QuizConfigScreen() {
        // 1. Ustawienia głównego okna
        setTitle("Konfiguracja Gry Quizowej");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Użycie pack() jest lepsze, ale ustawiamy stały rozmiar, aby pasował do GridLayout
        setSize(400, 350);
        // 5 wierszy, 2 kolumny, odstępy 10px
        setLayout(new GridLayout(5, 2, 10, 10)); 
        getContentPane().setBackground(Colors.LIGHT_BLUE);
        // 2. Inicjalizacja komponentów (w konstruktorze)
        

        // Liczba pytań (Spinner - pole z przyciskami)
        // Wartość początkowa: 10, Min: 5, Max: 50, Krok: 5
        SpinnerModel questionModel = new SpinnerNumberModel(10, 5, 50, 5); 
        questionCountSpinner = new JSpinner(questionModel);
        add(new JLabel("  Liczba Pytań:"));
        add(questionCountSpinner);

        // Liczba graczy
        // Wartość początkowa: 1, Min: 1, Max: 4, Krok: 1
        SpinnerModel playerModel = new SpinnerNumberModel(1, 1, 4, 1); 
        playerCountSpinner = new JSpinner(playerModel);
        add(new JLabel("  Liczba Graczy:"));
        add(playerCountSpinner);

        // Typ Quizu (ComboBox - lista rozwijana)
        String[] types = {"Ogólny", "Historia", "Nauka", "Sport", "Geografia"};
        gameTypeComboBox = new JComboBox<>(types);
        add(new JLabel("  Typ Quizu:"));
        add(gameTypeComboBox);
        
        // Pusta przestrzeń dla lepszego wyglądu
        add(new JLabel("")); 
        add(new JLabel(""));

        // Przycisk Start
        startButton = new JButton("Rozpocznij Grę");
        add(startButton);

        // Dodatkowa pusta komórka dla układu 2 kolumn
        add(new JLabel("")); 

        // 3. Dodanie obsługi zdarzeń (akcji przycisku)
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pobierzUstawienia();
            }
        });

        // Wyświetlenie okna na środku ekranu
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Metoda do pobierania i wyświetlania wprowadzonych ustawień.
     */
    private void pobierzUstawienia() {
        // Pobieranie danych z komponentów
        int numQuestions = (int) questionCountSpinner.getValue();
        int numPlayers = (int) playerCountSpinner.getValue();
        String quizType = (String) gameTypeComboBox.getSelectedItem();

        // Poprośmy o imiona dopiero po kliknięciu "Rozpocznij Grę"
        String[] playerNames;
        if (numPlayers == 1) {
            String single = (String) JOptionPane.showInputDialog(
                    this,
                    "Wprowadź imię gracza:",
                    "Imię gracza",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Gracz 1"
            );
            if (single == null) return; // anulowano
            playerNames = new String[]{single};
        } else {
            JPanel namesPanel = new JPanel(new GridLayout(numPlayers, 2, 5, 5));
            JTextField[] nameFields = new JTextField[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                namesPanel.add(new JLabel("Imię Gracza " + (i + 1) + ":"));
                JTextField tf = new JTextField("Gracz " + (i + 1));
                nameFields[i] = tf;
                namesPanel.add(tf);
            }

            int res = JOptionPane.showConfirmDialog(this, namesPanel, "Wprowadź imiona graczy",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) return; // anulowano

            playerNames = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerNames[i] = nameFields[i].getText();
            }
        }

        // Wyświetlanie informacji
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("--- Ustawienia Quizu ---\n");
        infoBuilder.append("Gracze:\n");
        for (int i = 0; i < playerNames.length; i++) {
            infoBuilder.append(String.format("  %d. %s\n", i + 1, playerNames[i]));
        }
        infoBuilder.append(String.format("Liczba Pytań: %d\n", numQuestions));
        infoBuilder.append(String.format("Liczba Graczy: %d\n", numPlayers));
        infoBuilder.append(String.format("Typ: %s", quizType));

        JOptionPane.showMessageDialog(this, infoBuilder.toString(), "Gra się rozpoczyna!", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // Zamykamy okno konfiguracji
        
        // Uruchamiamy okno gry w wątku Swinga
        SwingUtilities.invokeLater(() -> {
            new GameScreen(playerNames, numQuestions, quizType);
        });
    }

    public static void main(String[] args) {
        // Uruchomienie GUI w wątku EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizConfigScreen();
            }
        });
    }
}